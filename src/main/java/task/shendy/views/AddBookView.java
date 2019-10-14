package task.shendy.views;

import task.shendy.Utils;
import task.shendy.console.ConsoleRequest;
import task.shendy.console.ConsoleResponse;
import task.shendy.views.forms.AddBookFormView;
import task.shendy.views.forms.FormView;

import java.util.Map;

public class AddBookView extends MenuView {

    private String HEADER = "\n===== Add a Book =====";
//    private String FORM_TITLE_PROMPT = "\n    Title: ";
//    private String FORM_AUTHOR_PROMPT = "\n    Author: ";
//    private String FORM_DESCRIPTION_PROMPT = "\n    Description: ";

    ;

    @Override
    public void render() {

        Utils.print(HEADER);

        FormView addBookForm = new AddBookFormView();
        addBookForm.render();

//        // Get book title from user
//        String title;
//        while (true) {
//            Utils.print(FORM_TITLE_PROMPT);
//
//            title = this.consoleReader.nextLine();
//
//            // When user press <Enter>, return from readConsoleInput immediately
//            if (title.trim().isEmpty()) {
//                Utils.println("\nERROR: Title is required!");
//            } else {
//                break;
//            }
//        }
//
//        // Get book author from user
//        String author;
//        while (true) {
//            Utils.print(FORM_AUTHOR_PROMPT);
//
//            author = this.consoleReader.nextLine();
//
//            // When user press <Enter>, return from readConsoleInput immediately
//            if (author.trim().isEmpty()) {
//                Utils.println("\nERROR: Author is required!");
//            } else {
//                break;
//            }
//        }
//
//        // Get book description from user
//        Utils.print(FORM_DESCRIPTION_PROMPT);
//
//        String description = this.consoleReader.nextLine();

        // Get add-book form params
        Map<String, String> formParams = addBookForm.getFormParams();

        // Make post request to add-book route
        ConsoleResponse addBookResponse = new ConsoleRequest().post("/books", formParams);

        if (addBookResponse.hasError()) {
            Utils.println(addBookResponse.getError());
        } else {
            Utils.println(addBookResponse.getMessage());
        }

    }

//    @Override
//    public void render() {
//
//        Utils.print(HEADER, FORM_BODY);
//
//        // Get book title from user
//        String title;
//        while (true) {
//            Utils.print(FORM_TITLE_PROMPT);
//
//            title = this.consoleReader.nextLine();
//
//            // When user press <Enter>, return from readConsoleInput immediately
//            if (title.trim().isEmpty()) {
//                Utils.println("\nERROR: Title is required!");
//            } else {
//                break;
//            }
//        }
//
//        // Get book author from user
//        String author;
//        while (true) {
//            Utils.print(FORM_AUTHOR_PROMPT);
//
//            author = this.consoleReader.nextLine();
//
//            // When user press <Enter>, return from readConsoleInput immediately
//            if (author.trim().isEmpty()) {
//                Utils.println("\nERROR: Author is required!");
//            } else {
//                break;
//            }
//        }
//
//        // Get book description from user
//        Utils.print(FORM_DESCRIPTION_PROMPT);
//
//        String description = this.consoleReader.nextLine();
//
//        // Build add-book form params
//        Map<String, String> formParams = new HashMap<>();
//        formParams.put("title", title);
//        formParams.put("author", author);
//        formParams.put("description", description);
//
//        // Make post request to add-book route
//        ConsoleResponse addBookResponse = new ConsoleRequest().post("/books", formParams);
//
//        if (addBookResponse.hasError()) {
//            Utils.println(addBookResponse.getError());
//        } else {
//            Utils.println(addBookResponse.getMessage());
//        }
//
//    }
}
