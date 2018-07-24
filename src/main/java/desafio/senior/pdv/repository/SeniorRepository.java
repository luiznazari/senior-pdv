package desafio.senior.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import desafio.senior.pdv.model.Entidade;

@NoRepositoryBean
public interface SeniorRepository<E extends Entidade> extends JpaRepository<E, Long> {
	
}
