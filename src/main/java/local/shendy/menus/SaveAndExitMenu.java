package local.shendy.menus;

// MARK: Import Statements

import local.shendy.Utils;
import local.shendy.services.LocalDiskService;

// MARK: Class Definition

/**
 * This class will be called once the user select menu no. 5
 *
 * Sample:
 *   ==== Book Manager ====
 *
 *     1) View all books
 *     2) Add a book
 *     3) Edit a book
 *     4) Search for a book
 *     5) Save and exit
 *
 *   Choose [1-5]: 5
 *
 *   Library saved.
 */
public class SaveAndExitMenu implements IMenu {

    // MARK: LocalDiskService Instance

    private LocalDiskService localDiskService = LocalDiskService.getInstance();

    // MARK: IMenu interface implementation

    @Override
    public void runMenu() {
        this.printBody();
    }

    /**
     * It will ask LocalDiskService to write all changes (Add/Edit)
     *   to disk, then display success message, then exit the app
     *   with code error 0, if LocalDiskSpace fails for any reason,
     *   it will exit with code 2
     */
    @Override
    public void printBody() {
        // If writing changes to disk succeed
        if (this.localDiskService.writeChangesToDisk()) {
            Utils.println("\nLibrary saved.\n");
            System.exit(0);

        } else {
            System.exit(2);
        }
    }

    // This has no implementation for these methods.
    @Override public void printHeader() {}
    @Override public void readConsoleInput() {}
}