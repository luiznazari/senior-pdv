package desafio.senior.pdv.model.dto;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import desafio.senior.pdv.model.DocumentoItem;
import desafio.senior.pdv.utils.NumeroUtils;

public class ProdutoVendidoDTOBuilder {
	
	private final List<DocumentoItem> itensVendidos;
	private List<ProdutoVendidoDTO> produtosVendidos = new ArrayList<>();
	
	public ProdutoVendidoDTOBuilder(List<DocumentoItem> itensVendidos) {
		this.itensVendidos = itensVendidos;
	}
	
	public List<ProdutoVendidoDTO> build() {
		// Agrupar produtos, somando quantidades e valores.
		Map<Long, Optional<ProdutoVendidoDTO>> mapProdutosPorId = this.itensVendidos.stream()
			.map(ProdutoVendidoDTO::new)
			.collect(groupingBy(ProdutoVendidoDTO::getId, reducing(ProdutoVendidoDTO.REDUCE_FUNCTION)));
		
		// Arredondar valores e adicionar à lista.
		mapProdutosPorId.forEach((id, prod) -> {
			ProdutoVendidoDTO dto = prod.get();
			dto.setValor(NumeroUtils.arredondar(dto.getValor()));
			produtosVendidos.add(dto);
		});
		
		Collections.sort(produtosVendidos);
		return this.produtosVendidos;
	}
	
}
