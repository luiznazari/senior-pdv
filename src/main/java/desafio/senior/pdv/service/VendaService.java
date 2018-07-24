package desafio.senior.pdv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import desafio.senior.pdv.Constantes;
import desafio.senior.pdv.model.Documento;
import desafio.senior.pdv.model.Produto;
import desafio.senior.pdv.repository.ProdutoRepository;
import desafio.senior.pdv.utils.EntidadeUtils;

@Service
@Transactional(propagation = Propagation.REQUIRED,
	readOnly = false,
	rollbackFor = SeniorValidacaoException.class)
public class VendaService {
	
	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public VendaService() {
		// Construtor vazio.
	}
	
	VendaService(DocumentoService documentoService, ProdutoRepository produtoRepository) {
		this.documentoService = documentoService;
		this.produtoRepository = produtoRepository;
	}
	
	public Documento criarVenda(Documento documento, Produto produto) {
		documento.setConfirmado(false);
		return this.gravarProdutoNaVenda(documento, produto);
	}
	
	public Documento gravarProdutoNaVenda(Documento documento, Produto produto) {
		this.atualizarDadosEAdicionarProduto(documento, produto);
		return documentoService.salvar(documento);
	}
	
	private void atualizarDadosEAdicionarProduto(Documento documento, Produto produto) {
		if (documento.isPossuiItens() && documento.getItens().stream().anyMatch(item -> item.getProduto().equals(produto))) {
			throw new SeniorValidacaoException(Constantes.ERRO_VENDA_JA_CONTEM_PRODUTO);
		}
		produtoRepository.findById(produto.getId()).ifPresent(documento::addProduto);
	}
	
	public Documento removerProdutoDaVenda(Documento documento, Produto produto) {
		documento.removerProduto(produto);
		return documentoService.salvar(documento);
	}
	
	public void finalizarVenda(Documento documento) {
		if (EntidadeUtils.isNull(documento)) {
			throw new SeniorValidacaoException(Constantes.ERRO_FINALIZAR_VENDA_NAO_CRIADA);
		}
		
		documento.setConfirmado(true);
		documentoService.salvar(documento);
	}
	
	public void cancelarVenda(Documento documento) {
		if (EntidadeUtils.isNotNull(documento)) {
			documentoService.excluir(documento);
		}
	}
	
}
