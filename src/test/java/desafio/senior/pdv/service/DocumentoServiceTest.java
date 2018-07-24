package desafio.senior.pdv.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import desafio.senior.pdv.TesteIntegracao;
import desafio.senior.pdv.model.Documento;
import desafio.senior.pdv.model.Produto;
import desafio.senior.pdv.repository.DocumentoItemRepository;
import desafio.senior.pdv.repository.ProdutoRepository;
import desafio.senior.pdv.utils.EntidadeUtils;

public class DocumentoServiceTest extends TesteIntegracao {
	
	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private DocumentoItemRepository documentoItemRepository;
	
	@Test(expected = SeniorValidacaoException.class)
	public void naoDeveSalvarDocumentoSemItens() throws Exception {
		Documento documento = novoDocumento();
		assertNull(documento.getNumero());
		documentoService.salvar(documento);
		
		assertNotNull("O campo número deve ser sequencial e auto-increment!", documento.getNumero());
	}
	
	@Test
	public void deveSalvarDocumentoComUmItem() throws Exception {
		Documento documento = novoDocumento();
		documento.addProduto(novoProdutoSalvo());
		
		Documento documentoSalvo = documentoService.salvar(documento);
		assertTrue(EntidadeUtils.isNotNull(documentoSalvo));
	}
	
	@Test
	public void deveAlterarDocumentoSalvandoMaisItens() throws Exception {
		assertEquals(0, documentoService.getRepository().count());
		
		Documento documento = novoDocumento();
		documento.addProduto(novoProdutoSalvo());
		documentoService.salvar(documento);
		
		assertEquals(1, documentoService.getRepository().count());
		assertEquals(1, documentoItemRepository.count());
		
		documento.addProduto(novoProdutoSalvo());
		documento.addProduto(novoProdutoSalvo());
		documentoService.salvar(documento);
		
		assertEquals(1, documentoService.getRepository().count());
		assertEquals(3, documentoItemRepository.count());
	}
	
	@Test
	public void deveExcluirDocumentoEItens() throws Exception {
		Documento documento = novoDocumento();
		documento.addProduto(novoProdutoSalvo());
		documento.addProduto(novoProdutoSalvo());
		documentoService.salvar(documento);
		
		assertEquals(1, documentoService.getRepository().count());
		assertEquals(2, documentoItemRepository.count());
		
		documentoService.excluir(documento);
		assertEquals(0, documentoService.getRepository().count());
		assertEquals(0, documentoItemRepository.count());
	}
	
	private Documento novoDocumento() {
		Documento documento = new Documento();
		documento.setConfirmado(false);
		return documento;
	}
	
	private Produto novoProdutoSalvo() {
		Produto produto = new Produto();
		produto.setCodigo(RandomStringUtils.random(20));
		produto.setDescricao(RandomStringUtils.random(40));
		produto.setValor(new Random().nextDouble() * 99);
		return produtoRepository.save(produto);
	}
	
}
