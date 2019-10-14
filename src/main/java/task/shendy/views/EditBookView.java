package task.shendy.views;

import task.shendy.Utils;
import task.shendy.console.ConsoleRequest;
import task.shendy.console.ConsoleResponse;
import task.shendy.models.Book;
import task.shendy.views.forms.EditBookFormView;
import task.shendy.views.forms.FormView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditBookView extends MenuView {

    private final ConsoleRequest request = new ConsoleRequest();

    private String HEADER            = "\n===== Edit a Book =====";
    private String BOOK_ID_PROMPT    = ""
                                       + "\nTo edit book enter the book ID, to return press <Enter>."
                                       + "\n\n    Book ID: ";
    private final String BOOK_DETAIL = "\n    [%d] %s";

    @Override
    public void render() {

        Utils.print(HEADER);

        ConsoleResponse allBooksResponse = request.get("/books");

        if (allBooksResponse.hasError()) {
            Utils.println(allBooksResponse.getError());
            return;
        }

        List<Book> bookList = (List<Book>) allBooksResponse.getData();

        if (bookList.isEmpty()) {
            Utils.print(allBooksResponse.getMessage());
            return;
        }

        for (Book book : bookList) {
            Utils.printf(BOOK_DETAIL, book.getId(), book.getTitle());
        }

        while (true) {
            Utils.print(BOOK_ID_PROMPT);

            String userInput = this.consoleReader.nextLine();

            // When user press <Enter>, return from readConsoleInput immediately
            if (userInput.trim().isEmpty()) {
                return;
            }

            ConsoleResponse findBookResponse = request.get("/books/" + userInput);

            if (findBookResponse.hasError()) {
                Utils.println(findBookResponse.getError());
                continue;
            }

            Book book = (Book) findBookResponse.getData();

            String oldTitle = book.getTitle();
            String oldAuthor = book.getAuthor();
            String oldDescription = book.getDescription();

            Map<String, String> oldValues = new HashMap<>() {{
                put("title", oldTitle);
                put("author", oldAuthor);
                put("description", oldDescription);
            }};

            Utils.print(oldValues.get("title"));

            FormView editBookForm = new EditBookFormView(oldValues);
            editBookForm.render();

            // Get edit-book form params
            Map<String, String> formParams = editBookForm.getFormParams();

            ConsoleResponse editBookResponse = new ConsoleRequest().put("/books/" + book.getId(), formParams);

            if (editBookResponse.hasError()) {
                Utils.print(editBookResponse.getError());
            } else {
                Utils.print(editBookResponse.getMessage());
                return;
            }
        }
    }
}
