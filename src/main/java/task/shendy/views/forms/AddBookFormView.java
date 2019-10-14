package task.shendy.views.forms;

import task.shendy.Utils;

public class AddBookFormView extends FormView {
    private String FORM_TITLE = "\nPlease add the following information: ";
    private String FORM_FIELD_PROMPT = "\n    %s: ";
    private String[] fieldNames = new String[] {"Title", "Author", "Description"};

    @Override
    public void render() {
        Utils.print(FORM_TITLE);

        for (String field : fieldNames) {
            Utils.printf(FORM_FIELD_PROMPT, field);

            String fieldValue = this.consoleReader.nextLine();

            this.addFormParam(field, fieldValue.trim());
        }
    }
}
