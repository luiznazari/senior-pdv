package desafio.senior.pdv.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Especificação das classes que representam entidades.
 * 
 * @author Luiz Felipe Nazari
 * @author Luiz Felipe Nazari
 * @since 27 de maio de 2018
 */
public interface Entidade extends Serializable {
	
	Long getId();
	
	void setId(Long id);
	
	default boolean toEquals(Object obj) {
		if (!(obj instanceof Entidade)) {
			return false;
		}
		Entidade entidade = ( Entidade ) obj;
		return new EqualsBuilder().append(this.getId(), entidade.getId()).isEquals();
	}
	
	default int toHashCode() {
		return new HashCodeBuilder().append(this.getId()).toHashCode();
	}
	
}
