package com.example.ressource;

import com.example.dto.CategoryDto;
import com.example.service.CategoryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
    private final CategoryService svc = new CategoryService();

    @GET
    public Response list(@QueryParam("page") @DefaultValue("0") int page,
                         @QueryParam("size") @DefaultValue("50") int size){
        List<CategoryDto> res = svc.list(page, size);
        return Response.ok(res).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id){
        CategoryDto dto = svc.find(id);
        if(dto == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(dto).build();
    }

    @POST
    public Response create(CategoryDto dto){
        CategoryDto created = svc.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CategoryDto dto){
        CategoryDto updated = svc.update(id, dto);
        if(updated == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        boolean ok = svc.delete(id);
        return ok ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}/items")
    public Response categoryItems(@PathParam("id") Long id,
                                  @QueryParam("page") @DefaultValue("0") int page,
                                  @QueryParam("size") @DefaultValue("50") int size,
                                  @QueryParam("joinFetch") @DefaultValue("false") boolean joinFetch){
        // delegate to ItemService to fetch items by category
        com.example.service.ItemService is = new com.example.service.ItemService();
        return Response.ok(is.findByCategory(id, page, size, joinFetch)).build();
    }
}

