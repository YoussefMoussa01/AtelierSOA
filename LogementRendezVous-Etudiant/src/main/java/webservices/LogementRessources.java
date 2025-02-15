package webservices;


import metiers.LogementBusiness;
import entities.Logement;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/logement")
public class LogementRessources {
    static LogementBusiness help = new LogementBusiness();
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response  getAll(){
        return Response.
                status(200).header("Access-Control-Allow-Origin", "*").
                entity(help.getLogements()).
                build();
    }

    @GET
    @Path("/{reference}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByReference(@PathParam("reference") int reference) {
        Logement logement = help.getLogementsByReference(reference);
        if (logement != null) {
            return Response.status(200).entity(logement).build();
        } else {
            return Response.status(404).entity("Logement non trouvé").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addLogement(Logement logement) {
        boolean added = help.addLogement(logement);
        if (added) {
            return Response.status(201).entity("Logement ajouté avec succès").build();
        } else {
            return Response.status(500).entity("Erreur lors de l'ajout du logement").build();
        }
    }

    @PUT
    @Path("/update/{reference}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLogement(@PathParam("reference") int reference, Logement logement) {
        // Vérifier si l'ID dans le corps de la requête correspond à l'ID dans l'URL
        if (logement.getReference() != reference) {
            return Response.status(400)
                    .entity("L'ID dans l'URL ne correspond pas à l'ID dans le corps de la requête")
                    .build();
        }

        // Vérifier si le logement existe dans la base de données avant de mettre à jour
        Logement existingLogement = help.getLogementsByReference(reference);
        if (existingLogement == null) {
            return Response.status(404)
                    .entity("Logement non trouvé pour la référence spécifiée")
                    .build();
        }

        // Effectuer la mise à jour
        boolean updated = help.updateLogement(reference, logement);
        if (updated) {
            return Response.status(200)
                    .entity("Logement mis à jour avec succès")
                    .build();
        } else {
            return Response.status(500)
                    .entity("Erreur lors de la mise à jour du logement")
                    .build();
        }
    }


    @DELETE
    @Path("/delete/{reference}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLogement(@PathParam("reference") int reference) {
        boolean deleted = help.deleteLogement(reference);
        if (deleted) {
            return Response.status(200).entity("Logement supprimé avec succès").build();
        } else {
            return Response.status(404).entity("Logement non trouvé").build();
        }
    }
}
