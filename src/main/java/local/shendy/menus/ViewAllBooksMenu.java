package local.shendy.menus;

// MARK: Import Statements

import local.shendy.models.Book;
import local.shendy.Utils;
import local.shendy.services.LocalDiskService;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

// MARK: Class Definition

/**
 * This class is responsible for "View All Books" menu:
 *
 * Sample:
 *   ==== View Books ====
 *       [1] The Hobbit
 *       [2] Lord of the Rings
 *       [3] Snow White and the Seven Dwarfs
 *       [4] Moby Dick
 *       [5] Snow Crash
 *       [6] Kite Runner
 *
 *   To view details enter the book ID, to return press <Enter>.
 *
 *   Book ID: 6
 *
 *       ID: 6
 *       Title: Kite Runner
 *       Author: Khaled Hosseini
 *       Description: Story of Amir, a young boy from Kabul.
 *
 *   To view details enter the book ID, to return press <Enter>.
 *
 *   Book ID: 4
 *
 *       ID: 4
 *       Title: Moby Dick
 *       Author: Herman Melville
 *       Description: About a really big whale that
 *
 *   To view details enter the book ID, to return press <Enter>.
 *
 *   Book ID: <Enter>
 */
public class ViewAllBooksMenu implements IMenu {

    // MARK: LocalDiskService Instance

    private final LocalDiskService localDiskService = LocalDiskService.getInstance();

    // MARK: Properties

    private Scanner consoleReader = new Scanner(System.in);;
    private List<Book> bookList = new ArrayList<>();

    private final String HEADER = "\n===== View All Books =====";
    private final String BOOK_ID_PROMPT = "\nTo view details enter the book ID, to return press <Enter>.\n\n    Book ID: ";

    // MARK: IMenu implementations

    @Override
    public void runMenu() {
        this.printHeader();
        this.prepareBookList();
        this.printBody();
        this.readConsoleInput();
    }

    @Override
    public void printHeader() {
        Utils.println(HEADER);
    }

    /**
     * It prints all books details, otherwise asks to first add books.
     */
    @Override
    public void printBody() {
        if (this.bookList.isEmpty()) {
            Utils.println("\nLibrary is empty, please add book(s) first.");
        } else {
            Utils.printBookList(this.bookList);
        }
    }

    /**
     * If there are books in the library,
     *   it will ask user to enter book ID to print its details.
     *
     * It displays error message if user entered invalid book ID.
     */
    @Override
    public void readConsoleInput() {
        while (!this.bookList.isEmpty()) {
            this.printBookIdPrompt();

            String userInput = consoleReader.nextLine();

            // When user press <Enter>, return from readConsoleInput immediately
            if (userInput.equals("")) {
                return;
            }

            // Convert the userInput to integer
            // then find the book with given id
            try {
                int bookId = Integer.parseInt(userInput);
                Book book = localDiskService.findOneBook(bookId);

                // Display error when user enters invalid book id
                if (book == null) {
                    Utils.printSelectValidBookIdError();

                    // Print book details
                } else {
                    Utils.println(book);
                }

            } catch (NumberFormatException e) {
                Utils.printSelectValidBookIdError();
            }
        }
    }

    // MARK: Prompt related methods

    private void printBookIdPrompt() {
        Utils.print(BOOK_ID_PROMPT);
    }

    // MARK: Private Helper Methods

    private void prepareBookList() {
        this.bookList = localDiskService.findBookList();
    }
}