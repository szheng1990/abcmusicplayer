package player;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 * One major method in Piece is "get usefulNotes. 
 * @author kerengu
 *
 */
public class PieceTest {
    SongBuilder sb = new SongBuilder();
    Piece piece = sb.buildSong("sample_abc/pieceAll.abc");
    ArrayList<PlayableNotes> notes = (ArrayList<PlayableNotes>) piece.getUsefulNotes();
    
    @Test
    public void getUsefulNotesTest(){
        Note noteCheck = new Note('E', "3/4", "", 0);
        PlayableNotes pN = new PlayableNotes(noteCheck, 0, 1);
        assertEquals(pN.getPitch(), notes.get(0).getPitch());
        assertEquals(pN.getStartTick(), notes.get(0).getStartTick());
    }
    
    @Test
    public void testBeatsPerMinutes(){
        int beatsPerMinute = piece.getBeatsPerMinutes();
        assertEquals(360, beatsPerMinute);
    }
}