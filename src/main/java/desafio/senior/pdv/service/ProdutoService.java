package desafio.senior.pdv.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import desafio.senior.pdv.Constantes;
import desafio.senior.pdv.model.DocumentoItem;
import desafio.senior.pdv.model.Produto;
import desafio.senior.pdv.repository.DocumentoItemRepository;
import desafio.senior.pdv.repository.ProdutoRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED,
	readOnly = false,
	rollbackFor = SeniorValidacaoException.class)
public class ProdutoService extends AbstractCrudService<Produto, ProdutoRepository> {
	
	@Autowired
	private DocumentoItemRepository documentoItemRepository;
	
	@Override
	protected void validacoesExcluir(Produto produto) {
		DocumentoItem item = new DocumentoItem();
		item.setValorUnitario(null);
		item.setProduto(produto);
		if (documentoItemRepository.exists(Example.of(item))) {
			throw new SeniorValidacaoException(Constantes.ERRO_EXISTE_PRODUTO_DOCUMENTO_ITEM);
		}
	}
	
	@Transactional(readOnly = true)
	public Optional<Produto> buscarPorCodigo(String codigoProduto) {
		if (StringUtils.isBlank(codigoProduto)) {
			return Optional.empty();
		}
		
		Produto produto = new Produto();
		produto.setCodigo(codigoProduto.trim());
		produto.setValor(null);
		return getRepository().findOne(Example.of(produto));
	}
	
}
