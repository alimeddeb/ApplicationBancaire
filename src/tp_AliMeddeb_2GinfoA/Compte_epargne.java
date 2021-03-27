package tp_AliMeddeb_2GinfoA;

import tp_AliMeddeb_2GinfoA.exception.ErrCompteEpargneBloque;
import tp_AliMeddeb_2GinfoA.exception.ErrDateCreationCompteEpargne;
import tp_AliMeddeb_2GinfoA.exception.ErrNegatif;
import tp_AliMeddeb_2GinfoA.exception.ErrValeurNegative;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class Compte_epargne extends CompteAbstrait {

    LocalDate date_dernier_versement;
    BigDecimal montant_dernier_versement;
    boolean actif = true;
    static int nombre_compte_epargne;

    public Compte_epargne(String rib, BigDecimal solde, Client client, LocalDate date_creation, BigDecimal montant_dernier_versement, LocalDate date_dernier_versement) throws ErrNegatif {
        super(rib, solde, client, date_creation);
        this.montant_dernier_versement = montant_dernier_versement;
        this.date_dernier_versement = date_dernier_versement;
        nombre_compte_epargne++;
    }


    @Override
    public boolean versement(BigDecimal montant) throws ErrValeurNegative, ErrCompteEpargneBloque {
        if (montant.compareTo(new BigDecimal(0)) <= 0) throw new ErrValeurNegative();
        if (actif) {
            System.out.println(date_dernier_versement);
            LocalDate date_jour = LocalDate.now();
            Period p = Period.between(date_dernier_versement, date_jour);
            if (p.getYears() == 0 && p.getMonths() == 0) {
                montant_dernier_versement = montant;
                setSolde(getSolde().add(montant));
                agence.crediter(montant);
                date_dernier_versement = date_jour;
                return true;
            }
            if (p.getYears() == 0 && p.getMonths() == 1) {
                if (montant_dernier_versement.compareTo(new BigDecimal(20)) >= 0) {
                    setSolde(getSolde().add(montant));
                    agence.crediter(montant);
                    date_dernier_versement = date_jour;
                    montant_dernier_versement = montant;
                    return true;
                } else {
                    setActif(false);
                    System.out.println("Compte epargne bloque ");
                    throw new ErrCompteEpargneBloque();
                }

            } else {
                setActif(false);
                System.out.println("Compte epargne bloque ");
                throw new ErrCompteEpargneBloque();
            }
        } else {
            System.out.println("Compte epargne deja bloque ");
            throw new ErrCompteEpargneBloque();
        }
    }

    @Override
    public boolean retrait(BigDecimal montant) throws ErrNegatif, ErrValeurNegative, ErrDateCreationCompteEpargne {
        if (montant.compareTo(new BigDecimal(0)) <= 0) throw new ErrValeurNegative();
        LocalDate date_jour = LocalDate.now();
        Period p = Period.between(date_creation, date_jour);
        if ((p.getYears() >= 1) && getSolde().compareTo(this.valeurMinSolde) >= 0) {
            if ((getSolde().subtract(montant)).compareTo(new BigDecimal(2000)) >= 0) {
                setSolde(getSolde().subtract(montant));
                agence.debiter(montant);
                System.out.println("Retrait epargne de " + montant);
                return true;
            } else {
                throw new ErrNegatif();
            }
        }
        System.out.println("Periode entre creation et retrait" + " Year:" + p.getYears() + " month: " + p.getMonths());
        throw new ErrDateCreationCompteEpargne();
    }

    @Override
    public boolean virement(CompteAbstrait c, BigDecimal montant) throws ErrNegatif, ErrValeurNegative {
        // virement interdit depuis un compte épargne
        return false;
    }


    @Override
    public String toString() {
        return "Compte_epargne{" +
                "rib='" + rib + '\'' +
                ", solde=" + solde +
                ", date_creation=" + date_creation +
                ", date_dernier_versement=" + date_dernier_versement +
                ", montant_dernier_versement=" + montant_dernier_versement +
                ", actif=" + actif +
                '}';
    }


    public LocalDate getDate_dernier_versement() {
        return date_dernier_versement;
    }

    public BigDecimal getMontant_dernier_versement() {
        return montant_dernier_versement;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public BigDecimal getValeurMinSolde() {
        return new BigDecimal(2000);
    }
}