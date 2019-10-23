package task.shendy;

import java.util.*;

public class LibraryView {

    // NOTE: This will be removed later, just for demo
    private SortedMap<String, Book> library = new TreeMap<>();

    public LibraryView() {
        Book book = new Book(1, "Title 1", "Author 1", "Description 1");
        library.put(book.getId().toString(), book);

        book = new Book(2, "Title 2", "Author 2", "Description 2");
        library.put(book.getId().toString(), book);

        book = new Book(3, "Title 3", "Author 3", "Description 3");
        library.put(book.getId().toString(), book);

    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        String header = "\n===== Library Manager =====\n";
        String body = ""
                + "\n    1) List All Books."
                + "\n    2) Add a Book."
                + "\n    3) Edit a Book."
                + "\n    4) Search for a Book (By Title)."
                + "\n    5) Save and Exit.";
        String choosePrompt = "\n\nChoose [1-5]: ";

        String userChoice;

        while(true) {
            Utils.print(header, body, choosePrompt);

            userChoice = scanner.nextLine();

            switch (userChoice.trim()) {
                case "1":
                    this.showListAllBooksView();
                    break;

                // TODO
//                case "2":
//                case "3":
//                case "4":
//
                case "5":
                    Utils.println("Library saved!");
                    return;

                default: // Invalid Menu ID
                    Utils.printInvalidMenuId();
            }
        }
    }

    private void showListAllBooksView() {
        Scanner scanner = new Scanner(System.in);

        String header = "\n===== View All Books =====\n";
        String bookIdPrompt = ""
                + "\n\nTo view details enter the book ID, to return press <Enter>."
                + "\n\n    Book ID: ";
        String bookDetails = "\n    %s: %s1";

        Utils.print(header);
        Utils.printBookList(new ArrayList<>(library.values()));

        String userInput;
        while (true) {
            Utils.print(bookIdPrompt);

            userInput = scanner.nextLine();

            if (userInput.trim().isEmpty()) {
                return;
            }


            Book book = this.library.get(userInput);

            if (book == null) {
                Utils.printf("\nERROR: No such book with id %s", userInput);
                continue;
            }

            Utils.printf(bookDetails, "Title", book.getTitle());
            Utils.printf(bookDetails, "Author", book.getAuthor());
            Utils.printf(bookDetails, "Description", book.getDescription());
        }
    }
}
