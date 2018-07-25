package desafio.senior.pdv.model.dto;

import java.util.function.BinaryOperator;

import desafio.senior.pdv.model.DocumentoItem;

public class ProdutoVendidoDTO implements Comparable<ProdutoVendidoDTO> {
	
	public static final BinaryOperator<ProdutoVendidoDTO> REDUCE_FUNCTION = (acumulador, atual) -> {
		acumulador.valor += atual.valor;
		acumulador.quantidade += atual.quantidade;
		return acumulador;
	};
	
	private Long id;
	private String codigo;
	private String descricao;
	private Integer quantidade;
	private Double valor;
	
	public ProdutoVendidoDTO() {}
	
	public ProdutoVendidoDTO(DocumentoItem item) {
		this.id = item.getProduto().getId();
		this.codigo = item.getProduto().getCodigo();
		this.descricao = item.getProduto().getDescricao();
		this.quantidade = 1;
		this.valor = item.getValorUnitario();
	}
	
	public Long getId() {
		return id;
	}
	
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
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	@Override
	public int compareTo(ProdutoVendidoDTO o) {
		return this.codigo.compareToIgnoreCase(o.codigo);
	}
	
}
