package br.com.alura.forum.controllers.Dto;

import java.time.LocalDateTime;

import br.com.alura.forum.modelo.Topico;
import org.springframework.data.domain.Page;

public class TopicoDto {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }


    public Long getId() {
        return this.id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }


	public static Page<TopicoDto> converter(Page<Topico> topicos) {
        //Método automático do Spring para paginação
		return topicos.map(TopicoDto::new);
	}

}
