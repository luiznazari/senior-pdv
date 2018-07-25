package desafio.senior.pdv.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;

import desafio.senior.pdv.Constantes;
import desafio.senior.pdv.model.Usuario;
import desafio.senior.pdv.service.LoginService;
import desafio.senior.pdv.utils.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class LoginController extends FxController {
	
	@Autowired
	private LoginService loginService;
	
	@FXML
	private TextField tfLogin;
	@FXML
	private PasswordField pfSenha;
	
	@FXML
	public void entrar() {
		String login = tfLogin.getText();
		String senha = pfSenha.getText();
		
		if (StringUtils.isEmpty(login) || StringUtils.isEmpty(senha)) {
			Alertas.erro(Constantes.ERRO_LOGIN_OU_SENHA_VAZIO);
			return;
		}
		
		login = login.trim();
		senha = senha.trim();
		
		try {
			Usuario usuario = loginService.autenticar(login, senha);
			this.redirecionarUsuario(usuario);
		} catch (AuthenticationException e) {
			Alertas.alerta(e.getMessage());
		}
	}
	
	private void redirecionarUsuario(Usuario usuario) {
		Class<? extends FxController> controller = VendaController.class;
		if (isApenasAdministrador(usuario)) {
			controller = VendasRealizadasController.class;
		}
		scenes.alterarTela(controller);
	}
	
	private boolean isApenasAdministrador(Usuario usuario) {
		return usuario.isAdministrador() && usuario.getPerfis().size() == 1;
	}
	
}
