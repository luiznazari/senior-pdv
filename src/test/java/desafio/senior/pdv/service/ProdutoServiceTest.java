package desafio.senior.pdv.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import desafio.senior.pdv.TesteIntegracao;
import desafio.senior.pdv.model.Produto;

public class ProdutoServiceTest extends TesteIntegracao {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Test
	public void deveSalvarProduto() throws Exception {
		Produto produto = new Produto();
		produto.setCodigo("245938");
		produto.setDescricao("Produto bonito");
		produto.setValor(42.21);
		
		produtoService.salvar(produto);
		
		assertNotNull(produto.getId());
	}
	
	@Test
	public void deveBuscarProdutoPorCodigo() throws Exception {
		this.criarProdutoCodigo123();
		
		Optional<Produto> optProduto = produtoService.buscarPorCodigo("   123 ");
		assertTrue(optProduto.isPresent());
		
		optProduto = produtoService.buscarPorCodigo(" 1 ");
		assertFalse(optProduto.isPresent());
		
		optProduto = produtoService.buscarPorCodigo("");
		assertFalse(optProduto.isPresent());
		
		optProduto = produtoService.buscarPorCodigo(null);
		assertFalse(optProduto.isPresent());
	}
	
	private void criarProdutoCodigo123() {
		Produto produto = new Produto();
		produto.setCodigo("123");
		produto.setDescricao("Produto bonito");
		produto.setValor(42.21);
		produtoService.salvar(produto);
	}
	
}
