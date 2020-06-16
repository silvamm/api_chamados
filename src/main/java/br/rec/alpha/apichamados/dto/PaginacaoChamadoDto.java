package br.rec.alpha.apichamados.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.rec.alpha.apichamados.model.Chamado;
import lombok.Data;

@Data
public class PaginacaoChamadoDto {
	
	private List<ChamadoDto> chamados;
	private int paginas;
	private int atual;
	
	
	public PaginacaoChamadoDto(Page<Chamado> chamado) {
		chamados = chamado.getContent().stream().map(ChamadoDto::new).collect(Collectors.toList());
		paginas = chamado.getTotalPages();
		atual = chamado.getPageable().getPageNumber();
	}

}
