package tp_AliMeddeb_2GinfoA.exception;

public class ErrSoldeEnRouge extends ErrNegatif {
    public ErrSoldeEnRouge() {
        super("Compte en Rouge");
    }

    public ErrSoldeEnRouge(String message) {
        super(message);
    }
}
