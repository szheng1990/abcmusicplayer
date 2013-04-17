package player;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import sound.SequencePlayer;

/**
 * Main entry point of your application.
 */
public class Main {

	/**
	 * Plays the input file using Java MIDI API and displays
	 * header information to the standard output stream.
	 * 
	 * <p>Your code <b>should not</b> exit the application abnormally using
	 * System.exit()</p>
	 * 
	 * @param file the name of input abc file
	 */
	public static void play(String file) {
		
	    //Songbuilder builds a song piece. 
	    SongBuilder sb = new SongBuilder();
	    Piece piece = sb.buildSong(file);
	    
	    
	    List<PlayableNotes> noteList = piece.getUsefulNotes();
	    //Figure out the beats of the piece. 
	    int beatsPerMinute = piece.getBeatsPerMinutes();
	    int ticksPerQuarterNote = piece.getTicksPerQuarterNote();
	    
//	    //TODO: Delete when done testing
//        System.out.println("List of Playable Notes....");
//        System.out.println("BeatsPerMinute" + beatsPerMinute);
//        System.out.println("ticksPerQuarterNote" + ticksPerQuarterNote);
//        for(PlayableNotes pn: noteList){
//            System.out.println("\t" + pn);
//        }
        
	    SequencePlayer player;
	    
	    try {
            player = new SequencePlayer(beatsPerMinute, ticksPerQuarterNote);
            for(PlayableNotes n: noteList){
                player.addNote(n.getPitch().toMidiNote(), n.getStartTick(), n.getDurationTick());
            }
            System.out.println("Currently Playing " + file);
            player.play();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
	    
	    
	}
	
	public static void main(String[] args){
	    Main.play("sample_abc/fur_elise2.abc"); 
	    Main.play("sample_abc/fur_elise.abc"); 
      Main.play("sample_abc/invention.abc");
	    Main.play("sample_abc/little_night_music.abc");
	    Main.play("sample_abc/paddy.abc");
	    Main.play("sample_abc/piece1.abc");
	    Main.play("sample_abc/piece2.abc");
	    Main.play("sample_abc/pieceRepeat.abc");
	    Main.play("sample_abc/prelude.abc");
	    Main.play("sample_abc/pieceAll.abc"); 
//	    
	}
}
