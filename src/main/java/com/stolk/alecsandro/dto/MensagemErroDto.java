package com.stolk.alecsandro.dto;

import java.time.LocalDate;
import java.util.List;

public class MensagemErroDto {

    private List<String> mensagens;

    private LocalDate dataErro;

    public List<String> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<String> mensagens) {
        this.mensagens = mensagens;
    }

    public LocalDate getDataErro() {
        return dataErro;
    }

    public void setDataErro(LocalDate dataErro) {
        this.dataErro = dataErro;
    }

    public static MensagemErroDto build(List<String> mensagem) {
        MensagemErroDto mensagemErroDto = new MensagemErroDto();
        mensagemErroDto.setMensagens(mensagem);
        mensagemErroDto.setDataErro(LocalDate.now());
        return mensagemErroDto;
    }
}
