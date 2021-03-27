package tp_AliMeddeb_2GinfoA.exception;

public class ErrTypeCompte extends Exception {
    public ErrTypeCompte() {
        super("Type de  compte non valide!");
    }

    public ErrTypeCompte(String message) {
        super(message);
    }
}
