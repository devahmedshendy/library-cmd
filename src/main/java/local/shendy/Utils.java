package local.shendy;

// MARK: Import Statements

import local.shendy.models.Book;
import local.shendy.services.RecordBufferManager;

import java.util.Collection;

// MARK: Class Definition

/**
 * It's just one place to various or repeated printing needs in the code.
 */
public class Utils {

    public static void print(Object obj) {
        System.out.print(obj);
    }

    public static void println() {
        System.out.println();
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void printf(String string, Object ... args) {
        System.out.format(string, args);
    }

    public static void printSelectValidMenuError() {
        System.out.println("\nerror: please select a valid menu!");
    }

    public static void printSelectValidBookIdError() {
        System.out.println("\nerror: please select a valid book id!");
    }

    public static void printInvalidFieldValue(String fieldName) {
        switch (fieldName) {
            case "title":
                printInvalidTitleValue();
                break;

            case "author":
                printInvalidAuthorValue();
                break;

            default: // Description
                printInvalidDescriptionValue();
                break;
        }
    }

    public static void printInvalidTitleValue() {
        Utils.printf("\nerror: Title field cannot be empty or more than %d long.\n", RecordBufferManager.TITLE_SIZE);
    }

    public static void printInvalidAuthorValue() {
        Utils.printf("\nerror: Author field cannot be empty or more than %d long.\n", RecordBufferManager.AUTHOR_SIZE);
    }

    public static void printInvalidDescriptionValue() {
        Utils.printf("\nerror: Description field cannot be more than %d long.\n", RecordBufferManager.AUTHOR_SIZE);
    }

    public static void printCannotReadDatabaseFile() {
        println("\nerror: cannot read database file.\n");
    }

    public static void printCannotWriteToDatabaseFile() {
        println("\nerror: cannot write to database file.\n");
    }

    public static void printBookList(Collection<Book> bookList) {
        for (Book book : bookList) {
            Utils.printf("\n    [%d] %s", book.getId(), book.getTitle());
        }

        println();
    }
}