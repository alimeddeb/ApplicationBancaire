// Code redigé par Ali Meddeb 2GinfoA
package tp_AliMeddeb_2GinfoA;

import tp_AliMeddeb_2GinfoA.exception.ErrCompteEpargneBloque;
import tp_AliMeddeb_2GinfoA.exception.ErrNegatif;
import tp_AliMeddeb_2GinfoA.exception.ErrSoldeEnRouge;
import tp_AliMeddeb_2GinfoA.exception.ErrValeurNegative;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Compte extends CompteAbstrait {
    static int nombre_compte_normal_banque;


    public Compte(String rib, BigDecimal solde, Client client, LocalDate date) throws ErrNegatif {
        super(rib, solde, client, date);
        nombre_compte_normal_banque++;
    }

    public boolean versement(BigDecimal montant) throws ErrValeurNegative {
        if (montant.compareTo(new BigDecimal(0)) <= 0) throw new ErrValeurNegative();
        this.solde = this.solde.add(montant);
        agence.crediter(montant);
        return true;
    }


    public boolean retrait(BigDecimal s) throws ErrValeurNegative, ErrNegatif {
        if (s.compareTo(new BigDecimal(0)) <= 0) throw new ErrValeurNegative();
        if (solde.compareTo(s) >= 0) {
            agence.debiter(s);
            solde = solde.subtract(s);
            return true;
        } else {
            if ((getsolde().subtract(s)).compareTo(new BigDecimal(-0.05 * ((Client_salarie) this.client).getSalaire())) > 0)
                return true;
            else throw new ErrSoldeEnRouge();
        }
    }

    @Override
    public boolean virement(CompteAbstrait c, BigDecimal montant) throws ErrSoldeEnRouge, ErrNegatif, ErrValeurNegative, ErrCompteEpargneBloque {
        if (retrait(montant)) {
            c.versement(montant);
            return true;
        }
        return false;
    }

    public BigDecimal getsolde() {
        return (this.solde);
    }


    public static boolean compare(Compte c1, Compte c2) {
        return c1.getsolde().compareTo(c2.getsolde()) > 0;
    }

    public boolean compare(Compte autre) {
        return this.getsolde().compareTo(autre.getsolde()) > 0;
    }

    public static void tri(Compte[] tabCompte) {
        boolean sorted = false;
        while (sorted == false) {
            sorted = true;
            for (int i = 0; i < tabCompte.length - 1; i++) {
                if (tabCompte[i].compare(tabCompte[i + 1]) == true) {

                    Compte sub = tabCompte[i];
                    tabCompte[i] = tabCompte[i + 1];
                    tabCompte[i + 1] = sub;
                    sorted = false;
                }
            }
        }
        return;
    }

}
