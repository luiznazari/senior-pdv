package desafio.senior.pdv.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import desafio.senior.pdv.Constantes;
import desafio.senior.pdv.javafx.TableViewBuilder;
import desafio.senior.pdv.model.Documento;
import desafio.senior.pdv.model.DocumentoItem;
import desafio.senior.pdv.model.Produto;
import desafio.senior.pdv.repository.DocumentoRepository;
import desafio.senior.pdv.service.ProdutoService;
import desafio.senior.pdv.service.SeniorValidacaoException;
import desafio.senior.pdv.service.VendaService;
import desafio.senior.pdv.utils.Alertas;
import desafio.senior.pdv.utils.NumeroUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

@Controller
public class VendaController extends FxController {
	
	@Autowired
	private VendaService vendaService;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private DocumentoRepository documentoRepository;
	
	@FXML
	private Text txtNumero;
	@FXML
	private Label lblQtdProdutos;
	@FXML
	private TextField tfCodigoProduto;
	@FXML
	private Label lblValorTotalProdutos;
	@FXML
	private TableView<Produto> tvProdutosVenda;
	@FXML
	private Button btnCancelarVenda;
	@FXML
	private Button btnFinalizarVenda;
	@FXML
	private Button btnAdicionarProduto;
	
	private Documento documento;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.criarTabelaProdutosVenda(resources);
		this.criarEventoHabilitarBtnAdicionarProduto();
		this.documento = new Documento();
	}
	
	private void criarTabelaProdutosVenda(ResourceBundle resourceBundle) {
		BiConsumer<ActionEvent, Produto> acaoRemoverProduto = (evento, produto) -> {
			removerProdutoVenda(produto);
			tvProdutosVenda.getItems().remove(produto);
		};
		
		// @formatter:off
		new TableViewBuilder<>(tvProdutosVenda, resourceBundle)
			.coluna("codigo")
				.comLargura(20.0)
				.comPropriedadeIgualNome()
				.finalizar()
			.coluna("descricao")
				.comLargura(40.0)
				.comPropriedadeIgualNome()
				.finalizar()
			.coluna("descricao")
				.comLargura(25.0)
				.comPropriedadeIgualNome()
				.finalizar()
			.coluna("opcoes")
				.comLargura(15.0)
				.comBotaoAcao("x", acaoRemoverProduto)
				.finalizar()
			.finalizar();
		// @formatter:on
	}
	
	private void criarEventoHabilitarBtnAdicionarProduto() {
		tfCodigoProduto.addEventFilter(KeyEvent.KEY_RELEASED, evento -> {
			boolean possuiCodigoProduto = StringUtils.isNotBlank(tfCodigoProduto.getText());
			btnAdicionarProduto.setDisable(!possuiCodigoProduto);
		});
	}
	
	private void removerProdutoVenda(Produto produto) {
		this.documento = vendaService.removerProdutoDaVenda(documento, produto);
		this.atualizarDadosVenda();
	}
	
	public void adicionarProduto() {
		String codigoProduto = tfCodigoProduto.getText();
		
		Optional<Produto> optProduto = produtoService.buscarPorCodigo(codigoProduto);
		if (!optProduto.isPresent()) {
			Alertas.alerta(Constantes.ERRO_NENHUM_PRODUTO_ENCONTRADO_CODIGO);
			return;
		}
		
		Produto produto = optProduto.get();
		
		try {
			this.salvarProdutoNaVenda(produto);
			this.adicionarProdutoNaTela(produto);
		} catch (SeniorValidacaoException e) {
			Alertas.alerta(e.getMessage());
		}
	}
	
	private void salvarProdutoNaVenda(Produto produto) {
		if (this.documento.getId() == null) {
			this.documento = vendaService.criarVenda(documento, produto);
			this.documento.setNumero(documentoRepository.findNumeroById(this.documento.getId()));
		} else {
			this.documento = vendaService.gravarProdutoNaVenda(documento, produto);
		}
	}
	
	private void adicionarProdutoNaTela(Produto produto) {
		tvProdutosVenda.getItems().add(produto);
		this.atualizarDadosVenda();
	}
	
	private void atualizarDadosVenda() {
		Double valorTotalProdutos = documento.getItens().stream().map(DocumentoItem::getValorUnitario)
			.reduce((total, valorUnitarioItem) -> total + valorUnitarioItem).orElse(NumberUtils.DOUBLE_ZERO);
		valorTotalProdutos = NumeroUtils.arredondar(valorTotalProdutos);
		
		lblQtdProdutos.setText(String.valueOf(documento.getItens().size()));
		lblValorTotalProdutos.setText(NumeroUtils.formatarBr(valorTotalProdutos));
		if (documento.getNumero() != null) {
			txtNumero.setText(documento.getNumero().toString());
		}
		
		tfCodigoProduto.setText(StringUtils.EMPTY);
		btnCancelarVenda.setDisable(documento.getNumero() == null);
		btnFinalizarVenda.setDisable(!documento.isPossuiItens());
	}
	
	public void finalizarVenda() {
		vendaService.finalizarVenda(documento);
		scenes.alterarTela(VendaController.class);
	}
	
	public void cancelarVenda() {
		Alertas.confirmarAcao(Constantes.CANCELAR_VENDA, () -> {
			vendaService.cancelarVenda(documento);
			this.onCancelar();
		});
	}
	
}
