package graphql;

// Import the business logic class responsible for managing "UniteEnseignement" data
import business.UniteEnseignementBusiness;

// Import the GraphQL library class used to define the root of GraphQL mutations
import com.coxautodev.graphql.tools.GraphQLRootResolver;

// Import the entity class representing a Teaching Unit (Unité d’Enseignement)
import entities.UniteEnseignement;
import business.ModuleBusiness;
import entities.Module;

/**
 * This class defines all GraphQL mutations related to the "UniteEnseignement" entity.
 * A mutation in GraphQL is used to perform write operations (create, update, delete)
 * as opposed to queries, which are for read operations.
 */
public class MutationResolver implements GraphQLRootResolver {

    // Attribute used to access the business logic (service layer)
    public UniteEnseignementBusiness helper;
    private ModuleBusiness moduleHelper;

    /**
     * Constructor of the resolver.
     * It initializes the business helper that manages the teaching units.
     */
    public MutationResolver() {
        helper = new UniteEnseignementBusiness();
        moduleHelper = new ModuleBusiness();
    }

    /**
     * GraphQL Mutation: addUniteEnseignement
     *
     * This method allows clients to add a new "Unité d’Enseignement" to the system.
     *
     * When a GraphQL mutation like the following is executed:
     *
     * mutation {
     *     addUniteEnseignement(
     *         code: 101,
     *         domaine: "Informatique",
     *         responsable: "Dr. Smith",
     *         credits: 4,
     *         semestre: 2
     *     )
     * }
     *
     * GraphQL calls this Java method to create and save a new teaching unit.
     */
    public boolean addUniteEnseignement(int code, String domaine, String responsable, int credits, int semestre) {
        // Create a new UniteEnseignement object with the provided parameters
        UniteEnseignement ue = new UniteEnseignement(code, domaine, responsable, credits, semestre);

        // Call the business logic method to add the new unit
        // The helper handles the actual persistence or logic of adding the entity
        return helper.addUniteEnseignement(ue);
    }
    public boolean updateUniteEnseignement(
            int code,
            String domaine,
            String responsable,
            int credits,
            int semestre) {

        UniteEnseignement updatedUE = new UniteEnseignement(
                code, domaine, responsable, credits, semestre
        );

        return helper.updateUniteEnseignement(code, updatedUE);
    }

    // Supprimer une unite d'enseignement
    public boolean deleteUniteEnseignement(int code) {
        return helper.deleteUniteEnseignement(code);
    }

    // Ajouter un module
    public boolean addModule(
            String matricule,
            String nom,
            int coefficient,
            int volumeHoraire,
            String type,
            int codeUE) {

        UniteEnseignement ue = helper.getUEByCode(codeUE);

        if (ue == null) {
            return false;
        }

        try {
            Module.TypeModule typeModule =
                    Module.TypeModule.valueOf(type.toUpperCase());

            Module module = new Module(
                    matricule, nom, coefficient,
                    volumeHoraire, typeModule, ue
            );

            return moduleHelper.addModule(module);

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Modifier un module
    public boolean updateModule(
            String matricule,
            String nom,
            int coefficient,
            int volumeHoraire,
            String type,
            int codeUE) {

        UniteEnseignement ue = helper.getUEByCode(codeUE);

        if (ue == null) {
            return false;
        }

        try {
            Module.TypeModule typeModule =
                    Module.TypeModule.valueOf(type.toUpperCase());

            Module updatedModule = new Module(
                    matricule, nom, coefficient,
                    volumeHoraire, typeModule, ue
            );

            return moduleHelper.updateModule(matricule, updatedModule);

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Supprimer un module
    public boolean deleteModule(String matricule) {
        return moduleHelper.deleteModule(matricule);
    }
}
