package desafio.senior.pdv.repository;

import desafio.senior.pdv.model.Usuario;

public interface UsuarioRepository extends SeniorRepository<Usuario> {
	
	Usuario findByLogin(String login);
	
}
