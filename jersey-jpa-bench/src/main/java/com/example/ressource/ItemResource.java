package com.example.ressource;


import com.example.dto.ItemDto;
import com.example.service.ItemService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {
    private final ItemService svc = new ItemService();

    @GET
    public Response list(@QueryParam("page") @DefaultValue("0") int page,
                         @QueryParam("size") @DefaultValue("50") int size,
                         @QueryParam("categoryId") Long categoryId,
                         @QueryParam("joinFetch") @DefaultValue("false") boolean joinFetch){
        if(categoryId != null){
            List<ItemDto> items = svc.findByCategory(categoryId, page, size, joinFetch);
            return Response.ok(items).build();
        } else {
            return Response.ok(svc.list(page,size)).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id){
        ItemDto dto = svc.find(id);
        if(dto == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(dto).build();
    }

    @POST
    public Response create(ItemDto dto){
        ItemDto created = svc.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ItemDto dto){
        ItemDto updated = svc.update(id, dto);
        if(updated == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        boolean ok = svc.delete(id);
        return ok ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
