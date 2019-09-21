package local.shendy.menus;

// MARK: Import Statements

import java.util.Stack;

// MARK: Class Definition

/**
 * It is responsbile for navigation back and forth between menus
 */
public class MenuNavigator {

    // MARK: Properties

    private static Stack<IMenu> menuStack = new Stack<>();

    // MARK: Public Methods

    /**
     * This will start the menu navigator.
     * It should start with BookManagerMenu, and using stacking approach to
     *   move back and forth between menus.
     *
     * This navigator won't stop, the last menu (SaveAndExitMenu) will save
     *   changes to disk and exit.
     */
    public static void start() {
        menuStack.push(new BookManagerMenu());

        while(true) {
            IMenu currentMenu = MenuNavigator.menuStack.peek();

            currentMenu.runMenu();

            // We need to navigate to next menu if the current menu is BookManagerMenu
            if (currentMenu instanceof BookManagerMenu) {
                BookManagerMenu bookManagerMenu = (BookManagerMenu) currentMenu;
                IMenu nextMenu = bookManagerMenu.getNextMenu();

                navigateNext(nextMenu);

            } else {
                navigateBack();
            }
        }
    }

    /**
     * Navigate to next menu by adding it to the top of the menuStack
     */
    private static void navigateNext(IMenu menu) {
        MenuNavigator.menuStack.push(menu);
    }

    /**
     * Navigate to previous menu by removing the top menu from the menuStack
     */
    private static void navigateBack() {
        MenuNavigator.menuStack.pop();
    }
}