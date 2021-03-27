package tp_AliMeddeb_2GinfoA;

public class Client {
    String nom, prenom, CIN, profession;
    Agence agence;

    public Client(String nom, String prenom, String CIN, Agence agence, String profession) {
        this.agence = agence;
        this.nom = nom;
        this.prenom = prenom;
        this.CIN = CIN;
        this.profession = profession;
    }

    public String getCIN() {
        return CIN;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getProfession() {
        return profession;
    }

    public Agence getAgence() {
        return agence;
    }
}
