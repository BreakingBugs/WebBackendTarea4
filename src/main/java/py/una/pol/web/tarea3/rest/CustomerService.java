package py.una.pol.web.tarea3.rest;

import py.una.pol.web.tarea3.controller.CustomerController;
import py.una.pol.web.tarea3.model.Customer;
import py.una.pol.web.tarea3.model.Order;
import py.una.pol.web.tarea3.model.Payment;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/clientes")
public class CustomerService {

    @Inject
    CustomerController customerController;

    @GET
    @Produces("application/json")
    public List<Customer> getCustomers() {
        return customerController.getCustomers();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Customer addCustomer(Customer newCustomer) {
        customerController.addCustomer(newCustomer);
        return newCustomer;
    }

    @POST
    @Path("/{id: [0-9]*}/sell")
    @Consumes("application/json")
    @Produces("application/json")
    public Response sell(List<Order> orders, @PathParam("id") Integer customerId) {
        try {
            customerController.sellToClient(customerId, orders);
            return Response.ok().build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{id: [0-9]*}/pay")
    @Consumes("application/json")
    @Produces("application/json")
    public Response pay(Payment payment, @PathParam("id") Integer customerId) {
        if (customerController.addPayment(customerId, payment)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id: [0-9]*}")
    @Produces("application/json")
    public Response getCustomer(@PathParam("id") Integer id) {
        Customer c = customerController.getCustomer(id);
        if (c != null) {
            return Response.ok(c).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id: [0-9]*}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateCustomer(@PathParam("id") Integer id, Customer updatedCustomer) {
        Customer c = customerController.updateCustomer(id, updatedCustomer);
        if (c != null) {
            return Response.ok(c).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id: [0-9]*}")
    public Response removeCustomer(@PathParam("id") Integer id) {
        customerController.removeCustomer(id);
        return Response.ok().build();
    }
}
