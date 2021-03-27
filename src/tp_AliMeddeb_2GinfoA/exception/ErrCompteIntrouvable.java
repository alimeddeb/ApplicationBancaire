package tp_AliMeddeb_2GinfoA.exception;

public class ErrCompteIntrouvable extends Exception{
    public ErrCompteIntrouvable() {
        super("Compte non existant!");
    }

    public ErrCompteIntrouvable(String message) {
        super(message);
    }
}
