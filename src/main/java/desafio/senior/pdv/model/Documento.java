package desafio.senior.pdv.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.annotations.BatchSize;
import org.springframework.util.CollectionUtils;

import desafio.senior.pdv.utils.NumeroUtils;

@Entity
@Table(name = "documento", uniqueConstraints = @UniqueConstraint(columnNames = "numero"))
@BatchSize(size = 100)
public class Documento implements Entidade {
	
	private static final long serialVersionUID = -3248381675500788766L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "serial", insertable = false, updatable = false)
	private Integer numero;
	
	@NotNull
	@Column(name = "valor_total", nullable = false)
	private Double valorTotal = NumberUtils.DOUBLE_ZERO;
	
	@NotNull
	@Column(nullable = false)
	private boolean confirmado = false;
	
	@OneToMany(fetch = FetchType.LAZY,
		orphanRemoval = true,
		mappedBy = "documento",
		cascade = CascadeType.ALL)
	private Set<DocumentoItem> itens;
	
	public Documento() {} // NOSONAR
	
	public Documento(Long id) { // NOSONAR
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
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public Double getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public boolean isConfirmado() {
		return confirmado;
	}
	
	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}
	
	public Set<DocumentoItem> getItens() {
		return itens;
	}
	
	public void setItens(Set<DocumentoItem> itens) {
		this.itens = itens;
	}
	
	public boolean isPossuiItens() {
		return !CollectionUtils.isEmpty(this.itens);
	}
	
	public void addProduto(Produto produto) {
		if (this.itens == null) {
			this.itens = new HashSet<>();
		}
		
		DocumentoItem item = new DocumentoItem(this, produto);
		this.itens.add(item);
		this.valorTotal = NumeroUtils.arredondar(this.valorTotal + item.getValorUnitario());
	}
	
	public void removerProduto(Produto produto) {
		if (this.itens != null) {
			this.itens.removeIf(item -> item.getProduto().equals(produto));
		}
		this.valorTotal = NumeroUtils.arredondar(this.valorTotal - produto.getValor());
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
