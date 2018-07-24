package desafio.senior.pdv.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "documento_item",
	uniqueConstraints = @UniqueConstraint(columnNames = { "id_documento", "id_produto" }))
@BatchSize(size = 100)
public class DocumentoItem implements Entidade {
	
	private static final long serialVersionUID = -5271074347245787944L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_documento",
		nullable = false,
		foreignKey = @ForeignKey(name = "fk_documeto_item_documento"))
	private Documento documento;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_produto",
		nullable = false,
		foreignKey = @ForeignKey(name = "fk_documeto_item_produto"))
	private Produto produto;
	
	@NotNull
	@Column(name = "valor_unitario", nullable = false)
	private Double valorUnitario = NumberUtils.DOUBLE_ZERO;
	
	public DocumentoItem() {} // NOSONAR
	
	public DocumentoItem(Long id) { // NOSONAR
		this.id = id;
	}
	
	public DocumentoItem(Documento documento, Produto produto) {
		this.produto = produto;
		this.documento = documento;
		this.valorUnitario = produto.getValor();
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public Documento getDocumento() {
		return documento;
	}
	
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Double getValorUnitario() {
		return valorUnitario;
	}
	
	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DocumentoItem)) {
			return false;
		}
		DocumentoItem documentoItem = ( DocumentoItem ) obj;
		return new EqualsBuilder().append(this.documento, documentoItem.documento)
			.append(this.produto, documentoItem.produto).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.documento).append(this.produto).toHashCode();
	}
	
}
