package player;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * SongBuilderTest
 * 1) Test for getFile method
 * @author kerengu
 *
 */
public class SongBuilderTest {
    
    @Test
    public void getFileTest(){
        String f = "sample_abc/piece1.abc";
        SongBuilder sb = new SongBuilder();
        String print = sb.getFile(f);
        System.out.println(print);
        String output = "X: 1\nT:Piece No.1\nM:4/4\nL:1/4\nQ: 140\nK:C\nC C C3/4 D/4 E | E3/4 D/4 E3/4 F/4 G2 | (3ccc (3GGG (3EEE (3CCC | G3/4 F/4 E3/4 D/4 C2 |]";
        assertEquals(print, output);
    }
    
    
}
