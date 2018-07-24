package desafio.senior.pdv.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "produto", uniqueConstraints = @UniqueConstraint(columnNames = "codigo"))
@BatchSize(size = 100)
public class Produto implements Entidade {
	
	private static final long serialVersionUID = -6259533651299775219L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "{erro.codigo.vazio}")
	@Length(min = 1, max = 20, message = "{erro.codigo.invalido}")
	@Column(length = 20, nullable = false)
	private String codigo;
	
	@NotEmpty(message = "{erro.descricao.vazio}")
	@Length(min = 1, max = 256, message = "{erro.descricao.invalido}")
	@Column(length = 256, nullable = false)
	private String descricao;
	
	@NotNull(message = "{erro.valor.vazio}")
	@Column(nullable = false)
	private Double valor = NumberUtils.DOUBLE_ZERO;
	
	public Produto() {} // NOSONAR
	
	public Produto(Long id) { // NOSONAR
		this.id = id;
	}
	
	public Produto(String codigo, String descricao, Double valor) {
		this.valor = valor;
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
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
