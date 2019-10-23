package task.shendy;

// MARK: Import Statements

import java.util.Collection;

// MARK: Class Definition

/**
 * It's just one place to various or repeated printing needs in the code.
 */
public class Utils {

    public static void print(Object obj) {
        System.out.print(obj);
    }

    public static void print(Object... objects) {
        for (Object obj : objects) {
        System.out.print(obj);
        }
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

    public static void printInvalidMenuId() {
        System.out.println("\nERROR: invalid menu id!");
    }

    public static void printSelectValidBookIdError() {
        System.out.println("\nERROR: please select a valid book id!");
    }

//    public static void printInvalidFieldValue(String fieldName) {
//        switch (fieldName) {
//            case "title":
//                printInvalidTitleValue();
//                break;
//
//            case "author":
//                printInvalidAuthorValue();
//                break;
//
//            default: // Description
//                printInvalidDescriptionValue();
//                break;
//        }
//    }

//    public static void printInvalidTitleValue() {
//        Utils.printf("\nerror: Title field cannot be empty or more than %d long.\n", RecordBufferManager.TITLE_SIZE);
//    }
//
//    public static void printInvalidAuthorValue() {
//        Utils.printf("\nerror: Author field cannot be empty or more than %d long.\n", RecordBufferManager.AUTHOR_SIZE);
//    }
//
//    public static void printInvalidDescriptionValue() {
//        Utils.printf("\nerror: Description field cannot be more than %d long.\n", RecordBufferManager.AUTHOR_SIZE);
//    }

    public static void printCannotReadDatabaseFile() {
        println("\nConsoleResponse.error cannot read database file.\n");
    }

    public static void printCannotWriteToDatabaseFile() {
        println("\nConsoleResponse.error cannot write to database file.\n");
    }

    public static void printBookList(Collection<Book> bookList) {
        for (Book book : bookList) {
            Utils.printf("\n    [%d] %s", book.getId(), book.getTitle());
        }

        println();
    }
}