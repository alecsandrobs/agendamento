package com.stolk.alecsandro.business;

import com.stolk.alecsandro.banco.AgendamentoDao;
import com.stolk.alecsandro.exception.BusinessException;
import com.stolk.alecsandro.interceptor.Logger;
import com.stolk.alecsandro.modelo.Agendamento;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Stateless
@Logger
public class AgendamentoBusiness {

    @Inject
    private AgendamentoDao agendamentoDao;

    @Resource(lookup = "java:jboss/mail/AgendamentoMailSession")
    private Session sessaoEmail;

    //    private static String EMAIL_FROM = "mail.address";
//    private static String EMAIL_USER = "mail.smtp.user";
//    private static String EMAIL_PASSWORD = "mail.smtp.pass";
    private static String EMAIL_FROM = "mail.smtp.host";

    public List<Agendamento> listar() {
        return agendamentoDao.listar();
    }

    public void salvar(@Valid Agendamento agendamentoEmail) throws BusinessException {
        if (!agendamentoDao.listar(agendamentoEmail.getEmail()).isEmpty()) {
            throw new BusinessException("Email já agendado.");
        }
        agendamentoEmail.setEnviado(false);
        agendamentoDao.salvar(agendamentoEmail);
    }

    public List<Agendamento> listarNaoEnviados() {
        return agendamentoDao.listarNaoEnviados();
    }

    public void marcarEnviado(Agendamento agendamento) {
        agendamento.setEnviado(true);
        agendamentoDao.atualizar(agendamento);
    }

    public void enviar(Agendamento agendamento) {
        try {
            MimeMessage mensagem = new MimeMessage(sessaoEmail);
            mensagem.setFrom(sessaoEmail.getProperty(EMAIL_FROM));
            mensagem.setRecipients(Message.RecipientType.TO, agendamento.getEmail());
            mensagem.setSubject(agendamento.getAssunto());
            mensagem.setText(Optional.ofNullable(agendamento.getMensagem()).orElse(""));
            Transport.send(mensagem);
//            Transport.send(mensagem, sessaoEmail.getProperty(EMAIL_USER), sessaoEmail.getProperty(EMAIL_PASSWORD));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}