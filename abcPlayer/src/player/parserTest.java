package player;

import org.junit.Test;

/**
 * Testing Strategy for Parser:
 * 1) ParserTest: Create a .abc file with notes, accidentals, repeats, etc etc. chords and test 
 * the output piece by inspection
 * 2) ParseHeader
 * 3) ParseSingleVoice
 * 4) ParseSingleNote
 * 5) ParseRest
 * 6) UpdateAccidentals
 * 7) ParseChord
 * 8) Music with different K
 * @author kerengu
 *
 */
public class parserTest {

	/**
	 * 1) Test By Inspection
	 */
	@Test
	public void testAll() {

	    System.out.println("Test 1:  By inspecting the following. ");

		String f = "sample_abc/pieceAll.abc";
		SongBuilder sb = new SongBuilder();
		String content = sb.getFile(f);

		Lexer lexer = new Lexer(content);
		Parser parser = new Parser(lexer);
		Piece piece = parser.parse();

		for(PlayableNotes pn: piece.getUsefulNotes()){
		    System.out.println(pn);
		}
	}
	
	/**
     * 2) Test the Header. Make sure that when there are all values, all 
     * header-lines get saved. 
     */
    @Test
    public void testParseHeader() {

        System.out.println("Test 2: (Test by inspection) Check for currect construction of Header ");

        String f = "sample_abc/fur_elise.abc";
        SongBuilder sb = new SongBuilder();
        String content = sb.getFile(f);

        Lexer lexer = new Lexer(content);
        Parser parser = new Parser(lexer);
        Header header = parser.parseHeader(lexer.getTokens());
        System.out.println(header);
    }
	
    /**
     * 3) Test the parse Single voice method. 
     */
    @Test
    public void testParseSingleVoice(){
        System.out.println("Test 3: Parse Single Voice (test by inspection)");
        String f = "sample_abc/piece1.abc";
        SongBuilder sb = new SongBuilder();
        String content = sb.getFile(f);

        Lexer lexer = new Lexer(content);
        Parser parser = new Parser(lexer);
        Piece piece = parser.parse();
        
        for(PlayableNotes pn: piece.getUsefulNotes()){
            System.out.println(pn);
        }
        
    }
    
    /**
     * 4) Parse single note. The pieceAll.abc file has, what we think is, a comprehensive
     * collection of possible notes that we may encounter as valid inputs. Hence if testALL()
     * passes, we know that parseSingleNote also works. 
     */
    
    /**
     * 5), 6), 7) Rest, updateAccidental, and Chord are included in pieceAll.abc.
     */
    
	/**
     * 8) Test scale for different K-value. Make sure that a, c, D, F, G have sharps.
     * Also test that parser can handle "C" as an input for meter M 
     */
    @Test
    public void testDifferentKValueMCValue() {

        System.out.println("Test 8: Test by inspection");
        String f = "sample_abc/scaleK_A.abc";
        SongBuilder sb = new SongBuilder();
        String content = sb.getFile(f);

        Lexer lexer = new Lexer(content);
        Parser parser = new Parser(lexer);
        Piece piece = parser.parse();

        for(PlayableNotes pn: piece.getUsefulNotes()){
            System.out.println(pn);
        }
    }


		 }



