
package webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

@Path("/UE")
public class UniteEnsRestApi {

    private UniteEnseignementBusiness helper;

    public UniteEnsRestApi(UniteEnseignementBusiness helper) {
        this.helper = helper;
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListUe() {
        return Response
                .status(200)
                .entity(helper.getListeUE())
                .build();
    }

    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUe(UniteEnseignement ue) {
        if (helper.addUniteEnseignement(ue)) {
            return Response.status(201)
                    .entity("Success")
                    .build();
        } else {
            return Response.status(400)
                    .entity("Error")
                    .build();
        }
    }

    @Path("/update/{code}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUe(
            @PathParam("code") int code,
            UniteEnseignement ue) {

        if (helper.updateUniteEnseignement(code, ue)) {
            return Response.status(200)
                    .entity("Success")
                    .build();
        } else {
            return Response.status(404)
                    .entity("Error")
                    .build();
        }
    }

    @Path("/delete/{code}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUe(@PathParam("code") int code) {
        if (helper.deleteUniteEnseignement(code)) {
            return Response.status(200)
                    .entity("Success")
                    .build();
        } else {
            return Response.status(404)
                    .entity("Error")
                    .build();
        }
    }
}
