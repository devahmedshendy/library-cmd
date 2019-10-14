package task.shendy.views.forms;

import task.shendy.views.MenuView;

import java.util.*;

public abstract class FormView extends MenuView {
    private Map<String, String> formParams = new HashMap<>();

    void addFormParam(String param, String value) {
        this.formParams.put(param.toLowerCase(), value);
    }

    public Map<String, String> getFormParams() {
        return Collections.unmodifiableMap(this.formParams);
    }
}
