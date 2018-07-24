package desafio.senior.pdv.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import desafio.senior.pdv.Constantes;
import desafio.senior.pdv.TesteUnitario;
import desafio.senior.pdv.model.Documento;
import desafio.senior.pdv.model.DocumentoItem;
import desafio.senior.pdv.model.Produto;
import desafio.senior.pdv.repository.DocumentoRepository;
import desafio.senior.pdv.repository.ProdutoRepository;

public class VendaServiceTest extends TesteUnitario {
	
	@Mock
	private DocumentoService documentoService;
	@Mock
	private ProdutoRepository produtoRepository;
	@Mock
	private DocumentoRepository documentoRepository;
	
	private VendaService vendaService = new VendaService(); // Testar construtor vazio.
	
	@Before
	public void init() {
		when(documentoService.getRepository()).thenReturn(documentoRepository);
		this.vendaService = new VendaService(documentoService, produtoRepository);
	}
	
	private Documento novaVenda() {
		Documento documento = new Documento();
		documento.setId(1L);
		documento.setNumero(432);
		documento.setItens(new HashSet<DocumentoItem>());
		return documento;
	}
	
	@Test
	public void deveCriarVendaComSucesso() throws Exception {
		Documento documento = new Documento();
		Produto novoProduto = new Produto(2L);
		
		when(produtoRepository.findById(novoProduto.getId())).thenReturn(Optional.of(novoProduto));
		vendaService.criarVenda(documento, novoProduto);
		
		assertFalse(documento.isConfirmado());
		verify(documentoService).salvar(documento);
	}
	
	@Test
	public void deveAtualizarDadosDosProdutoAoInserirProdutoNaVenda() throws Exception {
		Documento documento = novaVenda();
		Produto novoProduto = new Produto(2L);
		
		when(produtoRepository.findById(novoProduto.getId())).thenReturn(Optional.of(novoProduto));
		vendaService.gravarProdutoNaVenda(documento, novoProduto);
		
		boolean contemProduto = documento.getItens().stream().anyMatch(item -> item.getProduto().equals(novoProduto));
		assertTrue(contemProduto);
		verify(produtoRepository).findById(novoProduto.getId());
	}
	
	@Test
	public void deveGerarErroAoAdicionarUmProdutoJaAdicionadoNaVenda() throws Exception {
		Documento documento = novaVenda();
		Produto novoProduto = new Produto(2L);
		
		when(produtoRepository.findById(novoProduto.getId())).thenReturn(Optional.of(novoProduto));
		vendaService.gravarProdutoNaVenda(documento, novoProduto);
		
		try {
			vendaService.gravarProdutoNaVenda(documento, novoProduto);
			fail();
		} catch (SeniorValidacaoException e) {
			assertEquals(Constantes.ERRO_VENDA_JA_CONTEM_PRODUTO, e.getMessage());
		}
	}
	
	@Test
	public void deveRemoverProdutoDaVeda() throws Exception {
		Documento documento = novaVenda();
		Produto novoProduto = new Produto(2L);
		when(produtoRepository.findById(novoProduto.getId())).thenReturn(Optional.of(novoProduto));
		vendaService.gravarProdutoNaVenda(documento, novoProduto);
		
		Produto outroProduto = new Produto(3L);
		when(produtoRepository.findById(outroProduto.getId())).thenReturn(Optional.of(outroProduto));
		vendaService.gravarProdutoNaVenda(documento, outroProduto);
		
		assertTrue(documento.getItens().stream().anyMatch(item -> item.getProduto().equals(outroProduto)));
		
		vendaService.removerProdutoDaVenda(documento, outroProduto);
		
		assertFalse(documento.getItens().stream().anyMatch(item -> item.getProduto().equals(outroProduto)));
	}
	
	@Test
	public void deveFinalizarVendaComSucesso() throws Exception {
		Documento documento = novaVenda();
		
		assertFalse(documento.isConfirmado());
		vendaService.finalizarVenda(documento);
		
		assertTrue(documento.isConfirmado());
		verify(documentoService).salvar(documento);
	}
	
	@Test
	public void naoDeveFinalizarVendaNaoCriada() throws Exception {
		Documento documento = new Documento();
		documento.setId(null);
		documento.setNumero(null);
		
		try {
			vendaService.finalizarVenda(documento);
			fail();
		} catch (SeniorValidacaoException e) {
			assertEquals(Constantes.ERRO_FINALIZAR_VENDA_NAO_CRIADA, e.getMessage());
		}
	}
}
