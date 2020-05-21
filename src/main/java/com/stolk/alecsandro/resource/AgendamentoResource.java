package com.stolk.alecsandro.resource;

import com.stolk.alecsandro.business.AgendamentoBusiness;
import com.stolk.alecsandro.exception.BusinessException;
import com.stolk.alecsandro.modelo.Agendamento;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("agendamentos")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class AgendamentoResource {

    @Inject
    private AgendamentoBusiness agendamentoBusiness;

    @GET
    public Response listar() {
        List<Agendamento> emails = agendamentoBusiness.listar();
        return Response.ok(emails).build();
    }

    @POST
    public Response salvar(Agendamento agendamento) throws BusinessException {
        agendamentoBusiness.salvar(agendamento);
        return Response.created(URI.create(String.format("/agendamentos/%s", agendamento.getId()))).build();
    }
}
