package desafio.senior.pdv.model.dto;

import java.util.function.BinaryOperator;

import desafio.senior.pdv.model.Produto;

public class ProdutoVendidoDTO implements Comparable<ProdutoVendidoDTO> {
	
	public static BinaryOperator<ProdutoVendidoDTO> REDUCE_FUNCTION = (acumulador, atual) -> {
		acumulador.valor += atual.valor;
		acumulador.quantidade += atual.quantidade;
		return acumulador;
	};
	
	private Long id;
	private String descricao;
	private Integer quantidade;
	private Double valor;
	
	public ProdutoVendidoDTO() {}
	
	public ProdutoVendidoDTO(Produto produto) {
		this.id = produto.getId();
		this.descricao = produto.getDescricao();
		this.quantidade = 1;
		this.valor = produto.getValor();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
		return this.descricao.compareToIgnoreCase(o.descricao);
	}
	
}
