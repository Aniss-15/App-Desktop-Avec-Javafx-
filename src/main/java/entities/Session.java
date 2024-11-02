package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Session {
    private int id;
    private String date_d;
    private String date_f;
    private String nom;
    private List<Formation> formations ;


    public Session(int id, String date_d, String date_f, String nomSession, List<Formation> formations) {
        this.id = id;
        this.date_d= date_d;
        this.date_f = date_f;
        this.nom=nomSession;
        this.formations = new ArrayList<>(formations);
    }

    public Session(String date_d, String date_f, String nom, List<Formation> formations) {
        this.date_d = date_d;
        this.date_f = date_f;
        this.nom = nom;
        this.formations = formations;
    }

    public Session() {
        formations = new ArrayList<>();
    }

    public String getNomSession() {
        return nom;
    }

    public void setNomSession(String nomSession) {
        this.nom = nomSession;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_d() {
        return date_d;
    }

    public void setDate_d(String date_d) {
        this.date_d = date_d;
    }

    public String getDate_f() {
        return date_f;
    }

    public void setDate_f(String date_f) {
        this.date_f = date_f;
    }

    public List<Formation> getFormations() {
        return formations;
    }

    public void setFormations(List<Formation> formations) {
        this.formations = formations;
    }

    public void addFormation(Formation formation) {
        if (this.formations == null) {
            this.formations = new ArrayList<>();
        }
        this.formations.add(formation);
    }

    public void removeFormation(Formation formation) {
        if (this.formations != null) {
            this.formations.remove(formation);
        }
    }
    public String getFormationsString() {
        // conversion de liste en stream et recuperez le nom de chaque formation *//
        return formations.stream()
                .map(Formation::getNom)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", date_d='" + date_d + '\'' +
                ", date_f='" + date_f + '\'' +
                ", nomSession='" + nom+ '\'' +
                ", formations=" + formations.stream().map(Formation::toString).collect(Collectors.toList()) +
                '}';
    }
}
