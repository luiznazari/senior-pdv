package desafio.senior.pdv.component;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;

@Component
public class SceneLoader {
	
	@Autowired
	private ResourceBundle resourceBundle;
	
	@Autowired
	private ApplicationContext context;
	
	public FXMLLoader getFxmlLoader(String arquivoFxml) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		// Permite ao JavaFx inicializar os controllers utilizando o conteiner IoC do Spring.
		loader.setControllerFactory(context::getBean);
		loader.setResources(resourceBundle);
		loader.setLocation(this.getClass().getResource(arquivoFxml));
		return loader;
	}
	
}
