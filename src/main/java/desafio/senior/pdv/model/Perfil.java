package desafio.senior.pdv.model;

import org.springframework.security.core.GrantedAuthority;

public enum Perfil implements GrantedAuthority {
	
	ADMINISTRADOR,
	VENDEDOR;
	
	@Override
	public String getAuthority() {
		return this.name();
	}
	
}
