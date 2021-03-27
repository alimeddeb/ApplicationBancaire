package tp_AliMeddeb_2GinfoA.exception;

public class ErrNegatif extends Exception {
    public ErrNegatif() {
        super("Solde Negatif");
    }

    public ErrNegatif(String message) {
        super(message);
    }
}
