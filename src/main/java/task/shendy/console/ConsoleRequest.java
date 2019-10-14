package task.shendy.console;

import java.util.Map;

public class ConsoleRequest {
    private ConsoleRouter consoleRouter = new ConsoleRouter();

    public ConsoleResponse get(String route) {
        return consoleRouter.routeGetRequest(route);
    }

    public ConsoleResponse post(String route, Map<String, String> params) {
        return consoleRouter.routePostRequest(route, params);
    }

    public ConsoleResponse put(String route, Map<String, String> params) {
        return consoleRouter.routePutRequest(route, params);
    }

}
