package com.stolk.alecsandro.timer;

import com.stolk.alecsandro.business.AgendamentoBusiness;
import com.stolk.alecsandro.modelo.Agendamento;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.List;

@Singleton
// @Startup (Estudar essa anotação) - import javax.ejb.Startup;
public class AgendamentoTimer {

   @Inject
    private AgendamentoBusiness agendamentoBusiness;

    @Inject
    @JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
    private JMSContext context;

    @Resource(mappedName = "java:/jms/queue/EmailQueue")
    private Queue queue;

    @Schedule(hour = "*", minute = "*")
    public void enviarEmailsAgendados() {
        List<Agendamento> agendamentos = agendamentoBusiness.listarNaoEnviados();
//        agendamentos.stream().forEach(agendamento -> agendamentoBusiness.enviar(agendamento));
        agendamentos.stream().forEach(agendamento -> {
            context.createProducer().send(queue, agendamento);
            agendamentoBusiness.marcarEnviado(agendamento);
        });
    }

}
