package tp_AliMeddeb_2GinfoA.exception;

public class ErrLoginIncorrect extends Exception {
    public ErrLoginIncorrect() {
        super("Identifiant ou mot de passe Incorrect!");
    }

    public ErrLoginIncorrect(String message) {
        super(message);
    }
}
