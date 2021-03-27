package tp_AliMeddeb_2GinfoA.exception;

public class ErrConst extends ErrNaturel {
    public ErrConst() {
        super("erreur, entier ne peut pas etre negatif");
    }

    public ErrConst(String message) {
        super(message);
    }
}
