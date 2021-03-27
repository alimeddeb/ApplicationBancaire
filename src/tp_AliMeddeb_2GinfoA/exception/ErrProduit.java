package tp_AliMeddeb_2GinfoA.exception;

public class ErrProduit extends ErrNaturel {
    public ErrProduit() {
        super("Produit dépasse valeur maximum autorisée");
    }
    public ErrProduit(String message) {
        super(message);
    }
}
