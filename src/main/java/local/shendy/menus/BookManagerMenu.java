package local.shendy.menus;

// MARK: Import Statements

import local.shendy.Utils;

import java.util.List;
import java.util.Scanner;
import java.util.Collections;

// MARK: Class Definition

/**
 * This class is responsible for "Book Manager" menu:
 *
 * Sample:
 *   ==== Book Manager ====
 *
 *       1) View all books
 *       2) Add a book
 *       3) Edit a book
 *       4) Search for a book
 *       5) Save and exit
 *
 *   Choose [1-5]: 4
 */
public class BookManagerMenu implements IMenu {

    // MARK: Properties

    private Scanner consoleReader = new Scanner(System.in);;
    private IMenu nextMenu;

    // MARK: Menu-related string constants

    private final String HEADER = "\n===== Book Manager =====";
    private final String BODY = "\n    1) View All Books."
            + "\n    2) Add a Book."
            + "\n    3) Edit a Book."
            + "\n    4) Search for a Book (By Title)."
            + "\n    5) Save and Exit.";
    private final String CHOOSE_MENU_PROMPT = "\nChoose [1-5]: ";

    private final List<String> MENU_ID_LIST = Collections.unmodifiableList(List.of("1", "2", "3", "4", "5"));

    // MARK: IMenu implementation

    @Override
    public void runMenu() {
        this.printHeader();
        this.printBody();
        this.readConsoleInput();
    }

    // This will print menu header string
    @Override
    public void printHeader() {
        Utils.println(HEADER);
    }

    // This will print menu body string
    @Override
    public void printBody() {
        Utils.println(BODY);
    }

    /**
     * I will prompt user asking for menu id to navigate to.
     *
     * It will create an object for the selected menu, and store it,
     *   Otherwise it will display error message asking user to write
     *   valid menu id.
     */
    @Override
    public void readConsoleInput() {
        while (true) {
            printInputPrompt();

            String selectedMenuId = consoleReader.nextLine();

            if (MENU_ID_LIST.contains(selectedMenuId)) {
                this.setNextMenu(selectedMenuId);

                break;
            } else {
                Utils.printSelectValidMenuError();
            }
        }
    }

    // MARK: Public Methods

    public IMenu getNextMenu() {
        return nextMenu;
    }

    // MARK: Prompt related methods

    private void printInputPrompt() {
        Utils.print(CHOOSE_MENU_PROMPT);
    }

    // MARK: Private Helper Methods

    /**
     * It will prepare an object for the menu corresponding to
     *   the passed menuId.
     */
    private void setNextMenu(String menuId) {
        switch (menuId) {
            case "1":
                nextMenu = new ViewAllBooksMenu();
                break;

            case "2":
                nextMenu = new AddBookMenu();
                break;

            case "3":
                nextMenu = new EditBookMenu();
                break;

            case "4":
                nextMenu = new SearchForBookMenu();
                break;

            default:
                nextMenu = new SaveAndExitMenu();
                break;
        }
    }
}