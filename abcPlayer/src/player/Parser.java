package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    /**
     * The parser gets a bunch of tokens from the lexer and ouput a list of
     * notes which will then be used to create a sound added to the player.
     */
    
    public ArrayList<Token> tokens;
    public Map<String, String> accidentals; // maps note names to accidentals, mutable
    public Body body;
    public Header header;
    
    
    /**
     * Create Parser object 
     */
    public Parser(Lexer lexer) {
        tokens = lexer.getTokens();
    }
    
    /**
     * @return Piece generated from the list of tokens
     */
    public Piece parse(){
        ArrayList<Token> listToUse = new ArrayList<Token>(tokens);
        Piece finalPiece = new Piece();
        
        // Header
        Header header = parseHeader(listToUse);
        this.header = header;
        finalPiece.setHeader(header);
        
        int numVoices = header.getNum_voices();
        
        String key = header.getKey_K();
        accidentals = KeyToAccidentals.getAccidentals(key);// to be mutated during parseSingleNote
        
        // Body
        Map<String, List<NoteElement>> voiceNotesMapping = new HashMap<String, List<NoteElement>>();
        List<NoteElement> notes;
        if (numVoices <= 1){
            //kgu: Add default voice to mapping. 
            notes = parseSingleVoice(header, listToUse);
            voiceNotesMapping.put("Default", notes);
        }
        else if (numVoices > 1){
            // use header to extract voice information 
            voiceNotesMapping.putAll(parseVoices(header, listToUse));
        }
        else throw new RuntimeException();
        
        body = new Body();
        body.setNotes(voiceNotesMapping);
        
        // set finalPiece
        finalPiece.setBody(body);
        
        return finalPiece;
    }
    
    /**
     * mssand
     * @param tokens Tokens to parse. Tokens is mutated to remove the used tokens. 
     * @return Header object for the piece
     */
    public Header parseHeader(List<Token> tokens){
        //TO DO: (1) run through tokens and create Header object
        //       (2) also need to remove any tokens it uses
        
        int trackNumber_X = 0;
        String name_T = "";
        String composer_C = "Unknown";
        String key_K = "";
        String length_L = "1/8";
        String meter_M = "4/4";
        int tempo_Q = 100;
        List<String> voices_V = new ArrayList<String>();
        boolean X_set = false;
        boolean K_set = false;
        boolean T_set = false;
        
        int counter = 0;
        
        for(Token t: tokens) {
            counter++;
            String data = t.getData();
            String id = data.substring(0, 2);
            if(id.compareTo("X:") == 0) {

                trackNumber_X = Integer.parseInt(data.substring(2).trim());
                X_set = true;
            }
            else if(id.compareTo("K:") == 0) {
                key_K = data.substring(2).trim();
                K_set = true;
                // last field should be K
                break;
            }
            else if(id.compareTo("T:") == 0) {
                name_T = data.substring(2).trim();
                T_set = true;
            }
            else if(id.compareTo("C:") == 0) {
                composer_C = data.substring(2).trim();
            }
            else if(id.compareTo("L:") == 0) {
                length_L = data.substring(2).trim();
            }
            else if(id.compareTo("M:") == 0) {
                meter_M = data.substring(2).trim();
                if (meter_M.compareTo("C") == 0) {
                    meter_M = "4/4";
                }
                if (meter_M.compareTo("C|") == 0) {
                    meter_M = "2/2";
                }
            }
            else if(id.compareTo("Q:") == 0) {
                tempo_Q = Integer.parseInt(data.substring(2).trim());
            }
            else if(id.compareTo("V:") == 0) {
                voices_V.add(data.substring(2).trim());
            }
            else {
                throw new RuntimeException("Unknown header line encountered: " + id);
            }
        }
        
        // if no voices listed, add a default voice
        if(voices_V.size() == 0) {
            voices_V.add("Default voice 1");
        }
        
        // X, T, and K needed at a bare minimum
        if(!(X_set && T_set && K_set)) {
            throw new RuntimeException("Missing required header information");
        }
        
        Header header = new Header(trackNumber_X, name_T, key_K);
        
        header.setComposer_C(composer_C);
        header.setLength_L(length_L);
        header.setMeter_M(meter_M);
        header.setTempo_Q(tempo_Q);
        header.setVoices_V(voices_V);
        
        // added by zhengshu
        header.setNum_voices(voices_V.size());
        
        // changes the tokens list so that all the header tokens are removed 
        tokens = tokens.subList(counter, tokens.size());
        
        return header;
    }
    
    /*
     * Parse single voice
     */
    /**
     * zhengshu
     * @param header
     * @param a list of tokens of a single voice (assume header is truncated - only body is passed in)
     * @return a list of re-arranged <NoteElement> WITHOUT repeats
     */
    public List<NoteElement> parseSingleVoice(Header header, List<Token> tokens){
        // step 1. iterate through tokens and re-arrange based on repeat
        
        ArrayList<Token> reArranged = new ArrayList<Token>();
        int latestRepeat = 0; // default repeat to the first token
        int j;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getType().equals(Token.Type.HEADER_LINE)){
                latestRepeat++;
            }
            else if (tokens.get(i).getType().equals(Token.Type.NOTE)
                    || tokens.get(i).getType().equals(Token.Type.REST)
                    || tokens.get(i).getType().equals(Token.Type.CHORD)
                    || tokens.get(i).getType().equals(Token.Type.TUPLET)
                    || tokens.get(i).getType().equals(Token.Type.BARLINE)) {
                reArranged.add(tokens.get(i));
            }
            else if (tokens.get(i).getData().equals("|:")
                    || tokens.get(i).getData().equals("||")
                    || tokens.get(i).getData().equals("|]")
                    || tokens.get(i).getData().equals("[|")) { // update
                                                                // latestRepeat
                // there will be no such special markers in the rearranged list
                // all section markers are replaced by the normal barline "|"
                reArranged.add(new Token(Token.Type.BARLINE, "|")); 
                int y = i + 1;

                latestRepeat = y;

            }
            else if (tokens.get(i).getData().equals(":|")) { // normal repeat
                                                        // identified
                
                //replace :| with normal barline
                reArranged.add(new Token(Token.Type.BARLINE, "|")); 
                
                List<Token> tokensBeforeNormalRepeat = tokens.subList(latestRepeat, i);
                reArranged.addAll(tokensBeforeNormalRepeat);
            }
            else if (tokens.get(i).getData().equals("[1")) { // nth repeat
                boolean repeatLabel = false; // every [1 should be match with a
                                                // :| later
                for (j = i + 1; j < tokens.size(); j++) {
                    if (tokens.get(j).getType().equals(Token.Type.NOTE)
                            || tokens.get(j).getType().equals(Token.Type.REST)
                            || tokens.get(j).getType().equals(Token.Type.CHORD)
                            || tokens.get(j).getType()
                                    .equals(Token.Type.TUPLET)
                            || tokens.get(j).getType()
                                    .equals(Token.Type.BARLINE)) {
                        reArranged.add(tokens.get(j));
                    }
                    if (tokens.get(j).getData().equals(":|")) {
                        // replace :| with normal barline
                        reArranged.add(new Token(Token.Type.BARLINE, "|")); 
                        
                        repeatLabel = true;
                        // ideally, j+1 should be now pointed at "[2"
                        if (!tokens.get(j + 1).getData().equals("[2")) {
                            throw new RuntimeException(
                                    "illegal: first repeat without a second repeat!");
                        }
                        break;
                    }
                }
                if (!repeatLabel) {
                    throw new RuntimeException(
                            "illegal nth repeat specifications");
                }

                // keep re-arranging the token sequence
                List<Token> tokensBeforeFirstRepeat = tokens.subList(latestRepeat, i);
                //ArrayList<Token> temp2 = new ArrayList<Token>(
                //      tokensBeforeFirstRepeat);
                reArranged.addAll(tokensBeforeFirstRepeat);
                i = j + 1; // i is now pointing at "[2"

            }
            else continue;

        }


                
        // step 2. now that the list of tokens contains no repeats, go through
        // it iteratively and construct the final list List<NoteElement>
        List<NoteElement> output = new ArrayList<NoteElement>();
        for (Token token : reArranged){
            if (token.getType().equals(Token.Type.NOTE)){ // simple note
                Note tempNote = parseSingleNote(token,header);
                output.add(tempNote);
            }
            else if (token.getType().equals(Token.Type.CHORD)){ // simple note
                Chord tempChord = parseChord(token);
                output.add(tempChord);
            }
            else if (token.getType().equals(Token.Type.TUPLET)){ // simple note
                Nplet tempNplet = splitTuplet(token);
                output.add(tempNplet);
            }
            else if (token.getType().equals(Token.Type.REST)){ // simple note
                Rest tempRest = parseRest(token, header);
                output.add(tempRest);
            }
            else if (token.getType().equals(Token.Type.BARLINE)){ // simple note
                updateAccidentals(true, "x", "x", null);
                continue;
            }
            else throw new RuntimeException("token at this stage must be one of Note/Chord/Nplet/Rest/Barline");
           
        }
        
        
        return output;
        
    }
    
    /**
     * mssand
     * Takes a piece with multiple voices and produces an output. Does this by
     * pulling out the tokens for each voice, then processing each voice 
     * separately through calls to parseSingleVoice()
     * @param header Header that was generated as part of the Piece
     * @param tokens Tokens representing the Body of the Piece
     * @return List of NoteElements
     * @throws TokenOutOfOrderException If a voice token is not the first token in the list
     */
    private Map<String, List<NoteElement>> parseVoices(Header header, List<Token> tokens) throws TokenOutOfOrderException{
        // step 0: find the start index of the body
        int globalStart = 0; // start index for body, default to zero
        for (int i = 0; i < tokens.size(); i++){
            if (tokens.get(i).getType().equals(Token.Type.VOICE)){ // when the first VOICE token in the body is found
                globalStart = i;
                break;
            }
        }
        List<Token> pureBody = tokens.subList(globalStart, tokens.size());
        
        // aggregate all music for each voice, and use parseSingleVoice
        
        // sort input tokens based on voice
        HashMap<String, ArrayList<Token>> voiceMap = new HashMap<String, ArrayList<Token>>();
        for (String v : header.getVoices_V()){
            voiceMap.put(v, new ArrayList<Token>());
        }
        if(pureBody.get(0).getType() != Token.Type.VOICE) {
            throw new TokenOutOfOrderException("Was expecting a voice token, but did not get one");
        }

        String voice = "";
        for (Token t : pureBody) {
            if(t.getType() == Token.Type.VOICE) {
                voice = t.getData();
            }
            else {
                // not a voice token, so should just include in list for voice
                voiceMap.get(voice.substring(2).trim()).add(t);
            }
        }
        
        //Using <String, List<Token>> map to call parseSingleVoice on each Key-String. 
        HashMap<String, List<NoteElement>> toReturn = new HashMap<String, List<NoteElement>>();
        for(String voiceAddingNotes : voiceMap.keySet()) {
            List<Token> listOTokens = voiceMap.get(voiceAddingNotes);
            List<NoteElement> listONotes = parseSingleVoice(header, listOTokens);
            toReturn.put(voiceAddingNotes, listONotes);
        }
        
        return toReturn;
    }

    /**
     * mssand
     * @param token Token to parse
     * @return A Note
     */
    public Note parseSingleNote(Token token, Header header){

        Note toReturn;
        String data = token.getData();
        
        // if length of data is 1, then should just have note name
        if (data.length() == 1) {
        	String tempAccidental;
        	String tempSingleNote = String.valueOf(data.charAt(0)) + "0";
            if(accidentals.containsKey(tempSingleNote)){
                tempAccidental = accidentals.get(String.valueOf(tempSingleNote)); 
                Note note = new Note(data.charAt(0), "1", tempAccidental, 0);
                toReturn = note;
            }
            else {
            Note note = new Note(data.charAt(0), "1", this.accidentals.get(String.valueOf(Character.toUpperCase(data.charAt(0)))), 0);
            toReturn = note;
            }
        }
        
        else {
            
            String accidental = "";
            int octave = 0;
            char noteName = ' ';
            String duration = "";
            for(int i = 0; i < token.data.length(); i ++){
                char currentChar = token.data.charAt(i);
                if(isAccidental(currentChar)){
                    accidental += currentChar;
                }else if(isPitch(currentChar)){
                    noteName = currentChar;
                }else if(isOctaveUp(currentChar)){
                    octave ++;
                }else if(isOctaveDown(currentChar)){
                    octave --;
                }else{
                    duration = token.data.substring(i);
                    break;
                }
            }

            //If there is no accidental,get accidentals from previously in the measure. 
            if(accidental.equals("")) {
                // check if it is exactly equal to one of the special accidentals (same octave)
                String tempNotePlusOctave = String.valueOf(noteName) + Integer.toString(octave);
                if(accidentals.containsKey(tempNotePlusOctave)){
                    accidental = accidentals.get(String.valueOf(tempNotePlusOctave)); 
                }
                else if(accidentals.containsKey(String.valueOf(noteName).toUpperCase())){
                    accidental = accidentals.get(String.valueOf(noteName).toUpperCase());
                }else{
                    accidental = "=";
                    accidentals.put(String.valueOf(noteName), "=");
                }
                    
            }
            else {
                String preciseNoteWithOctave = String.valueOf(noteName) + Integer.toString(octave);
                
                updateAccidentals(false, preciseNoteWithOctave, accidental, null);
            }

            //Set Default Duration
            if(duration.equals("")) {
                duration = "1";
            }
            
            Note note = new Note(noteName, duration, accidental, octave);
            
            toReturn = note;
        }    
        return toReturn;
    }
    
    private boolean isAccidental(char c){
        return (c == '^' || c=='=' || c=='_');
    }
    private boolean isPitch(char c){
        return ('a' <= c && c <= 'g') || ('A' <= c && c <= 'G');
    }
    private boolean isOctaveUp(char c){
        return c == '\'';
    }
    private boolean isOctaveDown(char c){
        return c == ',';
    }
    
    public Rest parseRest(Token t, Header header) {
        String restData = t.getData();
        //Make sure that the charactor starts with 'z'
        if(restData.charAt(0) == 'z'){
            String duration = restData.substring(1);
            if(duration.length() == 0){
                duration = "1";
            }
            Rest r = new Rest(duration);
            return r;
        }else{
            throw new RuntimeException("Invalid Rest Token: " + t);
        }
    }
    
    /**
     * mssand
     * Updates the map of accidentals either due to a new accidental or to
     * reaching the end of the measure
     * @param endOfMeasure True if at the end of the measure, false otherwise
     * @param note The note that has the accidental applied
     * @param accidental The accidental (should be _, __, ^, or ^^)
     * @param key The key from the header to reset the accidental map to at the beginning of a new measure
     * @throws UnknownAccidentalException If an a string not matching an accidental is encountered
     */
    public void updateAccidentals(boolean endOfMeasure, String note, String accidental, Map<Character, String> key) throws UnknownAccidentalException {
        if(endOfMeasure) {
            // zhengshu: set the map to default based on the header
            
            String k = header.getKey_K();
            accidentals = KeyToAccidentals.getAccidentals(k);// to be mutated during parseSingleNote


        }
        else {
            ArrayList<String> testList = new ArrayList<String>();
            testList.add("_");
            testList.add("__");
            testList.add("^");
            testList.add("^^");
            testList.add("=");
            if(!(testList.contains(accidental))) {
                throw new UnknownAccidentalException("Unknown accidental given in input file: " + accidental);
            }
            
            accidentals.put(note, accidental);
        }
    }
    
    
    /**
     * zhengshu
     * @param token
     * @return an instance of Nplet class
     */
    public Nplet splitTuplet(Token token){
        /// step 1. get type (i.e. DUPLET, TRIPLET, QUADRUPLET)
        // get length of Nplet
        Nplet.npletType type;
        String NpletData = token.getData();
        if (NpletData.substring(1,2).equals("2")){
             type = Nplet.npletType.DUPLET;
        }
        else if (NpletData.substring(1,2).equals("3")){
             type = Nplet.npletType.TRIPLET;
        }
        else if (NpletData.substring(1,2).equals("4")){
             type = Nplet.npletType.QUADRUPLET;
        }
        else throw new RuntimeException("illegal nplet!");
        
        // step 2. identify the class of each element of nplet;
        // and add each element to the output List<NoteElement>
        ArrayList<NoteElement> output = new ArrayList<NoteElement>();
        int length = NpletData.length();
        int current = 2;
        while (current < length){
            boolean anyMatchSoFar = false;
            for (int i = length; i > current; i--){
                String currentString = NpletData.substring(current, i);
//              System.out.println(currentString);
                // create two test tokens for NOTE and CHORD respectively
                Token testTokenNote = new Token(Token.Type.NOTE, "");
                Token testTokenChord = new Token(Token.Type.CHORD,"");
                if (testTokenNote.pattern.matcher(currentString).matches()){
                    // a NOTE token has been identified
                    anyMatchSoFar = true;
                    Note note = parseSingleNote(new Token(Token.Type.NOTE,currentString), header);
                    output.add(note);
                    current = i;
                }
                if (testTokenChord.pattern.matcher(currentString).matches()){
                    // a CHORD token has been identified
                    anyMatchSoFar = true;
                    current = i;
                    Chord chord = parseChord(new Token(Token.Type.CHORD,currentString));
                    output.add(chord);
                }                                                       
            }
            if (!anyMatchSoFar){
                throw new RuntimeException("this Nplet contains illegal note elements");
            }
        }
        
        Nplet finalNplet = new Nplet(type, output);

        return finalNplet;
    }
    
    
    /**
     * zhengshu
     * @param token
     * @return a list containing the notes that make up the chord
     */
    public Chord parseChord(Token token){
        String chordData = token.getData();
        // identify and parse each note in the chord
        ArrayList<NoteElement> output = new ArrayList<NoteElement>();
        int length = chordData.length();
        int current = 1;
        while (current < length-1){
            boolean anyMatchSoFar = false;
            for (int i = length-1; i > current; i--){
                String currentString = chordData.substring(current, i);
                // create two test tokens for NOTE and CHORD respectively
                Token testTokenNote = new Token(Token.Type.NOTE, "");
                if (testTokenNote.pattern.matcher(currentString).matches()){
                    // a NOTE token has been identified
                    anyMatchSoFar = true;
                    current = i;
                    Note note = parseSingleNote(new Token(Token.Type.NOTE,currentString), header);
                    output.add(note);
                }
                                                                    
            }
            if (!anyMatchSoFar){
                    throw new RuntimeException("this chord contains illegal note elements: " + token);
            }
        }
        // set the final output, which is an instance of Nplet class

        Chord finalChord = new Chord(output);
        return finalChord;
    }
    
}


