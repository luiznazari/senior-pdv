package desafio.senior.pdv.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import desafio.senior.pdv.Constantes;
import desafio.senior.pdv.model.Usuario;
import desafio.senior.pdv.repository.UsuarioRepository;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class LoginSenhaAuthenticationManager implements AuthenticationManager {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) {
		Usuario usuario = usuarioRepository.findByLogin(authentication.getName());
		if (usuario == null) {
			throw new UsernameNotFoundException(Constantes.ERRO_USUARIO_NAO_ENCONTRADO);
		}
		
		if (!isSenhaCorreta(usuario, authentication)) {
			throw new BadCredentialsException(Constantes.ERRO_SENHA_INCORRETA);
		}
		
		return usuarioAutenticado(usuario);
	}
	
	private Authentication usuarioAutenticado(Usuario usuario) {
		return new UsernamePasswordAuthenticationToken(usuario, usuario.getSenha(),
			usuario.getPerfis());
	}
	
	private boolean isSenhaCorreta(Usuario usuario, Authentication authentication) {
		String senha = authentication.getCredentials().toString();
		return usuario.getSenha().equals(senha);
	}
	
}
