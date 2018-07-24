package desafio.senior.pdv.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import desafio.senior.pdv.model.Documento;
import desafio.senior.pdv.repository.DocumentoRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED,
	readOnly = false,
	rollbackFor = SeniorValidacaoException.class)
public class DocumentoService extends AbstractCrudService<Documento, DocumentoRepository> {
	
	@Transactional(readOnly = true)
	public List<Documento> buscarDocumentosConfirmados() {
		Documento documento = new Documento();
		documento.setId(null);
		documento.setItens(null);
		documento.setValorTotal(null);
		documento.setConfirmado(true);
		return getRepository().findAll(Example.of(documento));
	}
	
}
