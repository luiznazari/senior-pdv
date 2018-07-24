package desafio.senior.pdv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import desafio.senior.pdv.component.Scenes;
import desafio.senior.pdv.config.ExceptionHandlerPadrao;
import desafio.senior.pdv.controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
public class SeniorPdvApplication extends Application {
	
	private ConfigurableApplicationContext springContext;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void init() throws Exception {
		this.springContext = SpringApplication.run(SeniorPdvApplication.class);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandlerPadrao());
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Scenes scenes = springContext.getBean(Scenes.class, stage);
		scenes.alterarTela(LoginController.class);
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		this.springContext.close();
	}
	
}
