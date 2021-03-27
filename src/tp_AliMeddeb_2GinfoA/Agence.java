// Code redigé par Ali Meddeb 2GinfoA
package tp_AliMeddeb_2GinfoA;

import tp_AliMeddeb_2GinfoA.exception.ErrNegatif;
import tp_AliMeddeb_2GinfoA.exception.ErrValeurNegative;

import java.math.BigDecimal;

public class Agence {
    private int numero_agence;
    private String nomAgence;
    private BigDecimal credit_global_agence = new BigDecimal(0), debit_global_agence = new BigDecimal(0), fond = new BigDecimal(0);
    private int nombre_compte_agence;


    public Agence(int numero_agence, String nomAgence) {
        this.nomAgence = nomAgence;

    }

    public boolean crediter(BigDecimal montant) throws ErrValeurNegative {
        if (montant.compareTo(new BigDecimal(0)) < 0) throw new ErrValeurNegative();
        fond = fond.add(montant);
        credit_global_agence = credit_global_agence.add(montant);
        Banque.crediter_banque(montant);
        return true;
    }

    public boolean debiter(BigDecimal montant) throws ErrValeurNegative {
        if (montant.compareTo(new BigDecimal(0)) < 0) throw new ErrValeurNegative();
        fond = fond.subtract(montant);
        debit_global_agence = debit_global_agence.add(montant);
        Banque.debiter_banque(montant);
        return true;
    }


    public void getInfo() {
        System.out.println("Le credit glob d agence :" + this.credit_global_agence);
        System.out.println("Le debit glob d agence :" + this.debit_global_agence);
    }

    public void ajout_compte_dans_agence() {
        nombre_compte_agence++;
    }

    public String getNomAgence() {
        return nomAgence;
    }

    public int getNumero_agence() {
        return numero_agence;
    }

    public BigDecimal getCredit_global_agence() {
        return credit_global_agence;
    }

    public BigDecimal getDebit_global_agence() {
        return debit_global_agence;
    }

    public int getNombre_compte_agence() {
        return nombre_compte_agence;
    }

    public BigDecimal getFond() {
        return fond;
    }
}
