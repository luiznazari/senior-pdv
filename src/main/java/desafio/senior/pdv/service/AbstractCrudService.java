package desafio.senior.pdv.service;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import desafio.senior.pdv.model.Entidade;
import desafio.senior.pdv.utils.EntidadeUtils;

/**
 * Abstração dos comportamentos de services da aplicação. Centraliza o processo
 * de validação e operações CRUD.
 * 
 * @author Luiz Felipe Nazari
 * @param <E>
 *            - Entidade manipulada
 * @param <R>
 *            - Repositório utilizado para persistência de dados
 */
public abstract class AbstractCrudService<E extends Entidade, R extends JpaRepository<E, Long>> {
	
	@Autowired
	private R repository;
	
	R getRepository() {
		return this.repository;
	}
	
	public void flush() {
		this.getRepository().flush();
	}
	
	public Optional<E> buscar(Long id) {
		return this.getRepository().findById(id);
	}
	
	public final E salvar(E entidade) {
		if (entidade.getId() != null) {
			return this.alterar(entidade);
		}
		
		validacoesSalvar(entidade);
		try {
			return this.getRepository().save(entidade);
		} catch (ConstraintViolationException e) {
			throw new SeniorValidacaoException(e.getMessage(), e);
		}
	}
	
	private final E alterar(E entidade) {
		validacoesAlterar(entidade);
		try {
			return this.getRepository().save(entidade);
		} catch (ConstraintViolationException e) {
			throw new SeniorValidacaoException(e.getMessage(), e);
		}
	}
	
	public final void excluir(E entidade) {
		if (EntidadeUtils.isNotNull(entidade)) {
			validacoesExcluir(entidade);
			this.getRepository().delete(entidade);
		}
	}
	
	protected void validacoesSalvar(E entidade) {
		// Fica a critério das implementações especificar as validações.
	}
	
	protected void validacoesAlterar(E entidade) {
		// Fica a critério das implementações especificar as validações.
	}
	
	protected void validacoesExcluir(E entidade) {
		// Fica a critério das implementações especificar as validações.
	}
	
}
