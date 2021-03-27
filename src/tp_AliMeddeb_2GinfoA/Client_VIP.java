package tp_AliMeddeb_2GinfoA;

import java.math.BigDecimal;

public class Client_VIP extends Client {
    String nomEntreprise;
    int nombreEmployes;
    BigDecimal chiffreAffaire;

    public Client_VIP(String nom, String prenom, String CIN, Agence agence, String nomEntreprise, int nombreEmploye, BigDecimal chiffreAffaire ) {
        super(nom, prenom, CIN, agence, null);
        this.nomEntreprise = nomEntreprise;
        this.nombreEmployes = nombreEmploye;
        this.chiffreAffaire = chiffreAffaire;
    }

    @Override
    public String toString() {
        return  "Client VIP: " +
                "\nNom Complet= " + prenom + " " + nom + " | CIN= "+CIN +" | Profession= " + profession
                +"\nEntreprise= " + nomEntreprise +" ("+ nombreEmployes    + " employés) | Chiffre d'affaire= " + chiffreAffaire
                ;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public int getNombreEmploye() {
        return nombreEmployes;
    }

    public void setNombreEmploye(int nombreEmploye) {
        this.nombreEmployes = nombreEmploye;
    }

    public BigDecimal getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(BigDecimal chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }
}
