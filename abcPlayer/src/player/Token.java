package player;

import java.util.regex.Pattern;

/**
 * A token is a lexical item that the parser uses.
 */
public class Token {
    public final Type type;
    public final String data;
    // zhengshu: add Pattern pattern
    public final Pattern pattern;
    
    /**
     * 
     */
    
    public static enum Type {
                        // example of what data can be
                        
        HEADER_LINE,    // anything that constitutes a line in a header
                        // ex. "Q: 500", "K: Bm"
        
        NOTE,           // all pieces that came with note
                        // ex. �d'2�, �_e,7�
        
        REST,           // rests
                        // ex. z2, z/4
        
        VOICE,          // the text from a voice line inside the music, but not in the header
                        // ex. "cat", "violin 1"
        
        CHORD,          // the string inside the []
                        // ex. [^c'e] -> �^c'e�
        
        TUPLET,         // everything in tuple 
                        // ex. �(3A,CE�, �(3_d'c'b�
 
        REPEAT,         // the specific repeat
                        // ex. �:|�, �[1�, �[2�
        
        SECTION_MARKER, // the specific section marker 
                        // ex. �||� and �|]� and �[|�
        
        BARLINE         // zhengshu: normal barline �|�
    }
    
 // zhengshu: define token type with regex; TO DO: define end of line
    public Token(Type type, String data) {
    	this.type = type;
    	
    	// define regex
    	String noteRegex = "((=|\\^\\^|\\^|_|__)*[CDEFGABcdefgab](,*|'*)(\\d*/\\d*|\\d*))";
    	String restRegex = "(z(\\d*/\\d*|\\d*))";
    	String chordRegex = "(\\[" + noteRegex + "+" + "\\])";
    	String tupletRegex = "\\([234]" + "(" + noteRegex + "|" + chordRegex + ")+";
    	
    	switch (type) {
    	case HEADER_LINE:
    		this.pattern = Pattern.compile("(X|T|M|L|Q|K):[a-z]*");
    		break;
    	case NOTE:
    		//regular expression covering both integer and float-point numbers
    		this.pattern = Pattern.compile(noteRegex);
    		break;
    	case REST:
    		this.pattern = Pattern.compile(restRegex);
    		break;
    	case VOICE:
    		this.pattern = Pattern.compile("V:.");
    		break;
    	case CHORD:
    		this.pattern = Pattern.compile(chordRegex);
    		break;
    	case TUPLET:
    		this.pattern = Pattern.compile(tupletRegex);
    		break;
    	case REPEAT:
    		this.pattern = Pattern.compile("\\|\\:|\\:\\||\\[1|\\[2");
    		break;
    	case SECTION_MARKER:
    		this.pattern = Pattern.compile("\\|\\]|\\|\\||\\[\\|");
    		break;
    	case BARLINE:
    		this.pattern = Pattern.compile("\\|");
    	    break;
    	default:
    		throw new RuntimeException("The input type is invalid");
    	}
        this.data = data;
    }
    
    public Type getType() {
        return type;
    }
    
    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Token [type=" + type + ", value=" + data + "]";
    }
    
    
}