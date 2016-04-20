package py.una.pol.web.tarea4.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.una.pol.web.tarea4.controller.ItemController;
import py.una.pol.web.tarea4.model.Item;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Path("/productos")
public class ItemService {
    @Inject
    ItemController itemController;

    @GET
    @Produces("application/json")
    public Response getItems() {
        return Response.ok(itemController.getItems()).build();
    }

    @GET
    @Path("/export")
    @Produces("multipart/form-data")
    public Response exportItems() {

        return Response.ok(itemController.getItems())
                .header("Content-Disposition", "attachment; filename = items.json")
                .build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addItems(String jsonString) {
        ObjectMapper om = new ObjectMapper();

        try {
            JsonNode node = om.readValue(jsonString, JsonNode.class);
            if (node.isArray()) {
                List<Item> items = Arrays.asList(om.readValue(jsonString, Item[].class));
                addBatch(items);
                return Response.ok(items).build();
            } else {
                Item item = om.readValue(jsonString, Item.class);
                addSingle(item);
                return Response.ok(item).build();
            }
        } catch (JsonMappingException e) {
            Logger logger = LoggerFactory.getLogger(ItemService.class);
            logger.error(e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (JsonParseException e) {
            Logger logger = LoggerFactory.getLogger(ItemService.class);
            logger.error(e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (IOException e) {
            Logger logger = LoggerFactory.getLogger(ItemService.class);
            logger.error(e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private void addBatch(List<Item> items) {
        itemController.batchAddItem(items);
    }

    private void addSingle(Item item) {
        itemController.addItem(item);
    }

    @GET
    @Path("/{id: [0-9]*}")
    @Produces("application/json")
    public Response getItem(@PathParam("id") Integer id) {
        Item p = itemController.getItem(id);
        if (p != null) {
            return Response.ok(p).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search")
    @Produces("application/json")
    public Response getItemByName(@QueryParam("name") String name) {
        Item p = itemController.getItemByName(name);
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
    public Response updateItem(@PathParam("id") Integer id, Item updatedItem) {
        Item p = itemController.updateItem(id, updatedItem);
        if (p != null) {
            return Response.ok(p).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id: [0-9]*}")
    public Response removeItem(@PathParam("id") Integer id) {
        itemController.removeItem(id);
        return Response.ok().build();
    }


}
