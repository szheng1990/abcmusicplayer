package player;

import java.util.ArrayList;
import java.util.List;

public class Chord implements NoteElement{
	
	// zhengshu: define constructor for Chord
	public final List<Note> notes;
	
	/**
	 * Constructor for Chord: 
	 * Chord cannot have chords, or nplets, or rests.
	 * It can only have Notes. 
	 * @param notes
	 */
	public Chord(List<NoteElement> notes){
	    this.notes = new ArrayList<Note>();
	    
	    for(NoteElement n: notes){
	        if (n.getType() != NoteElement.noteType.NOTE){
	            throw new IllegalArgumentException("Chord can only contain notes.");
	        }
	        this.notes.add((Note) n);   
	    }
	}

    @Override
    public noteType getType() {
        return noteType.CHORD;
    }
    
    public double getDecimalDuration() {
        Note n = this.notes.get(0);
        return n.getDecimalDuration();
    }
    
    public String getDuration() {
        Note n = this.notes.get(0);
        return n.getDuration();
    }

    @Override
    public String toString() {
        String toReturn = "Chord [";
        for(Note n: notes){
            toReturn += n.toString() + ". ";
        }
        return toReturn + "]";
    }

}
