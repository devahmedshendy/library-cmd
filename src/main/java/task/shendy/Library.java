package task.shendy;

// MARK: Import Statements

import task.shendy.menus.MenuNavigator;
import task.shendy.services.LocalDiskService;

// MARK: Class Definition

/**
 * This is the Main-Class of this java application.
 *
 * It only create shutdown hook, and start MenuNavigator
 */
public class Library {

    // MARK: LocalDiskService Instance

    private static LocalDiskService localDiskService = LocalDiskService.getInstance();

    /// MARK:
    public static void main(String[] args) {
        enableShutdownHook();
        start();
    }

    // MARK: Private Helper Methods

    /**
     * Whenever application is shutting down, it should delete the lock file
     */
    private static void enableShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                localDiskService.deleteLockFile();
            }
        });
    }

    /**
     * Starts the menu navigator
     */
    private static void start() {
        MenuNavigator.start();
    }
}