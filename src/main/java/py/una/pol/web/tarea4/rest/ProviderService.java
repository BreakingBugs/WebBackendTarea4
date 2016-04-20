package py.una.pol.web.tarea4.rest;

import py.una.pol.web.tarea4.controller.ProviderController;
import py.una.pol.web.tarea4.model.Order;
import py.una.pol.web.tarea4.model.Provider;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/proveedores")
public class ProviderService {
    @Inject
    ProviderController providerController;

    @GET
    @Produces("application/json")
    public List<Provider> getProviders() {
        return providerController.getProviders();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Provider addProvider(Provider newProvider) {
        System.out.println("newProvider: ");
        System.out.println(newProvider.getName());
        providerController.addProvider(newProvider);
        return newProvider;
    }

    @POST
    @Path("/{id: [0-9]*}/buy")
    @Consumes("application/json")
    @Produces("application/json")
    public Response sell(List<Order> orders, @PathParam("id") Integer providerId) {
        if (providerController.buyFromProvider(providerId, orders)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id: [0-9]*}")
    @Produces("application/json")
    public Response getProvider(@PathParam("id") Integer id) {
        Provider p = providerController.getProvider(id);
        if (p != null) {
            return Response.ok(p).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id: [0-9]*}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateProvider(@PathParam("id") Integer id, Provider updatedProvider) {
        Provider p = providerController.updateProvider(id, updatedProvider);
        if (p != null) {
            return Response.ok(p).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id: [0-9]*}")
    public Response removeProvider(@PathParam("id") Integer id) {
        providerController.removeProvider(id);
        return Response.ok().build();
    }


}
