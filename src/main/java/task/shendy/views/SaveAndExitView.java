package task.shendy.views;

import task.shendy.Utils;

public class SaveAndExitView extends MenuView {

    private final String HEADER = "\n===== Save and Exit =====";
    private final String CONFIRM_PROMPT = "\n\nConfirm [y/N]: ";
    private String userChoice;

    @Override
    public void render() {
        Utils.print(HEADER);

        while (true) {
            Utils.print(CONFIRM_PROMPT);

            String userChoice = this.consoleReader.nextLine();

            switch (userChoice.trim().toLowerCase()) {
                case "yes": case "y":
                    System.exit(0);
                    break;
                case "no": case "n": case "":
                    return;
                default:
                    Utils.printInvalidMenuId();
            }
        }
    }
}
