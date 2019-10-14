package task.shendy.menus;

// MARK: Import Statements

import task.shendy.Utils;
import task.shendy.services.LocalDiskService;
import task.shendy.validators.BookValidator;

import java.util.Scanner;

// MARK: Class Definition

/**
 * This class is responsible for "Add a Book" menu:
 *
 * Sample:
 *   ==== Add a Book ====
 *
 *   Please enter the following information:
 *
 *       Title: Kite Runner
 *       Author: Khaled Hosseini
 *       Description: Story of Amir, a young boy from Kabul.
 *
 *   Book [6] Saved
 */
public class AddBookMenu implements IMenu {

    // MARK: LocalDiskService Instance

    private LocalDiskService localDiskService = LocalDiskService.getInstance();

    // MARK: Properties

    private Scanner consoleReader = new Scanner(System.in);

    // MARK: Menu-related string constants

    private String HEADER = "\n===== Add a Book =====";
    private String BODY = "\nPlease add the following information: ";
    private String TITLE_PROMPT = "\n    Title: ";
    private String AUTHOR_PROMPT = "    Author: ";
    private String DESCRIPTION_PROMPT = "    Description: ";
    private String BOOK_SAVED_MESSAGE = "\nBook [%s] saved.\n";

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
        Utils.println(BODY);
    }

    /**
     * It reads new books details (title, author, description) from
     *   user input, then store it using LocalDiskService intsance,
     *   then it displays a success message.
     */
    @Override
    public void readConsoleInput() {
        String title = this.readFieldValueFromConsoleInput("title");
        String author = this.readFieldValueFromConsoleInput("author");
        String description = this.readFieldValueFromConsoleInput("description");

        int newBookId = localDiskService.add(title, author, description);

        this.printSucccessMessage(newBookId);
    }

    // MARK: Private Helper Methods for readConsoleInput method

    /**
     * It reads value for book FieldValue from user input,
     *   it displays error if user pressed <Enter>,
     *   or invalid Title value.
     */
    private String readFieldValueFromConsoleInput(String fieldName) {
        while (true) {
            this.printNewFieldPrompt(fieldName);

            String userInput = consoleReader.nextLine();
            userInput.trim();

            if (!userInput.equals("") && BookValidator.validateValue(fieldName, userInput)) {
                return userInput;
            } else {
                Utils.printInvalidFieldValue(fieldName);
            }
        }
    }

    /**
     * It reads value for book Title from user input,
     *   it displays error if user pressed <Enter>,
     *   or invalid Title value.
     */
    // private String readTitleConsoleInput() {
    //     while (true) {
    //         this.printTitlePrompt();

    //         String userInput = consoleReader.nextLine();

    //         if (BookValidator.validateTitle(userInput)) {
    //             return userInput.trim();
    //         } else {
    //             Utils.printInvalidTitleValue();
    //         }
    //     }
    // }

    // /**
    //  * It reads value for book Author from user input,
    //  *   it displays error if user pressed <Enter>
    //  *   or invalid Author value.
    //  */
    // private String readAuthorConsoleInput() {
    //     while (true) {
    //         printAuthorPrompt();

    //         String userInput = consoleReader.nextLine();

    //         if (BookValidator.validateAuthor(userInput)) {
    //             return userInput.trim();
    //         } else {
    //             Utils.printInvalidAuthorValue();
    //         }
    //     }
    // }

    // /**
    //  * It reads value for book Description from user input,
    //  *   it displays error if user pressed <Enter>,
    //  *   or invalid Description value.
    //  */
    // private String readDescriptionConsoleInput() {
    //     while (true) {
    //         printDescriptionPrompt();

    //         String userInput = consoleReader.nextLine();

    //         if (BookValidator.validateDescription(userInput)) {
    //             return userInput.trim();
    //         } else {
    //             Utils.printInvalidDescriptionValue();
    //         }
    //     }
    // }

    // MARK: Prompt related methods

    private void printNewFieldPrompt(String fieldName) {
        switch (fieldName) {
            case "title":
                this.printTitlePrompt();
                break;

            case "author":
                this.printAuthorPrompt();
                break;

            default: // Description
                this.printDescriptionPrompt();
                break;
        }
    }

    private void printTitlePrompt() {
        Utils.print(TITLE_PROMPT);
    }

    private void printAuthorPrompt() {
        Utils.print(AUTHOR_PROMPT);
    }

    private void printDescriptionPrompt() {
        Utils.print(DESCRIPTION_PROMPT);
    }

    // MARK: Private Helper Methods

    private void printSucccessMessage(int newBookId) {
        Utils.printf(BOOK_SAVED_MESSAGE, newBookId);
    }
}