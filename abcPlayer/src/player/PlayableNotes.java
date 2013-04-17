package player;

import sound.Pitch;

/**
 * Playable Notes can return a pitch. 
 * @author kerengu
 *
 */
public class PlayableNotes {

    private Note abstractNote;
    private int startTick;
    private int durationTick;
    private Pitch pitch;
    
    public PlayableNotes(Note abstractNote, int startTick, int durationTick){
        
        this.abstractNote = abstractNote;
        this.startTick = startTick;
        this.durationTick = durationTick;
        
        //Find the right Pitch using the abstract Note
        String notePitch = abstractNote.getPitch() + "";
        if(!notePitch.equals(notePitch.toUpperCase())){
            //If notePitch is not a Capital letter
            this.pitch = new Pitch(notePitch.toUpperCase().charAt(0)).transpose(Pitch.OCTAVE);
        }else{
            this.pitch = new Pitch(notePitch.charAt(0));
        }
        
        //Adjust pitch with octave
        this.pitch = this.pitch.octaveTranspose(abstractNote.getOctave());
        
        //Adjust pitch with accidental
        this.pitch = this.pitch.accidentalTranspose(abstractNote.getSemitoneUp());
    }

    /**
     * Getters and Setters for all attributes. Especially for getPitch. 
     * @return
     */
    public Note getAbstractNote() {
        return abstractNote;
    }

    public void setAbstractNote(Note abstractNote) {
        this.abstractNote = abstractNote;
    }

    public int getStartTick() {
        return startTick;
    }

    public void setStartTick(int startTick) {
        this.startTick = startTick;
    }

    public int getDurationTick() {
        return durationTick;
    }

    public void setDurationTick(int durationTick) {
        this.durationTick = durationTick;
    }

    public Pitch getPitch() {
        return pitch;
    }

    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }
    
    @Override
    public String toString() {
        return "PlayableNotes [abstractNote=" + abstractNote + ", startTick="
                + startTick + ", durationTick=" + durationTick + ", pitch="
                + pitch + "]";
    }


    
}
