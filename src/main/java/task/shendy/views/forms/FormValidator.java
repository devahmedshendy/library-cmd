package task.shendy.views.forms;


import task.shendy.views.enums.FormFieldProperty;

import java.util.List;

import static task.shendy.views.enums.FormFieldProperty.*;

public class FormValidator {

    public boolean validate(String fieldValue, List<FormFieldProperty> fieldProperties) {
        boolean isValid = true;
        for (FormFieldProperty fieldProperty : fieldProperties) {
            if (fieldProperty == IS_REQUIRED) {
                isValid = !fieldValue.trim().isEmpty();
            }
        }

        return isValid;
    }
}
