package com.stolk.alecsandro.mdb;

import com.stolk.alecsandro.business.AgendamentoBusiness;
import com.stolk.alecsandro.interceptor.Logger;
import com.stolk.alecsandro.modelo.Agendamento;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageListener;

@Logger
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:/jms/queue/EmailQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue")
})
public class EmailMdb implements MessageListener {

    @Inject
    private AgendamentoBusiness agendamentoBusiness;

    @Override
    public void onMessage(javax.jms.Message message) {
        try {
            Agendamento agendamento = message.getBody(Agendamento.class);
            agendamentoBusiness.enviar(agendamento);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}