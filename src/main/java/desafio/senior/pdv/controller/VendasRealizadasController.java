package desafio.senior.pdv.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import desafio.senior.pdv.javafx.TableViewBuilder;
import desafio.senior.pdv.model.Documento;
import desafio.senior.pdv.model.DocumentoItem;
import desafio.senior.pdv.model.dto.ProdutoVendidoDTO;
import desafio.senior.pdv.model.dto.ProdutoVendidoDTOBuilder;
import desafio.senior.pdv.service.DocumentoService;
import desafio.senior.pdv.utils.NumeroUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

@Controller
public class VendasRealizadasController extends FxController {
	
	@Autowired
	private DocumentoService documentoService;
	
	@FXML
	private Label lblQtdVendas;
	@FXML
	private Label lblValorTotalVendido;
	@FXML
	private Label lblQtdProdutosVendidos;
	@FXML
	private TableView<ProdutoVendidoDTO> tvProdutosVendidos;
	
	private List<Documento> documentosConfirmados;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.documentosConfirmados = documentoService.buscarDocumentosConfirmados();
		this.criarTabelaProdutosVendidos(resources);
		this.atualizarDadosVenda();
		this.atualizarDadosProdutosVendidos();
	}
	
	private void criarTabelaProdutosVendidos(ResourceBundle resourcesBundle) {
		// @formatter:off
		new TableViewBuilder<>(tvProdutosVendidos, resourcesBundle)
			.coluna("codigo")
				.comPropriedadeIgualNome()
				.comLargura(15.0)
				.finalizar()
			.coluna("descricao")
				.comPropriedadeIgualNome()
				.comLargura(45.0)
				.finalizar()
			.coluna("quantidade")
				.comPropriedadeIgualNome()
				.comLargura(20.0)
				.finalizar()
			.coluna("valor")
				.comPropriedadeIgualNome()
				.comLargura(20.0)
				.finalizar()
			.finalizar();
		// @formatter:on
	}
	
	private void atualizarDadosVenda() {
		Double valorTotalVendido = documentosConfirmados.stream().map(Documento::getValorTotal)
			.reduce((total, atual) -> total + atual).orElse(NumberUtils.DOUBLE_ZERO);
		
		lblQtdVendas.setText(String.valueOf(documentosConfirmados.size()));
		lblValorTotalVendido.setText(NumeroUtils.formatarBr(valorTotalVendido));
	}
	
	private void atualizarDadosProdutosVendidos() {
		List<DocumentoItem> todosItensVendidos = new ArrayList<>();
		
		documentosConfirmados
			.forEach(doc -> doc.getItens()
				.forEach(todosItensVendidos::add));
		lblQtdProdutosVendidos.setText(String.valueOf(todosItensVendidos.size()));
		
		List<ProdutoVendidoDTO> produtosVendidos = new ProdutoVendidoDTOBuilder(todosItensVendidos).build();
		produtosVendidos.forEach(prod -> tvProdutosVendidos.getItems().add(prod));
	}
	
}
