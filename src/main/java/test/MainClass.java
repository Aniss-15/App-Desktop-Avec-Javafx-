package test;
import entities.Formation;
import entities.Session;
import services.SessionServ;
import services.formationServ;

import tools.Myconnexion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        Myconnexion mc = Myconnexion.getInstance();
        Myconnexion mc2 = Myconnexion.getInstance();
        System.out.println(mc.hashCode() + '_' + mc2.hashCode());
        formationServ fs = new formationServ();
        SessionServ ss = new SessionServ();
        Formation f1 = new Formation(90, "kess", "developpement", "you", 10, "beginner");


        /*Formation f2 = new Formation(125,"hhhh","test","abc",12,"advanced");
      /*  Formation f3 = new Formation("developpement test","bcge","vcc",10,"beginner");
        /*  fs.updateFormation(f1);*/
        /* fs.DeleteFormation(f2);*/
        /* fs.addFormation(f1);*/
        /* System.out.println(fs.AfficherFormation());*/
        /*
        Session nouvelleSession1 = new Session();
        nouvelleSession1.setDate_d("2024-01-01"); // Utilisez des dates au format approprié pour votre base de données
        nouvelleSession1.setDate_f("2024-06-01");
        nouvelleSession1.setNomSession("Session juillet 2024");
        /*ss.ajouterSession(nouvelleSession1);*/
        /*

       List<Formation> formations = new ArrayList<>();
        formations.add(f1);
        Session nouvelleSession = new Session(10,"23/05/27888","23/06/2011","hhh",formations);
        ss.ajouterSession(nouvelleSession);


        List<Formation> formationss = new ArrayList<>();
        nouvelleSession.setFormations(formationss);*/
        /*

        List<Session> sessions = ss.AfficherSession();
        for (Session session : sessions) {
            System.out.println("Session ID: " + session.getId());
            System.out.println("Session Start Date: " + session.getDate_d());
            System.out.println("Session End Date: " + session.getDate_f());
            System.out.println("Session Name: " + session.getNomSession());
            System.out.println("Associated Formations:");
            for (Formation formation : session.getFormations()) {
                System.out.println(" - Formation ID: " + formation.getId());
                System.out.println(" - Formation Name: " + formation.getNom());
                // ... affichez d'autres détails de formation si nécessaire
            }
            // Appel de la méthode pour ajouter la session et ses formations associées dans la base

        }
*/
        List<Session> sessions = ss.getSessionBientot();
        System.out.println("Expiring or Expired Sessions:");
        for (Session session : sessions) {
            System.out.println("Session ID: " + session.getId());
            System.out.println("Session Name: " + session.getNomSession());
            System.out.println("Start Date: " + session.getDate_d());
            System.out.println("End Date: " + session.getDate_f());
            List<Formation> formations = session.getFormations();
            if (formations != null) {
                for (Formation formation : formations) {
                    System.out.println("\tFormation ID: " + formation.getId());
                    System.out.println("\tFormation Name: " + formation.getNom());
                }
            } else {
                System.out.println("\tNo formations available for this session.");
            }
        }
    }
}

