package task.shendy.controllers;

import task.shendy.models.Book;
import task.shendy.console.ConsoleResponse;

import java.util.*;

public class LibraryController {
    private Map<Integer, Book> libraryMap = new TreeMap<>();

    public LibraryController() {
        Book book = new Book(1, "Title1", "Author1", "Description1");
        libraryMap.put(book.getId(), book);

        book = new Book(2, "Title2", "Author2", "Description2");
        libraryMap.put(book.getId(), book);

        book = new Book(3, "Title3", "Author3", "Description3");
        libraryMap.put(book.getId(), book);
    }

    public ConsoleResponse findAllBooks() {
        List<Book> bookList = new ArrayList<>(this.libraryMap.values());

        return bookList.isEmpty()
                ? ConsoleResponse.ok(bookList, "INFO: Library is empty, please add books.")
                : ConsoleResponse.ok(bookList);
    }

    public ConsoleResponse findBookById(int id) {

        try {
            Book book = this.libraryMap.get(id);
            return book == null
                    ? ConsoleResponse.error("ERROR: Library doesn't have book with id [" + id + "].")
                    : ConsoleResponse.ok(book);

        } catch (NumberFormatException e) {
            return ConsoleResponse.error("ERROR: No such book with id [" + id + "]");
        }
    }

    public ConsoleResponse searchForBookByTitle(String keyword) {
        return ConsoleResponse.ok(this.libraryMap.get(0));
    }

    public ConsoleResponse addBook(Map<String, String> formParams) {
        Book book = this.libraryMap.get(this.libraryMap.size() - 1);
        String title = formParams.get("title");
        String author = formParams.get("author");
        String description = formParams.get("description");

        int newId = book.getId() + 1;

        Book newBook = new Book(newId, title, author, description);

        this.libraryMap.put(newId, newBook);

        return ConsoleResponse.ok("Book [" + newId + "] saved.");
    }

    public ConsoleResponse editBook(int id, Map<String, String> formParams) {
        Book book = this.libraryMap.get(id);

        if (book != null) {
            book.setTitle(formParams.get("title"));
            book.setAuthor(formParams.get("author"));
            book.setDescription(formParams.get("description"));
        }

        return book == null
                ? ConsoleResponse.error("ERROR: No such book with id [" + id + "]")
                : ConsoleResponse.ok("Book saved.");
    }
}
