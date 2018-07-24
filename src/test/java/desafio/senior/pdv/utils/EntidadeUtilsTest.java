package desafio.senior.pdv.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import desafio.senior.pdv.model.Entidade;

public class EntidadeUtilsTest {
	
	@Test
	public void deveValidarEntidadeOuCodigoNulos() {
		assertTrue(EntidadeUtils.isNull(null));
		assertTrue(EntidadeUtils.isNull(new EntidadeUtilsTestEntidade(null)));
		assertFalse(EntidadeUtils.isNull(new EntidadeUtilsTestEntidade(1L)));
	}
	
	@Test
	public void deveValidarEntidadeOuCodigoNaoNulos() {
		assertFalse(EntidadeUtils.isNotNull(null));
		assertFalse(EntidadeUtils.isNotNull(new EntidadeUtilsTestEntidade(null)));
		assertTrue(EntidadeUtils.isNotNull(new EntidadeUtilsTestEntidade(1L)));
	}
	
	private class EntidadeUtilsTestEntidade implements Entidade {
		
		private static final long serialVersionUID = 1L;
		
		private Long id;
		
		public EntidadeUtilsTestEntidade(Long id) {
			this.id = id;
		}
		
		@Override
		public Long getId() {
			return id;
		}
		
		@Override
		public void setId(Long id) {
			this.id = id;
		}
		
	}
	
}
