package desafio.senior.pdv.javafx;

import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javafx.event.EventHandler;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class NumericTextField extends LimitedTextField {
	
	private static final int[] CODIGOS_PERMITIDOS;
	
	private static final Pattern PONTO = Pattern.compile("\\.");
	private static final Pattern CARACTERES_NAO_PERMITIDOS = Pattern.compile("[^0-9\\.]");
	
	static {
		int delete = 127;
		int backspace = 8;
		CODIGOS_PERMITIDOS = new int[] { delete, backspace };
	}
	
	public NumericTextField() {
		super.addEventFilter(KeyEvent.KEY_TYPED, NumericTextField.filtro());
		super.setMaxLength(16);
	}
	
	@Override
	public void replaceText(int start, int end, String replaceText) {
		if (this.isReplaceValido(start, end, replaceText)) {
			super.replaceText(start, end, replaceText);
		}
	}
	
	@Override
	public void replaceText(IndexRange selection, String replaceText) {
		if (this.isReplaceValido(selection.getStart(), selection.getEnd(), replaceText)) {
			super.replaceText(selection, replaceText);
		}
	}
	
	private boolean isReplaceValido(int start, int end, String replaceText) {
		String text = this.getText();
		String replaced = new StringBuilder(text).replace(start, end, replaceText).toString();
		return NumberUtils.isCreatable(replaced);
	}
	
	private static EventHandler<KeyEvent> filtro() {
		return event -> {
			int keyCode = event.getCharacter().toCharArray()[0];
			boolean keyCodePermitido = ArrayUtils.contains(CODIGOS_PERMITIDOS, keyCode);
			boolean caracterPermitido = !CARACTERES_NAO_PERMITIDOS.matcher(event.getCharacter()).find();
			
			if (!(keyCodePermitido || caracterPermitido)) {
				event.consume();
				return;
			}
			
			TextField textField = ( TextField ) event.getSource();
			final String text = textField.getText();
			
			if (StringUtils.isNotEmpty(text)) {
				boolean ponto = PONTO.matcher(event.getCharacter()).find();
				boolean contemPonto = PONTO.matcher(text).find();
				
				if (ponto && contemPonto) {
					event.consume();
				}
			}
		};
	}
	
}
