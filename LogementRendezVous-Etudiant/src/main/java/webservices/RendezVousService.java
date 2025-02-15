package webservices;

import entities.RendezVous;
import metiers.LogementBusiness;
import metiers.RendezVousBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rendezvous")
public class RendezVousService {

    static RendezVousBusiness rendezVousBusiness = new RendezVousBusiness();

    // 1. Ajouter un rendez-vous
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRendezVous(RendezVous rendezVous) {
        boolean result = rendezVousBusiness.addRendezVous(rendezVous);
        if (result) {
            return Response.status(Response.Status.CREATED).entity(rendezVous).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erreur lors de l'ajout du rendez-vous").build();
        }
    }

    // 2. Mettre à jour un rendez-vous existant
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRendezVous(@PathParam("id") int id, RendezVous updatedRendezVous) {
        boolean result = rendezVousBusiness.updateRendezVous(id, updatedRendezVous);
        if (result) {
            return Response.status(Response.Status.OK).entity(updatedRendezVous).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Rendez-vous non trouvé").build();
        }
    }

    // 3. Supprimer un rendez-vous par son ID
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRendezVous(@PathParam("id") int id) {
        boolean result = rendezVousBusiness.deleteRendezVous(id);
        if (result) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Rendez-vous non trouvé").build();
        }
    }

    // 4. Récupérer tous les rendez-vous
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRendezVous() {
        List<RendezVous> rendezVousList = rendezVousBusiness.getListeRendezVous();
        if (rendezVousList.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.OK).entity(rendezVousList).build();
    }

    // 5. Récupérer un rendez-vous par son ID
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRendezVousById(@PathParam("id") int id) {
        RendezVous rendezVous = rendezVousBusiness.getRendezVousById(id);
        if (rendezVous != null) {
            return Response.status(Response.Status.OK).entity(rendezVous).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Rendez-vous non trouvé").build();
        }
    }

    // 6. Récupérer les rendez-vous par référence de logement
    @GET
    @Path("/getByLogement/{reference}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRendezVousByLogementReference(@PathParam("reference") int reference) {
        List<RendezVous> rendezVousList = rendezVousBusiness.getListeRendezVousByLogementReference(reference);
        if (rendezVousList.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.OK).entity(rendezVousList).build();
    }
}
