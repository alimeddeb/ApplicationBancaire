package tp_AliMeddeb_2GinfoA;

import tp_AliMeddeb_2GinfoA.exception.ErrCompteEpargneBloque;
import tp_AliMeddeb_2GinfoA.exception.ErrDateCreationCompteEpargne;
import tp_AliMeddeb_2GinfoA.exception.ErrNegatif;
import tp_AliMeddeb_2GinfoA.exception.ErrValeurNegative;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public abstract class CompteAbstrait implements Comparablee {

    protected String rib;
    protected BigDecimal solde;
    protected Client client;
    protected Agence agence;
    protected LocalDate date_creation;
    static int nombre_compte_agence = 0;
    BigDecimal valeurMinSolde = new BigDecimal(0);
    BigDecimal valeurMinSoldeEpargne = new BigDecimal(2000);
    BigDecimal valeurMinSoldeVIP;
    BigDecimal pourcentage_min = new BigDecimal(-0.005); //0.5%

    public CompteAbstrait(String rib, BigDecimal solde, Client client, LocalDate date) throws ErrNegatif {
        if (client instanceof Client_VIP) {
            this.valeurMinSoldeVIP = ((Client_VIP) client).getChiffreAffaire().multiply(pourcentage_min);
            this.valeurMinSoldeVIP = this.valeurMinSoldeVIP.setScale(3, RoundingMode.HALF_UP);
            if (solde.compareTo(valeurMinSoldeVIP) < 0) throw new ErrNegatif();
        } else if (solde.compareTo(valeurMinSolde) < 0) throw new ErrNegatif();
        this.rib = rib;
        this.solde = solde;
        this.client = client;
        this.agence = this.client.getAgence();
        this.date_creation = date;
        this.agence.ajout_compte_dans_agence();
    }


    public static boolean compare(CompteAbstrait c1, CompteAbstrait c2) {
        return (c1.solde.compareTo(c2.solde) <= 0);
    }


    public boolean compare(CompteAbstrait c) {
        return (this.solde.compareTo(c.solde)) == 1;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }


    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public abstract boolean versement(BigDecimal montant) throws ErrValeurNegative, ErrCompteEpargneBloque;

    public abstract boolean retrait(BigDecimal montant) throws ErrValeurNegative, ErrNegatif, ErrDateCreationCompteEpargne;

    public abstract boolean virement(CompteAbstrait c, BigDecimal montant) throws ErrNegatif, ErrValeurNegative, ErrCompteEpargneBloque;

    public boolean plusPetitQue(Object o) {
        return (this.solde.compareTo((((CompteAbstrait) o).solde)) == -1);
    }

    public Client getClient() {
        return client;
    }

    public LocalDate getDate_creation() {
        return date_creation;
    }

    public static int getNombre_compte_agence() {
        return nombre_compte_agence;
    }

    public BigDecimal getValeurMinSolde() {
        return valeurMinSolde;
    }

    public BigDecimal getValeurMinSoldeEpargne() {
        return valeurMinSoldeEpargne;
    }

    public void setvaleurMinSoldeEpargne(BigDecimal valeurMinSoldeEpargne) {
        this.valeurMinSoldeEpargne = valeurMinSoldeEpargne;
    }

    public void setvaleurMinSolde(BigDecimal valeurMinSolde) {
        this.valeurMinSolde = valeurMinSolde;
    }
}