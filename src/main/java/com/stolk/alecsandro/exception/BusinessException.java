package com.stolk.alecsandro.exception;

import javax.ejb.ApplicationException;
import java.util.ArrayList;
import java.util.List;

//@ApplicationException(rollback = true)
public class BusinessException extends Exception {

    private List<String> mensagens = new ArrayList<>();

    BusinessException() {
        super();
    }

    public BusinessException(String mensagem) {
        super(mensagem);
        mensagens.add(mensagem);
    }

    public List<String> getMensagens() {
        return mensagens;
    }

    public void addMensagem(String mensagem) {
        this.mensagens.add(mensagem);
    }
}