package desafio.senior.pdv.component;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import desafio.senior.pdv.Constantes;
import desafio.senior.pdv.controller.FxController;
import desafio.senior.pdv.javafx.MenuBarCreator;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Componente responsável por gerenciar a criação de cenas/views e manipulação de telas do JavaFx.
 * 
 * @author Luiz Felipe Nazari
 */
public class Scenes {
	
	private final Stage stage;
	private final SceneLoader sceneLoader;
	
	public Scenes(SceneLoader sceneLoader, Stage stage) {
		this.stage = stage;
		this.sceneLoader = sceneLoader;
		this.inicializarStage();
	}
	
	private void inicializarStage() {
		Image icone = new Image(Scenes.class.getResourceAsStream(Constantes.IMG.ICONE));
		stage.setTitle(Constantes.TITULO_APLICACAO);
		stage.getIcons().add(icone);
		stage.centerOnScreen();
		stage.setResizable(false);
	}
	
	public <C extends FxController> void alterarTela(final Class<C> controller) {
		Parent rootNode = carregarSceneDoController(controller, null);
		abrirTela(rootNode);
	}
	
	public <C extends FxController> void alterarTela(final Class<C> controller, Consumer<C> consumer) {
		Parent rootNode = carregarSceneDoController(controller, consumer);
		abrirTela(rootNode);
	}
	
	private <C extends FxController> Parent carregarSceneDoController(Class<C> controller,
		Consumer<C> consumer) {
		try {
			String fxml = getFxmlDoController(controller);
			FXMLLoader fxmlLoader = sceneLoader.getFxmlLoader(fxml);
			Parent node = fxmlLoader.load();
			if (consumer != null) {
				consumer.accept(fxmlLoader.getController());
			}
			return node;
			
		} catch (IOException e) {
			Platform.exit();
			throw new SceneLoaderException("Houve um problema ao carregar cena para " + controller, e);
		}
	}
	
	/**
	 * Procura a view referente ao controller respeitando a convenção de nomes.
	 * Ex.:
	 * 
	 * <pre>
	 * LoginController -> login.jxml
	 * UsuarioPerfilController -> usuarioPerfil.jxml
	 * </pre>
	 * 
	 * @param controller
	 * @return o caminho para o recurso .jrxml referente ao controller.
	 */
	private String getFxmlDoController(Class<? extends FxController> controller) {
		String nomeController = StringUtils.uncapitalize(controller.getSimpleName());
		nomeController = nomeController.replace("Controller", StringUtils.EMPTY);
		return Constantes.FXML.getCaminho(nomeController);
	}
	
	private void abrirTela(final Parent rootNode) {
		new MenuBarCreator(this).criarMenuPara(rootNode);
		Scene scene = criarScenePara(rootNode);
		
		stage.setScene(scene);
		stage.sizeToScene();
		
		if (!stage.isShowing()) {
			stage.show();
		}
	}
	
	private Scene criarScenePara(Parent rootNode) {
		Scene scene = Optional.ofNullable(stage.getScene())
			.orElseGet(() -> new Scene(rootNode));
		scene.setRoot(rootNode);
		scene.getStylesheets().add(carregarRecurso(Constantes.CSS.FEXTILE));
		scene.getStylesheets().add(carregarRecurso(Constantes.CSS.PRINCIPAL));
		return scene;
	}
	
	private static String carregarRecurso(String caminhoArquivo) {
		return Scenes.class.getResource(caminhoArquivo).toExternalForm();
	}
	
}
