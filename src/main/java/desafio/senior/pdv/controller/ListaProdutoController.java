package desafio.senior.pdv.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Controller;

import desafio.senior.pdv.javafx.TableViewBuilder;
import desafio.senior.pdv.model.Produto;
import desafio.senior.pdv.repository.ProdutoRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Controller
public class ListaProdutoController extends FxController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@FXML
	private TableView<Produto> tvProduto;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.criarColunasTabela(resources);
		produtoRepository.findAll(JpaSort.by("codigo")).forEach(this::adicionarProdutoNaTabela);
	}
	
	private void criarColunasTabela(ResourceBundle resources) {
		BiConsumer<ActionEvent, Produto> acaoBotaoEditar = (evento, item) -> scenes
			.alterarTela(CadastroProdutoController.class,
				controller -> controller.atualizarDadosProduto(item));
		
		// @formatter:off
		new TableViewBuilder<>(tvProduto, resources)
			.coluna("codigo")
				.comPropriedadeIgualNome()
				.comLargura(20.0)
				.finalizar()
			.coluna("descricao")
				.comPropriedadeIgualNome()
				.comLargura(40.0)
				.finalizar()
			.coluna("valor")
				.comPropriedadeIgualNome()
				.comLargura(25.0)
				.finalizar()
			.coluna("opcoes")
				.comLargura(15.0)
				.comBotaoAcao("editar", acaoBotaoEditar)
				.finalizar()
			.finalizar();
		// @formatter:on
	}
	
	private void adicionarProdutoNaTabela(Produto produto) {
		tvProduto.getItems().add(produto);
	}
	
	public void redirecionarAdicionar() {
		scenes.alterarTela(CadastroProdutoController.class);
	}
	
}
