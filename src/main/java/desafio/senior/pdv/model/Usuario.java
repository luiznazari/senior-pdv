package desafio.senior.pdv.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.CollectionUtils;

@Entity
@Table(name = "usuario")
@BatchSize(size = 100)
public class Usuario implements Entidade {
	
	private static final long serialVersionUID = -2306732093793364480L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Length(min = 1, max = 100)
	@Column(nullable = false)
	private String login;
	
	@NotEmpty
	@Length(min = 1, max = 100)
	@Column(nullable = false)
	private String senha;
	
	@Length(min = 1, max = 100)
	@Column(nullable = false)
	private String nome;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_perfil", joinColumns = @JoinColumn(name = "id_usuario"))
	@Column(name = "perfil", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<Perfil> perfis;
	
	public Usuario() {} // NOSONAR
	
	public Usuario(Long id) {
		this.id = id;
	}
	
	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Set<Perfil> getPerfis() {
		return perfis;
	}
	
	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}
	
	public boolean isVendedor() {
		return this.isPossuiPerfil(Perfil.VENDEDOR);
	}
	
	public boolean isAdministrador() {
		return this.isPossuiPerfil(Perfil.ADMINISTRADOR);
	}
	
	private boolean isPossuiPerfil(Perfil perfil) {
		return !CollectionUtils.isEmpty(this.perfis) && this.perfis.contains(perfil);
	}
	
	@Override
	public boolean equals(Object obj) {
		return toEquals(obj);
	}
	
	@Override
	public int hashCode() {
		return toHashCode();
	}
	
}
