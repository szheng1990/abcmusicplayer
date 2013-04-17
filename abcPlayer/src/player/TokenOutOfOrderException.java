package player;

public class TokenOutOfOrderException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TokenOutOfOrderException(String err) {
        super(err);
    }
}
