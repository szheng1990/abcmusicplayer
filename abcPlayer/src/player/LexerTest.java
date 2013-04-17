package player;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Lexer Test.
 * Testing strategies:
 * 1) Use the file with chord, notes, accidentals, and everything else. 
 * Pass into Lexer and test by inspection
 * 
 * 2) Give invalid inputs and test for exception:
 * 2.1) Invalid Character
 * 2.2) Bad Header ordering
 * 2.3) Invalid (1 (5 etc. for tuplet
 * 2.4) Invalid nth repeat that's not 1 or 2
 * 2.5) Invalid Header Character
 * 
 * @author kerengu
 *
 */
public class LexerTest {
    
    /**
     * Tests for Lexer's main function: getToken. The function analyzes the
     * string field of Lexer and returns a list of Tokens. 
     *
     */
    @Test
    public void getTokenTest() {
        
        System.out.println("Test 1...............................");
        
        String f = "sample_abc/piece1.abc";
        SongBuilder sb = new SongBuilder();
        String content = sb.getFile(f);
        
        Lexer lexer = new Lexer(content);
        ArrayList<Token> tokens = lexer.getTokens();
        //System.out.println(tokens);
        for(Token t: tokens){
            System.out.println(t);
        }
        
    }
    
    @Test
    public void getTokenTest2() {
        
        System.out.println("Test 2...............................");
        
        String f = "sample_abc/piece2.abc";
        SongBuilder sb = new SongBuilder();
        String content = sb.getFile(f);
        
        Lexer lexer = new Lexer(content);
        ArrayList<Token> tokens = lexer.getTokens();
        //System.out.println(tokens);
        for(Token t: tokens){
            System.out.println(t);
        }
        
    }
    
    @Test
    public void getTokenTest3() {
        
        System.out.println("Test 3...............................");
        
        String f = "sample_abc/pieceAll.abc";
        SongBuilder sb = new SongBuilder();
        String content = sb.getFile(f);
        
        Lexer lexer = new Lexer(content);
        ArrayList<Token> tokens = lexer.getTokens();
        //System.out.println(tokens);
        for(Token t: tokens){
            System.out.println(t);
        }
        
    }
    
}
