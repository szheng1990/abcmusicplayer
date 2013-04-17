/**
 * 
 */
package player;

/**
 * @author Michael Scott
 * For use in Parser.updateAccidentals()
 */
public class UnknownAccidentalException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 2L;

    public UnknownAccidentalException(String err) {
        super(err);
    }
}
