package tp_AliMeddeb_2GinfoA;

public class Admin {
    String login;
    String mdp;
    String nom, prenom;

    public Admin(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public String getMdp() {
        return mdp;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
