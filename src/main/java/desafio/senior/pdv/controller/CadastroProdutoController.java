package desafio.senior.pdv.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import desafio.senior.pdv.Constantes;
import desafio.senior.pdv.component.Validador;
import desafio.senior.pdv.javafx.LimitedTextField;
import desafio.senior.pdv.javafx.NumericTextField;
import desafio.senior.pdv.model.Produto;
import desafio.senior.pdv.service.ProdutoService;
import desafio.senior.pdv.service.SeniorValidacaoException;
import desafio.senior.pdv.utils.Alertas;
import desafio.senior.pdv.utils.EntidadeUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

@Controller
public class CadastroProdutoController extends FxController {
	
	@Autowired
	private Validador validador;
	@Autowired
	private ProdutoService produtoService;
	
	private String textoTituloEditar;
	@FXML
	private Text txtTitulo;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button btnExcluir;
	@FXML
	private LimitedTextField tfCodigo;
	@FXML
	private LimitedTextField tfDescricao;
	@FXML
	private NumericTextField tfValor;
	
	private Produto produto = new Produto();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textoTituloEditar = resources.getString(Constantes.EDITAR_PRODUTO);
		this.eventosHabilitarBotaoSalvar();
	}
	
	public void atualizarDadosProduto(Produto produto) {
		this.produto = produto;
		if (EntidadeUtils.isNotNull(this.produto)) {
			produtoService.buscar(this.produto.getId())
				.ifPresent(produtoBanco -> {
					tfCodigo.setText(produtoBanco.getCodigo());
					tfDescricao.setText(produtoBanco.getDescricao());
					tfValor.setText(produtoBanco.getValor().toString());
					txtTitulo.setText(textoTituloEditar);
					this.produto = produtoBanco;
				});
		}
		
		this.habilitarBotaoExcluir();
	}
	
	private void habilitarBotaoExcluir() {
		if (produto.getCodigo() != null) {
			btnExcluir.setDisable(false);
		}
	}
	
	private void eventosHabilitarBotaoSalvar() {
		TextField[] campos = new TextField[] { tfCodigo, tfDescricao, tfValor };
		EventHandler<KeyEvent> eventFilterHabilitarSalvar = evento -> {
			boolean camposPreenchidos = Arrays.stream(campos)
				.allMatch(tf -> StringUtils.isNotBlank(tf.getText()));
			btnSalvar.setDisable(!camposPreenchidos);
		};
		
		Arrays.stream(campos)
			.forEach(tf -> tf.addEventFilter(KeyEvent.KEY_RELEASED, eventFilterHabilitarSalvar));
		eventFilterHabilitarSalvar.handle(null);
	}
	
	@Override
	public void onCancelar() {
		scenes.alterarTela(ListaProdutoController.class);
	}
	
	public void salvar() {
		Produto produtoAlterado = this.construirProduto();
		validador.validate(produtoAlterado).quandoErroMostraAlerta();
		
		produtoService.salvar(produtoAlterado);
		scenes.alterarTela(ListaProdutoController.class);
	}
	
	private Produto construirProduto() {
		Produto produtoTela = new Produto();
		produtoTela.setId(this.produto.getId());
		produtoTela.setCodigo(tfCodigo.getText());
		produtoTela.setDescricao(tfDescricao.getText());
		produtoTela.setValor(NumberUtils.toDouble(tfValor.getText()));
		return produtoTela;
	}
	
	public void excluir() {
		try {
			produtoService.excluir(this.produto);
			scenes.alterarTela(ListaProdutoController.class);
		} catch (SeniorValidacaoException e) {
			Alertas.alerta(e.getMessage());
		}
	}
	
}
