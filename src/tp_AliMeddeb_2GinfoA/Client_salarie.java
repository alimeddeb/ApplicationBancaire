package tp_AliMeddeb_2GinfoA;

public class Client_salarie extends Client {
    double salaire;

    public Client_salarie(String nom, String prenom, String CIN, Agence agence, String profession, double salaire) {
        super(nom, prenom, CIN, agence, profession);
        this.salaire = salaire;
    }

    public double getSalaire() {
        return salaire;
    }

    @Override
    public String toString() {
        return "Client Salarié: " +
                "\nNom Complet= " + prenom + " " + nom + " | CIN= " + CIN
                + "\nProfession= " + profession + " | salaire= " + salaire;
    }
}
