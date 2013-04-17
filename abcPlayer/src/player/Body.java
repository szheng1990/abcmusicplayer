package player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Body is an attribute of Piece and A body consists
 * of a list of NoteElments 
 *
 */
public class Body {
    
    private Map<String, List<NoteElement>> notes;
    
    public Body(){
        this.notes = new HashMap<String, List<NoteElement>>();
    }
    
    //Getter for Notes
    public HashMap<String, List<NoteElement>> getNotes(){
        return new HashMap<String, List<NoteElement>>(notes);
    }
    
    public void addNote(String voice, NoteElement note){
        if(note == null){
            throw new IllegalArgumentException("Cannot add null as new note.");
        }
        List<NoteElement> l = this.notes.get(voice);
        l.add(note);
        this.notes.put(voice, l);
    }
    
    public void setNotes(Map<String, List<NoteElement>> notes){
        this.notes = new HashMap<String, List<NoteElement>>(notes);
    }
    
    
}
