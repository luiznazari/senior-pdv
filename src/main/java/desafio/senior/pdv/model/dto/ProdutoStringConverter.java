package desafio.senior.pdv.model.dto;

import org.apache.commons.lang3.StringUtils;

import desafio.senior.pdv.model.Produto;
import javafx.util.StringConverter;

public class ProdutoStringConverter extends StringConverter<Produto> {
	
	@Override
	public String toString(Produto produto) {
		return new StringBuilder().append(produto.getCodigo()).append(" - ")
			.append(produto.getDescricao()).toString();
	}
	
	@Override
	public Produto fromString(String string) {
		Produto produto = new Produto();
		produto.setCodigo(string);
		return produto;
	}
	
	public static String extrairCodigo(String string) {
		int indexSeparador;
		if (string != null && (indexSeparador = string.indexOf('-')) != StringUtils.INDEX_NOT_FOUND) {
			return string.substring(0, indexSeparador).trim();
		}
		return string;
	}
	
}
