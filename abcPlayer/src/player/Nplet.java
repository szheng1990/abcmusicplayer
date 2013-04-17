package player;

import java.util.ArrayList;
import java.util.List;

public class Nplet implements NoteElement {

    // updated by zhengshu: Nplet can contain both Note and Chord classes
    public npletType type;
    public List<NoteElement> elements;
    public int numNotes;
    
    /**
     *    Duplet: 2 notes in the time of 3 notes
     *    Triplet: 3 notes in the time of 2 notes
     *    Quadruplet: 4 notes in the time of 3 notes
     * @author kerengu
     *
     */
    public enum npletType{
        DUPLET, TRIPLET, QUADRUPLET
    }
    
    
    /**
     * Takes an n-plet type and constructs an n-plet with 
     * an array of length 2, 3, or 4. 
     * @param type
     */
    public Nplet(Nplet.npletType type){
        this.type = type;
        if(type == npletType.DUPLET){
            this.elements = new ArrayList<NoteElement>(2);
            this.numNotes = 2;
        }else if(type == npletType.TRIPLET){
        	this.elements = new ArrayList<NoteElement>(3);
        	this.numNotes = 3;
        }else if(type == npletType.QUADRUPLET){
            this.elements = new ArrayList<NoteElement>(4);
            this.numNotes = 4;
        }
    }
    
    /**
     * Constructs an n-plet based on their type. sets the list of notes and 
     * total length. 
     * If the size of the notes-list does not match the n-plet type, throw exception. 
     * @param type
     * @param notes
     * @param length
     */
    public Nplet(Nplet.npletType type, List<NoteElement> notes){
        this(type);
        if (notes.size() != this.numNotes){
            throw new IllegalArgumentException("Number of Notes does not match the type of n-plet");
        }else{
            this.elements = new ArrayList<NoteElement>(notes);

        }
    }

    /**
     * Returns the type of note (class)
     */
    public noteType getType(){
        return noteType.NPLET;
    }
    
    /**
     * Getter for the Type of the n-plet but no setter.
     * Once creating an nplet, nplet is immutable. 
     * @return
     */
    public npletType getNpletType() {
        return type;
    }

    /**
     * Getter for Notes, but no setters because notes are immutable after construction. (why would it be
     * mutable?)
     * @return
     */
    public List<NoteElement> getNotes() {
        return elements;
    }

    public double getDecimalDuration() {
        Note note = (Note) elements.get(0);
        return note.getDecimalDuration() * durationFactor();
    }
    private double durationFactor(){
        if (this.type == npletType.DUPLET){
            return ((double)3)/2;
        }else if (this.type == npletType.TRIPLET){
            return ((double)2)/3;
        }else if (this.type == npletType.QUADRUPLET){
            return ((double)3)/4;
        }else{
            throw new RuntimeException();
        }
    }
    
    
    
    public String getDuration() {
        int denomThis;
        if (this.type == npletType.DUPLET){
            denomThis = 2;
        }else if (this.type == npletType.TRIPLET){
            denomThis = 3;
        }else if (this.type == npletType.QUADRUPLET){
            denomThis = 4;
        }else{
            throw new RuntimeException();
        } 
        
        Note n = (Note) elements.get(0);
        String dur = n.getDuration();
        if(dur.indexOf("/") > 0) {
            String num = dur.substring(0, dur.indexOf("/")+1);
            int denom = Integer.parseInt(dur.substring(dur.indexOf("/")+1));
            int newDenom = denomThis * denom;
            return num  + newDenom;
        }
        else {
            return dur + "/" + denomThis;
        }
    }

    @Override
    public String toString() {
        return "Nplet [type=" + type + ", elements=" + elements + ", numNotes="
                + numNotes + "]";
    }
    
    
    
}
