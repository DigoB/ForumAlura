package br.com.alura.forum.controllers.Dto;

import java.time.LocalDateTime;

import br.com.alura.forum.modelo.Resposta;

//Dados que serão mostrados no momento de responder uma duvida do Forum
public class RespostaDto {
    
    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;


    public RespostaDto(Resposta resposta) {
        this.id = resposta.getId();
        this.mensagem = resposta.getMensagem();
        this.dataCriacao = resposta.getDataCriacao();
        this.nomeAutor = resposta.getAutor().getNome();
    }


    public Long getId() {
        return this.id;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    public String getNomeAutor() {
        return this.nomeAutor;
    }



}
