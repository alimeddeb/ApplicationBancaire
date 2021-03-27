package tp_AliMeddeb_2GinfoA;

import tp_AliMeddeb_2GinfoA.exception.ErrNegatif;
import tp_AliMeddeb_2GinfoA.exception.ErrValeurNegative;

import java.math.BigDecimal;

public class Banque {
    private static String BIC = "154875";
    private static String nom_banque = "ENSIT Online Bank";
    private static String abreviation_nom = "EOB";
    private static int nombre_compte_banque = 0;
    private static BigDecimal credit_global_banque = new BigDecimal(0), debit_global_banque = new BigDecimal(0);

    public static String getBIC() {
        return BIC;
    }

    public static String getNom_banque() {
        return nom_banque;
    }

    public static String getAbreviation_nom() {
        return abreviation_nom;
    }

    public static int getNombre_compte_banque() {
        return nombre_compte_banque;
    }

    public static BigDecimal getCredit_global_banque() {
        return credit_global_banque;
    }

    public static BigDecimal getDebit_global_banque() {
        return debit_global_banque;
    }

    public static void debiter_banque(BigDecimal montant) throws ErrValeurNegative {
        if (montant.compareTo(new BigDecimal(0)) < 0) throw new ErrValeurNegative();
        debit_global_banque = debit_global_banque.add(montant);
    }

    public static void crediter_banque(BigDecimal montant) throws ErrValeurNegative {
        if (montant.compareTo(new BigDecimal(0)) < 0) throw new ErrValeurNegative();
        credit_global_banque = credit_global_banque.add(montant);
    }

    public static void ajout_compte_dans_banque() {
        nombre_compte_banque++;
    }
}
