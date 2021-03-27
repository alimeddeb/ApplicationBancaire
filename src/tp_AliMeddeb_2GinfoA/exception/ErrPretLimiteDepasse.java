package tp_AliMeddeb_2GinfoA.exception;

public class ErrPretLimiteDepasse extends Exception {
    public ErrPretLimiteDepasse() {
        super("Client non existant!");
    }

    public ErrPretLimiteDepasse(String message) {
        super(message);
    }

}
