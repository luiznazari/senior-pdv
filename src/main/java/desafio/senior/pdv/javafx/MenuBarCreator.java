package desafio.senior.pdv.javafx;

import java.util.ResourceBundle;

import desafio.senior.pdv.Constantes;
import desafio.senior.pdv.component.Scenes;
import desafio.senior.pdv.component.UsuarioLogadoHolder;
import desafio.senior.pdv.config.NullSafeResourceBundle;
import desafio.senior.pdv.controller.CadastroProdutoController;
import desafio.senior.pdv.controller.FxController;
import desafio.senior.pdv.controller.ListaDocumentoController;
import desafio.senior.pdv.controller.ListaProdutoController;
import desafio.senior.pdv.controller.LoginController;
import desafio.senior.pdv.controller.VendaController;
import desafio.senior.pdv.controller.VendasRealizadasController;
import desafio.senior.pdv.model.Usuario;
import desafio.senior.pdv.service.LoginService;
import desafio.senior.pdv.utils.Alertas;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/*
 * TODO Criar MenuBarBuilder para construção fluente e automatizada de menus,
 * com base nos controllers e perfis do usuário, eliminando IFs de perfil.
 */
public class MenuBarCreator {
	
	private Scenes scenes;
	private Usuario usuario;
	private boolean vendedor = false;
	private boolean administrador = false;
	private ResourceBundle resourceBundle = NullSafeResourceBundle.getInstance();
	
	public MenuBarCreator(Scenes scenes) {
		this.scenes = scenes;
		this.usuario = UsuarioLogadoHolder.get();
		
		if (this.usuario != null) {
			this.vendedor = usuario.isVendedor();
			this.administrador = usuario.isAdministrador();
		}
	}
	
	public void criarMenuPara(Parent rootNode) {
		if (this.isBorderPane(rootNode)) {
			BorderPane borderPane = ( BorderPane ) rootNode;
			borderPane.setTop(this.criarMenu());
		}
	}
	
	private boolean isBorderPane(Parent rootNode) {
		return rootNode instanceof BorderPane;
	}
	
	private MenuBar criarMenu() {
		MenuBar menuBar = new MenuBar();
		
		menuBar.getMenus().add(this.criarMenuPrincipal());
		
		if (usuario != null) {
			menuBar.getMenus().add(this.criarMenuVendas());
		}
		
		if (administrador) {
			menuBar.getMenus().add(this.criarMenuProdutos());
		}
		
		menuBar.getMenus().add(this.criarMenuAjuda());
		
		return menuBar;
	}
	
	private Menu criarMenuPrincipal() {
		Menu seniorMenu = new Menu();
		
		Image imgLogo = new Image(Scenes.class.getResourceAsStream(Constantes.IMG.LOGO_BRANCO));
		ImageView imageViewLogo = new ImageView(imgLogo);
		imageViewLogo.setFitWidth(76.0);
		imageViewLogo.setFitHeight(25.0);
		seniorMenu.setGraphic(imageViewLogo);
		
		if (this.usuario != null) {
			MenuItem logout = this.criarMenuItem("trocar.usuario");
			logout.setOnAction(event -> {
				LoginService.logout();
				scenes.alterarTela(LoginController.class);
			});
			seniorMenu.getItems().add(logout);
			seniorMenu.getItems().add(new SeparatorMenuItem());
		}
		
		MenuItem sair = this.criarMenuItem("sair");
		sair.setOnAction(event -> Platform.exit());
		seniorMenu.getItems().add(sair);
		
		return seniorMenu;
	}
	
	private Menu criarMenuVendas() {
		Menu vendas = this.criarMenu("vendas");
		
		if (vendedor) {
			vendas.getItems().add(this.criarMenuItem("nova.venda", VendaController.class));
		}
		if (administrador) {
			vendas.getItems().add(this.criarMenuItem("lista.vendas", ListaDocumentoController.class));
			vendas.getItems().add(this.criarMenuItem("vendas.realizadas", VendasRealizadasController.class));
		}
		
		return vendas;
	}
	
	private Menu criarMenuProdutos() {
		Menu vendas = this.criarMenu("produtos");
		
		vendas.getItems().add(this.criarMenuItem("lista", ListaProdutoController.class));
		vendas.getItems().add(this.criarMenuItem("adicionar", CadastroProdutoController.class));
		
		return vendas;
	}
	
	private MenuItem criarMenuItem(String chaveTitulo, Class<? extends FxController> controller) {
		MenuItem menuItem = this.criarMenuItem(chaveTitulo);
		menuItem.setOnAction(event -> scenes.alterarTela(controller));
		return menuItem;
	}
	
	private Menu criarMenuAjuda() {
		Menu ajuda = this.criarMenu("ajuda");
		
		MenuItem sobre = this.criarMenuItem("sobre");
		sobre.setOnAction(event -> Alertas.sobre());
		
		ajuda.getItems().add(sobre);
		
		return ajuda;
	}
	
	private Menu criarMenu(String chaveTitulo) {
		return new Menu(resourceBundle.getString(chaveTitulo));
	}
	
	private MenuItem criarMenuItem(String chaveTitulo) {
		return new MenuItem(resourceBundle.getString(chaveTitulo));
	}
	
}
