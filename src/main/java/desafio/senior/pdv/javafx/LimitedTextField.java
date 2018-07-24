package desafio.senior.pdv.javafx;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class LimitedTextField extends TextField {
	
	private static final Integer DEFAULT_LENGTH = 256;
	
	private Integer maxLength = DEFAULT_LENGTH;
	private EventHandler<KeyEvent> filtro;
	
	public Integer getMaxLength() {
		return maxLength;
	}
	
	public void setMaxLength(Integer maxLength) {
		this.maxLength = Optional.ofNullable(maxLength).orElse(DEFAULT_LENGTH);
		this.adicionarFiltro();
	}
	
	private void adicionarFiltro() {
		if (this.filtro != null) {
			super.removeEventFilter(KeyEvent.KEY_TYPED, this.filtro);
		}
		
		this.filtro = LimitedTextField.filtro(this.maxLength);
		super.addEventFilter(KeyEvent.KEY_TYPED, this.filtro);
	}
	
	private static EventHandler<KeyEvent> filtro(final Integer maxLengh) {
		return event -> {
			TextField textField = ( TextField ) event.getSource();
			if (StringUtils.length(textField.getText()) >= maxLengh) {
				event.consume();
			}
		};
	}
	
}
