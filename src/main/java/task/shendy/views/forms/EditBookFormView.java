package task.shendy.views.forms;

import task.shendy.Utils;

import java.util.Map;

public class EditBookFormView extends FormView {
    private String FORM_TITLE = "\nPlease add the following information: ";
    private String FORM_FIELD_PROMPT = "\n    %s [%s]: ";
    private String[] fieldNames = new String[] {"Title", "Author", "Description"};
    private Map<String, String> oldValues;

    public EditBookFormView(Map<String, String> oldValues) {
        this.oldValues = oldValues;
    }

    @Override
    public void render() {
        Utils.print(FORM_TITLE);

        for (String fieldName : fieldNames) {
            Utils.printf(FORM_FIELD_PROMPT, fieldName, this.oldValues.get(fieldName.toLowerCase()));

            String fieldValue = this.consoleReader.nextLine();

            if (fieldValue.trim().isEmpty()) {
                this.addFormParam(fieldName, this.oldValues.get((fieldName)));
            } else {
                this.addFormParam(fieldName, fieldValue.trim());
            }
        }
    }
}
