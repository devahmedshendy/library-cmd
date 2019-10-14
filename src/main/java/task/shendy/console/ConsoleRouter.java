package task.shendy.console;

import task.shendy.controllers.LibraryController;

import java.util.Map;

public class ConsoleRouter {

    public ConsoleResponse routeGetRequest(String route) {

        // GET /books/
        if (route.matches("^/books/?$") ) {
            return new LibraryController().findAllBooks();

        // GET /books/{id}
        } else if (route.matches("^/books/\\d+/?$") ) {
            String pathParam = route.replaceAll("/|books", "");
            
            return new LibraryController().findBookById(Integer.parseInt(pathParam));

        // GET /books/keyword={search text}
        } else if (route.matches("^/books\\Q?\\Ekeyword=.*/?$")) {
            String keyword = route.replaceAll("/|books|keyword|\\Q?\\E", "");

            return new LibraryController().searchForBookByTitle(keyword);

        // No matched route
        } else {
            return this.noMatchedRoute(route);
        }
    }

    public ConsoleResponse routePostRequest(String route, Map<String, String> formParams) {

        // POST /books/
        if (route.matches("^/books/?$")) {
            return new LibraryController().addBook(formParams);

        // No matched route
        } else {
            return this.noMatchedRoute(route);
        }
    }

    public ConsoleResponse routePutRequest(String route, Map<String, String> formParams) {

        // PUT /books/{id}
        if (route.matches("^/books/\\d+/?$") ) {
            String pathParam = route.replaceAll("/|books", "");

            return new LibraryController().editBook(Integer.parseInt(pathParam), formParams);

        // No matched route
        } else {
            return this.noMatchedRoute(route);
        }
    }

    private ConsoleResponse noMatchedRoute(String route) {
        return ConsoleResponse.error("ERROR: No matched route '" + route + "'");
    }
}
