package desafio.senior.pdv.javafx;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class NumericTextField extends LimitedTextField {
	
	public NumericTextField() {
		super.addEventFilter(KeyEvent.KEY_TYPED, NumericTextField.filtro());
		super.setMaxLength(16);
	}
	
	@Override
	public void replaceText(int start, int end, String text) {
		if (validate(text)) {
			super.replaceText(start, end, text);
		}
	}
	
	@Override
	public void replaceSelection(String text) {
		if (validate(text)) {
			super.replaceSelection(text);
		}
	}
	
	private boolean validate(String text) {
		return NumberUtils.isParsable(text);
	}
	
	private static EventHandler<KeyEvent> filtro() {
		return event -> {
			TextField textField = ( TextField ) event.getSource();
			String text = textField.getText();
			String somenteNumeros = StringUtils.replaceAll(text, "[^0-9\\.]", StringUtils.EMPTY);
			textField.setText(somenteNumeros);
		};
	}
	
}
