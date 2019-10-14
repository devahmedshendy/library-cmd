package task.shendy.menus;

// MARK: Import Statements

import task.shendy.models.Book;
import task.shendy.Utils;
import task.shendy.services.LocalDiskService;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

// MARK: Class Definition

/**
 * This class is responsible for "Search For a Book" menu:
 *
 * Sample:
 *   ==== Search For a Book (By Title) ====
 *
 *   Type in one or more keywords to search for
 *
 *   Search: moby
 *
 *   The following books matched your query. Enter the book ID to see more details, or <Enter> to return.

 *       [4] Moby Dick
 *
 *   Book ID: 4
 *
 *       ID: 4
 *       Title: Moby Dick
 *       Author: Herman Melville
 *       Description: About a big whale
 *
 *   Book ID: <Enter>
 */
public class SearchForBookMenu implements IMenu {

    // MARK: LocalDiskService Instance

    private LocalDiskService localDiskService = LocalDiskService.getInstance();

    // MARK: Properties

    private Scanner consoleReader = new Scanner(System.in);
    private List<Book> foundBookList = new ArrayList<>();

    // MARK: Menu-related string constants

    private String HEADER = "\n===== Search for a Book (By Title) =====";
    private String SEARCH_QUERY_PROMPT = "\nType in one or more keywords to search for, or press <Enter> to return.\n\n    Search: ";
    private String RESULT_FOUND_MESSAGE = "\nThe following books matched your query: ";
    private String BOOK_ID_PROMPT = "\nEnter the book ID to see more details, or press <Enter> to return.\n\n    Book ID: ";

    // MARK: IMenu implementation

    @Override
    public void runMenu() {
        this.printHeader();
        this.printBody();
        this.readConsoleInput();
    }

    @Override
    public void printHeader() {
        Utils.println(HEADER);
    }

    @Override
    public void printBody() {
        this.readSearchQueryInput();
    }

    /**
     * Ask user to input search keyword, then find a book
     *   which its title contains this keyword and.
     * Display all matched books, otherwise does nothing.
     */
    private void readSearchQueryInput() {
        while (true) {
            this.printSearchInputPrompt();

            String searchKeyword = consoleReader.nextLine();

            // User pressed <Enter>, which means navigate to BookManagerMenu
            if (searchKeyword.equals("")) {
                break;
            }

            this.foundBookList = localDiskService.findBookListByTitle(searchKeyword);

            if (this.foundBookList.isEmpty()) {
                Utils.println("\nNo book matched your query.");

            } else {
                this.printResultFoundMessage();
                Utils.printBookList(this.foundBookList);
                break;
            }
        }
    }

    /**
     * If there are matched books, ask user to enter any ID of the listed books.
     *
     * It displays the book if the user entered any book ID of the search result,
     *   Otherwise it displays error messsage asking the user to enter a valid
     *   of the search result.
     */
    @Override
    public void readConsoleInput() {
        while (!this.foundBookList.isEmpty()) {
            this.printBookIdInputPrompt();

            String userInput = consoleReader.nextLine();

            // User pressed <Enter>, which means navigate back to BookManagerMenu
            if (userInput.equals("")) {
                break;
            }

            // Convert the userInput to integer
            // then find the book with given id
            try {
                int bookId = Integer.parseInt(userInput);
                Book book = getBookFromSearchResult(bookId);

                // Display error when user enters invalid book id
                if (book == null) {
                    Utils.printSelectValidBookIdError();
                    continue;

                    // Print book details
                } else {
                    Utils.println(book);
                }

            } catch (NumberFormatException e) {
                Utils.printSelectValidBookIdError();
                continue;
            }
        }
    }

    // MARK: Prompt related methods

    private void printSearchInputPrompt() {
        Utils.print(SEARCH_QUERY_PROMPT);
    }

    private void printResultFoundMessage() {
        Utils.println(RESULT_FOUND_MESSAGE);
    }

    private void printBookIdInputPrompt() {
        Utils.print(BOOK_ID_PROMPT);
    }

    // MARK: Private Helper Methods

    private Book getBookFromSearchResult(int bookId) {
        return this.foundBookList.stream()
                .filter(book -> book.getId() == bookId)
                .findFirst()
                .orElse(null);
    }
}