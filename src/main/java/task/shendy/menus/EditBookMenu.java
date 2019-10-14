package task.shendy.menus;

// MARK: Import Statements

import task.shendy.models.Book;
import task.shendy.Utils;
import task.shendy.services.LocalDiskService;
import task.shendy.validators.BookValidator;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

// MARK: Class Definition

/**
 * This class is responsible for "Edit a Book" menu:
 *
 * Sample:
 *   ==== Edit a Book ====
 *
 *       [1] The Hobbit
 *       [2] Lord of the Rings
 *       [3] Snow White and the Seven Dwarfs
 *       [4] Moby Dick
 *       [5] Snow Crash
 *
 *   Enter the book ID of the book you want to edit; to return press <Enter>.
 *
 *   Book ID: 4
 *
 *   Input the following information. To leave a field unchanged, hit <Enter>
 *
 *        Title [Moby Dick]: <Enter>
 *        Author: [Herman Melville]: <Enter>
 *        Description: [About a big whale]: About a really big whale that
 *
 *   Book saved.
 *
 *   Enter the book ID of the book you want to edit; to return press <Enter>.
 *
 *   Book ID: <Enter>
 */
public class EditBookMenu implements IMenu {

    // MARK: LocalDiskService Instance

    private LocalDiskService localDiskService = LocalDiskService.getInstance();

    // MARK: Properties

    private Scanner consoleReader = new Scanner(System.in);
    private List<Book> bookList = new ArrayList<>();

    // MARK: Menu-related string constants

    private String HEADER = "\n===== Edit a Book =====";
    private String BOOK_ID_PROMPT = "\nEnter the book ID of the book you want to edit, to return press <Enter>.\n\n    Book ID: ";
    private String BODY = "\nPlease add the following information:";
    private String TITLE_PROMPT = "    Title [%s]: ";
    private String AUTHOR_PROMPT = "    Author [%s]: ";
    private String DESCRIPTION_PROMPT = "    Description [%s]: ";
    private String BOOK_SAVED_MESSAGE = "\nBook [%s] saved.\n";

    // MARK: IMenu implementation

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
     *   it will ask user to enter book ID to start editing its details.
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
                break;
            }

            // Convert the userInput to integer
            // then find the book with given id
            try {
                int bookId = Integer.parseInt(userInput);
                Book book = localDiskService.findOneBook(bookId);

                // Display error when user enters invalid book ID
                if (book == null) {
                    Utils.printSelectValidBookIdError();
                    continue;
                }

                // Start editing book details process
                this.readBookEditConsoleInput(book);

                // Update the new details using LocalDiskService
                localDiskService.update(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription());

                // Print success message after update operation is done
                this.printSucccessMessage(book.getId());

            } catch (NumberFormatException e) {
                Utils.printSelectValidBookIdError();
                continue;
            }
        }
    }

    /**
     * It reads the new details (title, author, description) from user input,
     *   then stores update the book object.
     */
    private void readBookEditConsoleInput(Book book) {
        String newTitle = this.readNewFieldValueFromConsoleInput("title", book.getTitle());
        String newAuthor = this.readNewFieldValueFromConsoleInput("author", book.getAuthor());
        String newDescription = this.readNewFieldValueFromConsoleInput("description", book.getDescription());

        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setDescription(newDescription);
    }

    // MARK: Private Helper Methods for readBookEditConsoleInput method

    /**
     * It reads new value for book FieldName from user input,
     *   it displays error if user pressed <Enter>,
     *   or invalid Title value.
     */
    private String readNewFieldValueFromConsoleInput(String fieldName, String oldValue) {
        while (true) {
            this.printNewFieldPrompt(fieldName, oldValue);

            String newValue = consoleReader.nextLine();

            if (BookValidator.validateValue(fieldName, newValue)) {
                newValue = newValue.trim();

                return newValue.equals("") || newValue.equals(oldValue)
                        ? oldValue
                        : newValue;

            } else {
                Utils.printInvalidFieldValue(fieldName);
            }
        }
    }

    // MARK: Prompt related methods

    private void printBookIdPrompt() {
        Utils.print(BOOK_ID_PROMPT);
    }

    private void printNewFieldPrompt(String fieldName, String fieldValue) {
        switch (fieldName) {
            case "title":
                this.printNewTitlePrompt(fieldValue);
                break;

            case "author":
                this.printNewAuthorPrompt(fieldValue);
                break;

            default: // Description
                this.printNewDescriptionPrompt(fieldValue);
                break;
        }
    }

    private void printNewTitlePrompt(String title) {
        Utils.printf(TITLE_PROMPT, title);
    }

    private void printNewAuthorPrompt(String author) {
        Utils.printf(AUTHOR_PROMPT, author);
    }

    private void printNewDescriptionPrompt(String description) {
        Utils.printf(DESCRIPTION_PROMPT, description);
    }

    // MARK: Private Helper Methods

    private void prepareBookList() {
        this.bookList = localDiskService.findBookList();
    }

    private void printSucccessMessage(int updatedBookId) {
        Utils.printf(BOOK_SAVED_MESSAGE, updatedBookId);
    }
}