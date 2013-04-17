package player;

/**
 * Note is an Note Element. It contains a bunch of stuff
 * and it has getters and setters foo most of the stuff but
 * not all. Because it wouldn't makes sense to change
 * the note after you create it. 
 * @author mssand (in part), edited by kgu
 */
public class Note implements NoteElement{
    
    private char pitch;
    private String duration;
    private AccidentalType accidental;
    private int semitoneUp;
    private int octave;
    
    public static enum AccidentalType {        
        SHARP,
        DOUBLE_SHARP,
        FLAT,          
        DOUBLE_FLAT,
        NONE,
    }
    public Note(char pitch, String duration, String accidental, int octave) {
        this.pitch = pitch;
        if(duration.contains("/")) {
            if(duration.indexOf("/") == 0) {
                if(duration.length() == 1) {
                    this.duration = "1/2";
                }
                else {
                    this.duration = "1" + duration;
                }
            }
            else {
                int index = duration.indexOf("/");
                if(index == (duration.length() - 1)) {
                    this.duration = duration + "2";
                }
                else {
                    this.duration = duration;
                }
            }
        }
        else {
            this.duration = duration;
        }
        
        if(accidental.compareTo("^") == 0) {
            this.accidental = AccidentalType.SHARP;
            this.semitoneUp = 1;
        }
        else if(accidental.compareTo("^^") == 0) {
            this.accidental = AccidentalType.DOUBLE_SHARP;
            this.semitoneUp = 2;
        }
        else if(accidental.compareTo("_") == 0) {
            this.accidental = AccidentalType.FLAT;
            this.semitoneUp = -1;
        }
        else if(accidental.compareTo("__") == 0) {
            this.accidental = AccidentalType.DOUBLE_FLAT;
            this.semitoneUp = -2;
        }
        else if(accidental.compareTo("") == 0) {
            this.accidental = AccidentalType.NONE;
            this.semitoneUp = 0;
        }
        else if(accidental.compareTo("=") == 0) {
            this.accidental = AccidentalType.NONE;
        }
        else {
            throw new RuntimeException("Unknown accidental found while creating note: " + accidental);
        }
        
        this.octave = octave;
    }
    
    /**
     * Getters and Setters for SemetoneUp
     * @return
     */
    public int getSemitoneUp() {
        return semitoneUp;
    }


    public void setSemitoneUp(int semitoneUp) {
        this.semitoneUp = semitoneUp;
    }


    /**
     * @return the pitch
     */
    public char getPitch() {
        return pitch;
    }



    /**
     * @param pitch the pitch to set
     */
    public void setPitch(char pitch) {
        this.pitch = pitch;
    }



    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    public double getDecimalDuration(){
        int index = this.duration.indexOf("/");
        // In the case that duration is not a fraction
        if(index < 0){
            return Double.parseDouble(this.duration);
        }
        float num = Float.parseFloat(this.duration.substring(0, index));
        float denom = Float.parseFloat(this.duration.substring(index+1));
        return num / denom;
    }


    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }



    /**
     * @return the accidental
     */
    public AccidentalType getAccidental() {
        return accidental;
    }



    /**
     * @param accidental the accidental to set
     */
    public void setAccidental(AccidentalType accidental) {
        this.accidental = accidental;
    }



    /**
     * @return the octave
     */
    public int getOctave() {
        return octave;
    }



    /**
     * @param octave the octave to set
     */
    public void setOctave(int octave) {
        this.octave = octave;
    }

    @Override
    public noteType getType() {
        return noteType.NOTE;
    }
  
    @Override
    public String toString() {
        return "Note [pitch=" + pitch + ", duration=" + duration
                + ", accidental=" + accidental + ", semitoneUp=" + semitoneUp
                + ", octave=" + octave + "]";
    }
}
