package task.shendy.validators;

import task.shendy.services.RecordBufferManager;

public class BookValidator {

    public static boolean validateValue(String fieldName, String fieldValue) {
        switch (fieldName.trim()) {
            case "title":
                return validateTitle(fieldValue);

            case "author":
                return validateAuthor(fieldValue);

            default: // "description";
                return validateDescription(fieldValue);
        }
    }

    private static boolean validateTitle(String title) {
        return title.length() <= RecordBufferManager.TITLE_SIZE;
    }

    private static boolean validateAuthor(String author) {
        return author.length() <= RecordBufferManager.AUTHOR_SIZE;
    }

    private static boolean validateDescription(String description) {
        return description.length() <= RecordBufferManager.DESCRIPTION_SIZE;
    }
}