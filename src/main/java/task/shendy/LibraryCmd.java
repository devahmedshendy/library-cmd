package task.shendy;

public class LibraryCmd {
    public static void main(String[] args) {
        Book model = new Book();

        LibraryView view = new LibraryView();

        LibraryController libraryController = new LibraryController(model, view);

        view.run();
    }
}
