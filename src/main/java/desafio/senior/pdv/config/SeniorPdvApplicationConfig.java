/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desafio.senior.pdv.config;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;

import desafio.senior.pdv.component.LoginSenhaAuthenticationManager;
import desafio.senior.pdv.component.SceneLoader;
import desafio.senior.pdv.component.Scenes;
import javafx.stage.Stage;

@Configuration
public class SeniorPdvApplicationConfig {
	
	@Autowired
	private SceneLoader sceneLoader;
	
	/*
	 * Caso não seja lazy, o Spring irá tentar instanciar na inicialização da aplicação,
	 * quando o Stage ainda não estará criado, resultando em erros de injeção de dependência.
	 */
	@Bean
	@Lazy(value = true)
	public Scenes scenes(Stage stage) {
		return new Scenes(sceneLoader, stage);
	}
	
	@Bean
	public ResourceBundle resourceBundle() {
		return NullSafeResourceBundle.getInstance();
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new LoginSenhaAuthenticationManager();
	}
	
}
