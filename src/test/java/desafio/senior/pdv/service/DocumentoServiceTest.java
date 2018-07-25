package desafio.senior.pdv.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import desafio.senior.pdv.TesteIntegracao;
import desafio.senior.pdv.model.Documento;
import desafio.senior.pdv.model.DocumentoItem;
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
	
	@Test
	public void deveSalvarDocumentoGerandoNumeroSequencial() throws Exception {
		Documento documento = novoDocumento();
		assertNull(documento.getNumero());
		documentoService.salvar(documento);
		
		Integer numeroSerial = documentoService.getRepository().findNumeroById(documento.getId());
		assertNotNull("O campo número deve ser sequencial e auto-increment!", numeroSerial);
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
	
	@Test
	public void deveTrazerTodosOsDocumentosComProdutos() throws Exception {
		this.criarDocumentosComProdutos();
		
		List<Documento> documentosConfirmados = documentoService.buscarDocumentosConfirmados();
		assertEquals(2, documentosConfirmados.size());
		
		Documento documento1 = documentosConfirmados.stream().findFirst().get();
		DocumentoItem documentoItem = documento1.getItens().stream().findFirst().get();
		assertNotNull(documentoItem.getId());
		assertNotNull(documentoItem.getProduto().getId());
	}
	
	private void criarDocumentosComProdutos() {
		Documento documento1 = novoDocumento();
		documento1.setConfirmado(true);
		documento1.addProduto(novoProdutoSalvo());
		documento1.addProduto(novoProdutoSalvo());
		documentoService.salvar(documento1);
		
		Documento documento2 = novoDocumento();
		documento2.setConfirmado(false);
		documento2.addProduto(novoProdutoSalvo());
		documentoService.salvar(documento2);
		
		Documento documento3 = novoDocumento();
		documento3.setConfirmado(true);
		documento3.addProduto(novoProdutoSalvo());
		documentoService.salvar(documento3);
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
		produto.setValor(42.21);
		return produtoRepository.save(produto);
	}
	
}
