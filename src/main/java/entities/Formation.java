package entities;

import java.util.List;

public class Formation {
        private int id ;
        private String nom ;
        private String description ;
        private String thematique ;
        private int nbr_participant ;
        private String niveau;



        public Formation(int id , String nom , String description , String thematique , int nbr_participant,String niveau){
            this.id=id;
            this.nom=nom;
            this.description=description;
            this.thematique=thematique;
            this.nbr_participant=nbr_participant;
            this.niveau=niveau;

        }
        public Formation() {
            //this.id = id;
            this.nom=nom;
            this.description = description;
            this.thematique = thematique;
            this.nbr_participant = nbr_participant;
            this.niveau = niveau;
        }

    public Formation(String nom, String description, String thematique, int nbr_participant, String niveau) {
        this.nom = nom;
        this.description = description;
        this.thematique = thematique;
        this.nbr_participant = nbr_participant;
        this.niveau = niveau;
    }

    public int getId() {
            return id;
        }

        public String getNom() {
            return nom;
        }

    public String getDescription() {
        return description;
    }

    public String getThematique() {
            return thematique;
        }

        public int getNbr_participant() {
            return nbr_participant;
        }

        public String getNiveau() {
            return niveau;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setThematique(String thematique) {
            this.thematique = thematique;
        }

        public void setNbr_participant(int nbr_participant) {
            this.nbr_participant = nbr_participant;
        }

        public void setNiveau(String niveau) {
            this.niveau = niveau;
        }

        @Override
        public String toString() {
            return "formation{" +
                    "id=" + id +
                    "nom" + nom +
                    ", description='" + description + '\'' +
                    ", thematique='" + thematique + '\'' +
                    ", nbr_participant=" + nbr_participant +
                    ", niveau='" + niveau + '\'' +
                    '}';
        }


}
