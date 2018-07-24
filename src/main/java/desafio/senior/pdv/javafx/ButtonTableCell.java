package desafio.senior.pdv.javafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.layout.StackPane;

class ButtonTableCell<S, T> extends TableCell<S, T> {
	
	private final Button button;
	private final StackPane paddedButton = new StackPane();
	
	public ButtonTableCell(String buttonText, EventHandler<ActionEvent> onAction) {
		button = this.createButton(buttonText, onAction);
		paddedButton.setPadding(new Insets(3));
		paddedButton.getChildren().add(button);
	}
	
	private Button createButton(String buttonText, EventHandler<ActionEvent> onAction) {
		Button btn = new Button(buttonText);
		btn.setOnAction(onAction);
		btn.getStyleClass().addAll("btn", "btn-xs", "btn-info");
		return btn;
	}
	
	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty) {
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			setGraphic(paddedButton);
		} else {
			setGraphic(null);
		}
	}
	
}
