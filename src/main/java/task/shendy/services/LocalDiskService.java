package task.shendy.services;

// MARK: Import Statements

import task.shendy.Utils;
import task.shendy.models.Book;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Collections;

// MARK: Static Import Statements

import static java.nio.file.StandardOpenOption.*;

// MARK: Class Definition

public class LocalDiskService {

    // MARK: Properties

    private SeekableByteChannel byteChannel;
    private SortedMap<Integer, Book> bookList = new TreeMap<>();
    private Set<Book> bookToSaveList = new HashSet<>();
    private Path dbFilePath = Paths.get(System.getProperty("user.home") + "/library.db");
    private Path lockFilePath = Paths.get(System.getProperty("user.home") + "/library.lock");

    // MARK: Get Singletone instance of LocalDiskService

    public static LocalDiskService getInstance() {
        return LocaDiskServiceSingletoneHolder.SINGLETONE_INSTANCE;
    }

    private static class LocaDiskServiceSingletoneHolder {
        static final LocalDiskService SINGLETONE_INSTANCE = new LocalDiskService();
    }

    // MARK: Constructor

    public LocalDiskService() {
        loadBookListFromDisk();
        createLockFile();
    }

    // MARK: Service Methods (Find/Add/Update)

    public Book findOneBook(int bookId) {
        return this.bookList.get(bookId);
    }

    public List<Book> findBookList() {
        return Collections.unmodifiableList(
                this.bookList.values().stream().collect(Collectors.toList())
        );
    }

    public List<Book> findBookListByTitle(String searchKeyword) {
        return this.bookList.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(searchKeyword))
                .collect(Collectors.toList());
    }

    public int add(String title, String author, String description) {
        int newId = generateNewId();

        Book newBook = new Book(newId, title, author, description);
        this.bookList.put(newId, newBook);
        this.bookToSaveList.add(newBook);

        return newId;
    }

    public void update(int id, String title, String author, String description) {
        for (Book bookToUpdate : this.bookList.values()) {
            if (bookToUpdate.getId() == id) {
                bookToUpdate.setTitle(title);
                bookToUpdate.setAuthor(author);
                bookToUpdate.setDescription(description);

                this.bookToSaveList.add(bookToUpdate);
            }
        }
    }

    // MARK: Disk storage related Methods

    private void loadBookListFromDisk() {
        Set<OpenOption> options = new HashSet<>();
        options.add(CREATE);
        options.add(READ);
        options.add(WRITE); // This will force the newByteChannel to create the file if not exit

        try (SeekableByteChannel byteChannel = Files.newByteChannel(dbFilePath, options)) {
            while (byteChannel.position() < byteChannel.size()) {
                RecordBufferManager recordBufferManager = new RecordBufferManager();


                recordBufferManager.readFromChannel(byteChannel);
                // Book loadedBook = recordBufferManager.mapRecordBufferToBook();
                Book loadedBook = recordBufferManager.writeToBook();
                this.bookList.put(loadedBook.getId(), loadedBook);
            }
        } catch (IOException e) {
            Utils.printCannotReadDatabaseFile();
            e.printStackTrace();
        }
    }

    public boolean writeChangesToDisk() {
        Set<OpenOption> options = new HashSet<>();
        options.add(WRITE);

        try (SeekableByteChannel byteChannel = Files.newByteChannel(dbFilePath, options)) {
            RecordBufferManager recordBufferManager = new RecordBufferManager();

            for (Book book : this.bookToSaveList) {
                recordBufferManager.readFromBook(book);
                recordBufferManager.writeToChannel(book.getId(), byteChannel);
            }
            return true;

        } catch (IOException e) {
            Utils.printCannotReadDatabaseFile();
            Utils.println(e);
            return false;
        }
    }

    // MARK: Lock file related methods

    private boolean createLockFile() {
        try {
            Files.createFile(lockFilePath);

        } catch (FileAlreadyExistsException e) {
            Utils.println("\nerror: Another instance of LibraryManager app is running,\n"
                    + "       Please close this instance, or kill its process.\n");
            return false;

        } catch (IOException e) {
            Utils.println(e);
            System.exit(2);
            return false;
        }

        return true;
    }

    public boolean deleteLockFile() {
        try {
            Files.delete(lockFilePath);

        } catch (IOException e) {
            Utils.println(e);
            return false;
        }

        return true;
    }

    // MARK: Private Helper Methods

    private int generateNewId() {
        return this.bookList.isEmpty() ? 1 : this.bookList.lastKey() + 1;
    }
}
