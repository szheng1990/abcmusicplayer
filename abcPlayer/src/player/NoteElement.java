package player;

/**
 * Note Elements is an interface that contains Notes, Chord, Nplet and Rest
 * @author kerengu
 */
public interface NoteElement {
    
    public enum noteType{
        NOTE, CHORD, NPLET, REST
    }
    
    /**
     * Helps distinguish which kind of NoteElement the object is. (Can also use .class? But might
     * encounter problems or ambiguities with compile-time type. )
     * @return
     */
    public noteType getType();
    
    /**
     * ToString Method for debugging. 
     * @return
     */
    public String toString();
    
    /**
     * @return A double representing the duration of the NoteElement
     */
    public double getDecimalDuration();
    
    /**
     * @return The NoteElement's duration
     */
    public String getDuration();
}
