package services;
import entities.Formation;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tools.Myconnexion;

public class formationServ implements Iservice<Formation> {
    Connection cnx2;

    public formationServ() {
        cnx2 = Myconnexion.getInstance().getCnx();
    }

    @Override
    public void addFormation(Formation formation) {
        String req = "INSERT INTO formation (nom,description,thematique,nbr_participant,niveau)" +
                " VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setString(1, formation.getNom());
            pst.setString(2, formation.getDescription());
            pst.setString(3, formation.getThematique());
            pst.setInt(4, formation.getNbr_participant());
            pst.setString(5, formation.getNiveau());
            pst.executeUpdate();
            System.out.println("votre formation a ete ajoute avc sucess");

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
    }

    @Override
    public void updateFormation(Formation formation) {
        String req2 = "UPDATE formation SET nom=?, description=? , thematique=?, nbr_participant=? " +
                "WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req2);
            pst.setString(1, formation.getNom());
            pst.setString(2, formation.getDescription());
            pst.setString(3, formation.getThematique());
            pst.setInt(4, formation.getNbr_participant());
            pst.setInt(5, formation.getId());
            pst.executeUpdate();

            System.out.println("Modification de la formation effectuee avec succes !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void DeleteFormation(Formation formation) {
        String req3 = "DELETE FROM formation WHERE id = ?";
        try {
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ps.setInt(1, formation.getId());
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("formation a ete supprimee avec succes.");
            } else {
                System.out.println("Failed.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public List<Formation> AfficherFormation() {
        List<Formation> myList = new ArrayList<>();
        try {
            String req1 = "SELECT * FROM FORMATION";
            Statement st = cnx2.createStatement();
            ResultSet RS = st.executeQuery(req1);
            while (RS.next()) {
                Formation f = new Formation();
                f.setId(RS.getInt(1));
                f.setNom(RS.getString("nom"));
                f.setDescription(RS.getString("description"));
                f.setThematique(RS.getString("thematique"));
                f.setNbr_participant(RS.getInt(5));
                f.setNiveau(RS.getString("niveau"));
                myList.add(f);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return myList;
    }

    public boolean nameExists(String nom) {
        String query = "SELECT count(*) FROM formation WHERE nom = ?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(query);
            pst.setString(1, nom);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }
    public int getFormationIdB(String nomF) {
        int formationId = -1; // Valeur par défaut
        try {
            // Préparez requête SQL pour récupérer l'ID de la formation à partir de son nom
            String sql = "SELECT id FROM formation WHERE nom = ?";
            PreparedStatement statement = cnx2.prepareStatement(sql);
            statement.setString(1, nomF);

            // Exécution de  la requête SQL
            ResultSet resultSet = statement.executeQuery();

            // Vérifiez si une formation correspondante est trouvée
            if (resultSet.next()) {
                formationId = resultSet.getInt("id"); // Récupérez l'ID de la formation
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Gérez l'erreur de connexion ou de requête SQL
        }
        return formationId;
    }

}


