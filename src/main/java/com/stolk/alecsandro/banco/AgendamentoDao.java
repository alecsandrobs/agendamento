package com.stolk.alecsandro.banco;

import com.stolk.alecsandro.modelo.Agendamento;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class AgendamentoDao {

    @PersistenceContext
    private EntityManager em;

    public List<Agendamento> listar() {
        return em.createQuery("select a from Agendamento a", Agendamento.class).getResultList();
    }

    public void salvar(Agendamento agendamento) {
        em.persist(agendamento);
    }

    public void atualizar(Agendamento agendamento) {
        em.merge(agendamento);
    }

    public List<Agendamento> listar(String email) {
        Query query = em.createQuery("select a from Agendamento a where a.email = :pEmail and a.enviado = false", Agendamento.class);
        query.setParameter("pEmail", email);
        return query.getResultList();
    }

    public List<Agendamento> listarNaoEnviados() {
        Query query = em.createQuery("select a from Agendamento a where a.enviado = false", Agendamento.class);
        return query.getResultList();
    }
}
