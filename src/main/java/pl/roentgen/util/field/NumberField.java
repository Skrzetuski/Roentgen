package pl.roentgen.util.field;

import javafx.scene.control.TextField;

public final class NumberField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("^-|[0-9]*") || text.isEmpty()){
            super.replaceText(start, end, text);
        }
    }

    public Integer getValue(){
        try {
            return Integer.parseInt(super.getText());
        } catch (NumberFormatException e){
            return 0;
        }
    }
}
