package task.shendy.models;

public class Book {

    // MARK: Properties

    private Integer id;
    private String title;
    private String author;
    private String description;

    // MARK: Constructors

    public Book() {
        this(0, "", "", "");
    }

    public Book(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public Book(Integer id, String title, String author, String description) {
        this(title, author, description);
        this.id = id;
    }

    // MARK: Getters

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // MARK: Overridden Methods

    @Override
    public String toString() {
        return String.format(
                "\n    ID: %s"
                        + "\n    Title: %s"
                        + "\n    Author: %s"
                        + "\n    Description: %s"
                , this.id, this.title, this.author, this.description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Book) {
            Book otherBook = (Book) obj;

            if (id == otherBook.id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}