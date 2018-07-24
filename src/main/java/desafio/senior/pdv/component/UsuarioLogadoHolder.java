package desafio.senior.pdv.component;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import desafio.senior.pdv.model.Usuario;

public interface UsuarioLogadoHolder {
	
	public static Usuario get() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			return ( Usuario ) auth.getPrincipal();
		}
		return null;
	}
	
}
