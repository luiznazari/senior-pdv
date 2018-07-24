package desafio.senior.pdv.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import desafio.senior.pdv.component.Scenes;
import javafx.fxml.Initializable;

public abstract class FxController implements Initializable {
	
	/*
	 * Caso não seja lazy, o Spring irá tentar instanciar na inicialização da aplicação,
	 * quando o Stage ainda não estará criado, resultando em erros de injeção de dependência.
	 */
	@Lazy
	@Autowired
	protected Scenes scenes;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Nada a fazer.
		// Remove a obrigatoriedade de implementar o método vazio em controllers.
	}
	
	public void onCancelar() {
		scenes.alterarTela(this.getClass());
	}
	
}
