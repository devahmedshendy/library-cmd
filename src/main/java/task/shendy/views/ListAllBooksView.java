package task.shendy.views;

import task.shendy.Utils;
import task.shendy.console.ConsoleRequest;
import task.shendy.console.ConsoleResponse;
import task.shendy.models.Book;

import java.util.List;

public class ListAllBooksView extends MenuView {

    private final ConsoleRequest request = new ConsoleRequest();

    private final String HEADER         = "\n===== View All Books =====\n";
    private final String BOOK_ID_PROMPT = ""
                                          + "\n\nTo view details enter the book ID, to return press <Enter>."
                                          + "\n\n    Book ID: ";
    private final String BOOK_DETAIL    = "\n    [%d] %s";

    @Override
    public void render() {
        Utils.print(HEADER);

        ConsoleResponse allBooksResponse = request.get("/books");

        if (allBooksResponse.hasError()) {
            Utils.println(allBooksResponse.getError());
            return;
        }

        List<Book> bookList = (List<Book>) allBooksResponse.getData();
        for (Book book : bookList) {
            Utils.printf(BOOK_DETAIL, book.getId(), book.getTitle());
        }

        while (true) {
            Utils.print(BOOK_ID_PROMPT);

            String userInput = consoleReader.nextLine();

            // When user press <Enter>, return immediately
            if (userInput.trim().isEmpty()) {
                return;
            }

            ConsoleResponse booksDetailsResponse = request.get("/books/" + userInput);

            if (booksDetailsResponse.hasError()) {
                Utils.println(booksDetailsResponse.getError());
                return;
            }

            // Print book details
            Book book = (Book) booksDetailsResponse.getData();
            Utils.print(book);
        }
    }
}
