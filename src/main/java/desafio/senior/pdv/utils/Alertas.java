package desafio.senior.pdv.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

import org.apache.commons.lang3.ArrayUtils;

import desafio.senior.pdv.Constantes;
import desafio.senior.pdv.config.NullSafeResourceBundle;
import desafio.senior.pdv.model.Perfil;
import desafio.senior.pdv.model.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public abstract class Alertas {
	
	private static final ResourceBundle mensagens = NullSafeResourceBundle.getInstance();
	
	private Alertas() {}
	
	public static void sucesso(String chave) {
		mostrarAlerta(chave, AlertType.INFORMATION);
	}
	
	public static void alerta(String chave) {
		mostrarAlerta(chave, AlertType.WARNING);
	}
	
	public static void erro(String chave) {
		mostrarAlerta(chave, AlertType.ERROR);
	}
	
	private static void mostrarAlerta(String chave, AlertType alertType) {
		String mensagem = getMensagem(chave);
		new Alert(alertType, mensagem, ButtonType.OK).show();
	}
	
	public static void confirmarAcao(String chave, Runnable onConfirmar) {
		ButtonType btnNao = new ButtonType(getMensagem(Constantes.NAO), ButtonBar.ButtonData.NO);
		ButtonType btnSim = new ButtonType(getMensagem(Constantes.SIM), ButtonBar.ButtonData.YES);
		
		Alert alerta = new Alert(AlertType.CONFIRMATION, getMensagem(chave), btnNao, btnSim);
		alerta.showAndWait().ifPresent(btn -> {
			if (btn == btnSim) {
				onConfirmar.run();
			}
		});
	}
	
	public static void sobre() {
		Usuario usuario1 = new Usuario("luiz", "123123");
		usuario1.setPerfis(new HashSet<>(Arrays.asList(Perfil.ADMINISTRADOR, Perfil.VENDEDOR)));
		Usuario usuario2 = new Usuario("admin", "123");
		usuario2.setPerfis(new HashSet<>(Arrays.asList(Perfil.ADMINISTRADOR)));
		Usuario usuario3 = new Usuario("venda", "123");
		usuario3.setPerfis(new HashSet<>(Arrays.asList(Perfil.VENDEDOR)));
		Usuario usuario4 = new Usuario("soadmin", "123");
		usuario4.setPerfis(new HashSet<>(Arrays.asList(Perfil.ADMINISTRADOR)));
		
		StringBuilder sb = new StringBuilder();
		sb.append(getMensagem("senior.pdv.sobre1")).append('\n');
		sb.append(getMensagem("senior.pdv.sobre2")).append("\n\n");
		sb.append(getMensagem("senior.pdv.sobre3")).append('\n');
		
		Arrays.asList(usuario1, usuario2, usuario3, usuario4).forEach(usuario -> {
			sb.append("Login: ").append(usuario.getLogin()).append("\t|\t");
			sb.append("Senha: ").append(usuario.getSenha()).append('\n');
			sb.append("Perfis: ").append(ArrayUtils.toString(usuario.getPerfis()));
			sb.append("\n\n");
		});
		
		new Alert(AlertType.INFORMATION, sb.toString(), ButtonType.OK).show();
	}
	
	private static String getMensagem(String chave) {
		return mensagens.getString(chave);
	}
	
}
