package tp_AliMeddeb_2GinfoA.exception;

public class ErrSomme extends ErrNaturel{

    public ErrSomme() {
        super("Somme dépasse valeur maximum autorisée");
    }
    public ErrSomme(String message) {
        super(message);
    }
}
