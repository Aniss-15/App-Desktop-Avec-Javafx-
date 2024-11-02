package services;

import entities.Formation;
import entities.Session;

import tools.Myconnexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class SessionServ implements servicef<Session> {
    Connection cnx2;
    public SessionServ() {
        cnx2= Myconnexion.getInstance().getCnx();
    }

    @Override
    public void ajouterSession(Session session) {

        String reqS = "INSERT INTO session (date_d, date_f,nom) VALUES(?,?,?)";
        String reqSF = "INSERT INTO session_formation (session_id,formation_id) VALUES(?,?)";

        try {
            // Préparation de la requête pour insérer la session
            PreparedStatement pstSession = cnx2.prepareStatement(reqS, Statement.RETURN_GENERATED_KEYS);

            pstSession.setString(1, session.getDate_d());
            pstSession.setString(2, session.getDate_f());
            pstSession.setString(3, session.getNomSession());
            pstSession.executeUpdate();

            // Obtention de l'ID de session généré
            ResultSet rs = pstSession.getGeneratedKeys();
            if (rs.next()) {
                int sessionId = rs.getInt(1);
                session.setId(sessionId); // Mettre à jour l'ID de la session avec l'ID généré

                // Préparation de la requête pour insérer dans session_formation
                if (session.getFormations() != null) {
                    PreparedStatement pstSF = cnx2.prepareStatement(reqSF);
                    for (Formation f : session.getFormations()) {
                        pstSF.setInt(1, sessionId);
                        pstSF.setInt(2, f.getId());
                        pstSF.executeUpdate();
                    }
                } else {
                    System.err.println("aucune formation n'est ajoutée .");
                }
                System.out.println("Votre session a été ajoutée avec succès.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void UpdateSession(Session session) {
        String reqSession = "UPDATE session SET date_d = ?, date_f = ?, nom = ? WHERE id = ?";
        //cette requette est pour supprimer la formation associe a la session
        String reqDeleteSF = "DELETE FROM session_formation WHERE session_id = ?";
        //apres inserer la nouvelle session associe
        String reqInsertSF = "INSERT INTO session_formation (session_id, formation_id) VALUES(?,?)";

        try {
            PreparedStatement pstSession = cnx2.prepareStatement(reqSession);
            pstSession.setString(1, session.getDate_d());
            pstSession.setString(2, session.getDate_f());
            pstSession.setString(3, session.getNomSession());
            pstSession.setInt(4, session.getId());
            pstSession.executeUpdate();

            PreparedStatement pstDeleteSF = cnx2.prepareStatement(reqDeleteSF);
            pstDeleteSF.setInt(1, session.getId());
            pstDeleteSF.executeUpdate();

            PreparedStatement pstInsertSF = cnx2.prepareStatement(reqInsertSF);
            for (Formation f : session.getFormations()) {
                pstInsertSF.setInt(1, session.getId());
                pstInsertSF.setInt(2, f.getId());
                pstInsertSF.executeUpdate();
            }

            System.out.println("La session et les formations associées ont été mises à jour avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();

        }
    }

    @Override
    public void DeleteSession(Session session) {
        String req3 = "DELETE FROM session WHERE id = ?";
        String req4 = "DELETE FROM session_formation WHERE session_id = ?";

        try {
            PreparedStatement psFormation = cnx2.prepareStatement(req4);
            psFormation.setInt(1, session.getId());
            int rowsFormation = psFormation.executeUpdate();

            // Préparer et exécuter la requête pour supprimer la session de 'session'
            PreparedStatement psSession = cnx2.prepareStatement(req3);
            psSession.setInt(1, session.getId());
            int rowsSession = psSession.executeUpdate();

            // Vérifiez si les deux suppressions ont été réalisées avec succès
            if (rowsFormation > 0 && rowsSession > 0) {
                System.out.println("La session et les formations associées ont été supprimées avec succès.");
            } else {
                System.out.println("Échec de la suppression.");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL: " + e.getMessage());

        }

    }


    public List<Session> AfficherSession() {
        List<Session> sessions = new ArrayList<>();
        Map<Integer, Session> sessionMap = new HashMap<>();

        String req = "SELECT s.id AS session_id, s.date_d, s.date_f, s.nom AS session_nom, sf.formation_id, f.nom AS formation_nom " +
                "FROM `session` s " +
                "JOIN session_formation sf ON s.id = sf.session_id " +
                "JOIN formation f ON sf.formation_id = f.id";


        try {
            Statement st = cnx2.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                int sessionId = rs.getInt("session_id");
                String dateD = rs.getString("date_d");
                String dateF = rs.getString("date_f");
                String sessionNom = rs.getString("session_nom");
                int formationId = rs.getInt("formation_id");
                String formationNom = rs.getString("formation_nom");


               // Debug information

                Session session = sessionMap.get(sessionId);
                if (session == null) {
                    session = new Session();
                    session.setId(sessionId);
                    session.setDate_d(rs.getString("date_d"));
                    session.setDate_f(rs.getString("date_f"));
                    session.setNomSession(sessionNom);
                    session.setFormations(new ArrayList<>(formationId)); // Initialise la liste de formations
                    sessionMap.put(sessionId, session);
                }

                if (formationNom != null && !formationNom.trim().isEmpty()) {
                    List<Formation> formations = session.getFormations();
                    boolean formationExists = formations.stream().anyMatch(f -> f.getNom().equals(formationNom));
                    if (!formationExists) {
                        Formation formation = new Formation();
                        formation.setNom(formationNom);
                        formations.add(formation);
                    }
                }
            }
            sessions.addAll(sessionMap.values());
        } catch (SQLException e) {
            System.err.println("Erreur SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return sessions;
    }
    public List<Session> getSessionBientot() {
        List<Session> sessionexpire = new ArrayList<>();
        // Définir le nombre de jours avant expiration
        int jour= 1;
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(jour);

        String query = "SELECT * FROM session WHERE date_f > ? AND date_f <= ?";

        try (PreparedStatement pst = cnx2.prepareStatement(query)) {
            pst.setString(1, currentDate.toString()); // Date actuelle
            pst.setString(2, futureDate.toString()); // Date future 5 jours apres la date actuelle

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Session session = new Session();
                session.setId(rs.getInt("id"));
                session.setDate_d(rs.getString("date_d"));
                session.setDate_f(rs.getString("date_f"));
                session.setNomSession(rs.getString("nom"));
                session.setFormations(getFormationsForSession(session.getId()));
                LocalDate sessionfin = LocalDate.parse(session.getDate_f());
                long jourf = DAYS.between(currentDate, sessionfin);
                if(jourf<=0)
                {
                    DeleteSession(session);
                }else {
                        sessionexpire.add(session);
                    }
                }
        } catch (SQLException e) {
            System.err.println("Error fetching sessions expiring soon: " + e.getMessage());
            e.printStackTrace();
        }
        return sessionexpire;
    }

    private List<Formation> getFormationsForSession(int sessionId) {
        List<Formation> formations = new ArrayList<>();
        String query = "SELECT f.* FROM formation f JOIN session_formation sf ON f.id = sf.formation_id WHERE sf.session_id = ?";
        try (PreparedStatement pst = cnx2.prepareStatement(query)) {
            pst.setInt(1, sessionId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Formation formation = new Formation();
                formation.setId(rs.getInt("id"));
                formation.setNom(rs.getString("nom"));
                formation.setDescription(rs.getString("Description"));
                formations.add(formation);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching formations for session: " + e.getMessage());
            e.printStackTrace();
        }
        return formations;
    }

}

