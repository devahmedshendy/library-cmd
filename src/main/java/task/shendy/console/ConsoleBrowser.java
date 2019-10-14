package task.shendy.console;

import task.shendy.Utils;
import task.shendy.views.*;

import java.util.Scanner;

public class ConsoleBrowser {
    private final Scanner consoleReader = new Scanner(System.in);

    private final String HEADER = "\n===== Library Manager =====\n";
    private final String BODY = ""
            + "\n    1) List All Books."
            + "\n    2) Add a Book."
            + "\n    3) Edit a Book."
            + "\n    4) Search for a Book (By Title)."
            + "\n    5) Save and Exit.";
    private final String CHOOSE_PROMPT = "\n\nChoose [1-5]: ";

    public void start() {
        while (true) {
            Utils.print(HEADER, BODY, CHOOSE_PROMPT);

            String userChoice = this.consoleReader.nextLine();

            MenuView view;
            switch (userChoice.trim()) {
                case "1":
                    view = new ListAllBooksView();
                    break;

                case "2":
                    view = new AddBookView();
                    break;

                case "3":
                    view = new EditBookView();
                    break;

                case "4":
                    view = new SearchForBookView();
                    break;

                case "5":
                    view = new SaveAndExitView();
                    break;

                default: // Invalid Menu ID
                    Utils.printInvalidMenuId();
                    continue;
            }

            view.render();
        }
    }
}
