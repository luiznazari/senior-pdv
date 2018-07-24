package desafio.senior.pdv.utils;

import org.hibernate.Hibernate;

import desafio.senior.pdv.model.Entidade;

public interface EntidadeUtils {
	
	static boolean isNull(Entidade entidade) {
		return entidade == null || entidade.getId() == null;
	}
	
	static boolean isNotNull(Entidade entidade) {
		return entidade != null && entidade.getId() != null;
	}
	
	static void lazyInitialize(Object... entidades) {
		for (Object entidade : entidades) {
			if (entidade != null && Hibernate.isInitialized(entidade)) {
				Hibernate.initialize(entidade);
			}
		}
	}
	
}
