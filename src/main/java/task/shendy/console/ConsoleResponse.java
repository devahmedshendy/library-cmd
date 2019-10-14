package task.shendy.console;

public class ConsoleResponse<T> {

    // MARK: Properties

    private T data;
    private String message = "";
    private String error = "";

    // MARK: Constructors

    private ConsoleResponse() { }

    private ConsoleResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    private ConsoleResponse(String error) {
        this.error = error;
    }

    // MARK: Static Methods for Creating Meaningful Response

    public static ConsoleResponse<Void> ok() {
        return new ConsoleResponse<>();
    }

    public static <R> ConsoleResponse<R> ok(String message) {
        return new ConsoleResponse<R>(null, message);
    }

    public static <R> ConsoleResponse<R> ok(R data) {
        return new ConsoleResponse<R>(data, "");
    }

    public static <R> ConsoleResponse<R> ok(R data, String message) {
        return new ConsoleResponse<R>(data, message);
    }

    public static ConsoleResponse<Void> error(String message) {
        return new ConsoleResponse<>(message);
    }

    // MARK: Getters

    public String getError() { return this.error; }

    public T getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    // MARK: Helper Methods

    public boolean hasError() {
        return !this.error.isEmpty();
    }
}
