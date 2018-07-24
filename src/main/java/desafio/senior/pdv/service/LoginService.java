package desafio.senior.pdv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import desafio.senior.pdv.model.Usuario;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class LoginService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/**
	 * Autentica o usuário e adiciona no contexto de segurança.
	 * 
	 * @param login
	 * @param senha
	 * @throws AuthenticationException
	 *             - caso o usuário não exista ou a senha esteja incorrata.
	 */
	public Usuario autenticar(String login, String senha) {
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(login, senha);
		Authentication auth = authenticationManager.authenticate(authReq);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		return ( Usuario ) auth.getPrincipal();
	}
	
	public static void logout() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(null);
	}
	
}
