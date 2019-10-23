package task.shendy;

import task.shendy.controllers.LibraryController;
import task.shendy.views.LibraryView;

public class LibraryCmd {
    public static void main(String[] args) {
        LibraryView view = new LibraryView();
        LibraryController libraryController = new LibraryController();

        view.run();
    }
}
