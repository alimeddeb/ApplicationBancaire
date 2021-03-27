package tp_AliMeddeb_2GinfoA.exception;

public class ErrClientIntrouvable extends Exception{
    public ErrClientIntrouvable() {
        super("Client non existant!");
    }

    public ErrClientIntrouvable(String message) {
        super(message);
    }
}
