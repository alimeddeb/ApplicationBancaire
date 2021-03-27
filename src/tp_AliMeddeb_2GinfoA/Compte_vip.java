package tp_AliMeddeb_2GinfoA;

import tp_AliMeddeb_2GinfoA.exception.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Compte_vip extends CompteAbstrait {

    Client_VIP client;
    static int nombre_compte_vip = 0;
    BigDecimal pourcentage_min = new BigDecimal(-0.005); //0.5%
    BigDecimal pret;
    BigDecimal chiffre;

    public Compte_vip(String rib, BigDecimal solde, Client_VIP client, LocalDate date) throws ErrNegatif {
        super(rib, solde, client, date);
        System.out.println("ChiffeAffaire: " + client.getChiffreAffaire());
        nombre_compte_vip++;
        this.valeurMinSolde = client.chiffreAffaire.multiply(pourcentage_min);
        this.valeurMinSolde = this.valeurMinSolde.setScale(3, RoundingMode.HALF_UP);
        this.pret = new BigDecimal(0);
        chiffre = client.getChiffreAffaire();
    }

    public boolean versement(BigDecimal montant) throws ErrValeurNegative {
        if (montant.compareTo(new BigDecimal(0)) <= 0) throw new ErrValeurNegative();
        setSolde(getSolde().add(montant));
        agence.crediter(montant);
        return true;
    }

    public boolean retrait(BigDecimal montant) throws ErrSoldeEnRouge, ErrNegatif, ErrValeurNegative {
        if (montant.compareTo(new BigDecimal(0)) <= 0) throw new ErrValeurNegative();
        if (this.getSolde().subtract(montant).compareTo(valeurMinSolde) >= 0) {
            setSolde(getSolde().subtract(montant));
            agence.debiter(montant);
            return true;
        }
        throw new ErrSoldeEnRouge();
    }

    public boolean pret(BigDecimal montant) throws ErrValeurNegative, ErrPretLimiteDepasse {
        BigDecimal maxPret = this.chiffre.multiply(new BigDecimal(0.3));
        maxPret = maxPret.setScale(0, RoundingMode.UP);
        if ((this.pret.add(montant)).compareTo(maxPret) <= 0) {
            this.pret = this.pret.add(montant);
            return this.versement(montant);
        } else
            throw new ErrPretLimiteDepasse();
    }

    public boolean payer_pret(BigDecimal montant) throws ErrValeurNegative, ErrNegatif {
        if (montant.compareTo(new BigDecimal(0)) <= 0) throw new ErrValeurNegative();
        if (this.pret.compareTo(new BigDecimal(0)) == 0) return true;
        if (montant.compareTo(this.pret) > 0) montant = this.pret;
        if (getSolde().compareTo(montant) < 0) throw new ErrSoldeEnRouge("Solde insuffisant pour payer ce montant");
        retrait(montant);
        setPret(getPret().subtract(montant));
        return true;
    }

    @Override
    public boolean virement(CompteAbstrait c, BigDecimal montant) throws ErrNegatif, ErrValeurNegative, ErrCompteEpargneBloque {
        if (retrait(montant))
            return c.versement(montant);
        return false;
    }

    @Override
    public String toString() {
        return "Compte VIP | RIB: " + rib + "Solde: " + solde + " | Agennce: " + agence.getNomAgence()
                + "\n" + client;
    }

    public BigDecimal getValeurMinSolde() {
        return valeurMinSolde;
    }

    public BigDecimal getPret() {
        return pret;
    }

    public void setPret(BigDecimal pret) {
        this.pret = pret;
    }

}
