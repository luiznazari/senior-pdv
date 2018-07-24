package desafio.senior.pdv.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import desafio.senior.pdv.model.Documento;

public interface DocumentoRepository extends SeniorRepository<Documento> {
	
	@Query("select distinct doc from Documento doc"
		+ " left join fetch doc.itens itens"
		+ " left join fetch itens.produto produto")
	List<Documento> findAllCompletos(Sort sort);
	
	@Query("select doc.numero from Documento doc where doc.id = ?1")
	Integer findNumeroById(Long id);
	
}
