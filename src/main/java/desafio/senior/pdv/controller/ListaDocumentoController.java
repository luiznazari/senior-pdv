package desafio.senior.pdv.controller;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Controller;

import desafio.senior.pdv.javafx.TableViewBuilder;
import desafio.senior.pdv.model.Documento;
import desafio.senior.pdv.model.DocumentoItem;
import desafio.senior.pdv.repository.DocumentoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Controller
public class ListaDocumentoController extends FxController {
	
	@Autowired
	private DocumentoRepository documentoRepository;
	
	@FXML
	private TableView<Documento> tvDocumento;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.criarColunasTabela(resources);
		documentoRepository.findAllCompletos(JpaSort.by("numero").and(JpaSort.by(Direction.DESC, "confirmado")))
			.forEach(this::adicionarDocumentoNaTabela);
	}
	
	private void criarColunasTabela(ResourceBundle resources) {
		Function<Documento, String> fnProdutosDocumento = documento -> {
			StringBuilder sb = new StringBuilder();
			Iterator<DocumentoItem> iterator = documento.getItens().iterator();
			while (iterator.hasNext()) {
				sb.append(iterator.next().getProduto().getDescricao());
				if (iterator.hasNext()) {
					sb.append(", ");
				}
			}
			return sb.toString();
		};
		
		// @formatter:off
		new TableViewBuilder<>(tvDocumento, resources)
			.coluna("numero")
				.comPropriedadeIgualNome()
				.comLargura(15.0)
				.finalizar()
			.coluna("produtos")
				.comPropriedade(fnProdutosDocumento)
				.comLargura(45.0)
				.finalizar()
			.coluna("valor.total")
				.comPropriedade("valorTotal")
				.comLargura(25.0)
				.finalizar()
			.coluna("confirmado")
				.comPropriedadeI18n()
				.comLargura(15.0)
				.finalizar()
			.finalizar();
		// @formatter:on
	}
	
	private void adicionarDocumentoNaTabela(Documento documento) {
		tvDocumento.getItems().add(documento);
	}
	
}
