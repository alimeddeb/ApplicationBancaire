package tp_AliMeddeb_2GinfoA.exception;

public class ErrValeurNegative extends Exception {
    public ErrValeurNegative() {
        super("Valeur negative");
    }

    public ErrValeurNegative(String message) {
        super(message);
    }
}
