package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import player.NoteElement.noteType;

/**
 * This class represents a musical Piece. A piece has a Header
 * element and a body element. Piece also remembers the address of
 * the file. 
 */
public class Piece {

        private Header header;
        private Body body;
        private String fileName;
        private int beatsPerMinute;
        private int ticksPerQuarterNote;
        
        /**
         * Default Constructor
         * Setting of instance variables is accomplished elsewhere
         */
        public Piece(){
            this.header = null;
            this.body = new Body();
            this.fileName = new String();
        }
        
        /**
         * Get Useful Notes processes the NoteElements of Piece and returns a list of
         * Playable Notes. 
         * @return A List<PlayableNotes> to be entered into a SequencePlayer
         */
        public List<PlayableNotes> getUsefulNotes() {
            
            ArrayList<PlayableNotes> toReturn = new ArrayList<PlayableNotes>();
            
            ArrayList<Integer> denomList = new ArrayList<Integer>();
            for(String v: body.getNotes().keySet()) {
                for(NoteElement n : body.getNotes().get(v)) {
                    String durationString = n.getDuration();
                    if(durationString.indexOf("/") > 0){
                        int durationDenom = Integer.parseInt(durationString.substring(durationString.indexOf("/") + 1)); //kgu: +1 to only take denom, and not "/"
                        denomList.add(durationDenom);
                    }
                    
                }
            }
            
            int leastCommonMultiple = lcm(denomList);
            
            // get values for L (default note length)
            String l = header.getLength_L();
            int lNum = Integer.parseInt(l.substring(0, l.indexOf("/")));
            int lDenom = Integer.parseInt(l.substring(l.indexOf("/")+1));
                        
            // conversionFactor is getting (quarter note) / (default note) to get
            // a scaling factor for the tempo
            double conversionFactor = 4 * (double) lNum / (double)lDenom;
            this.beatsPerMinute = (int) (header.getTempo_Q() * conversionFactor);
            
            // leastCommonMultiple is the number of ticks per default note length
            // conversionFactor2 is a scaling factor to determine the length of a quarter
            // note relative to a note of default length
            double conversionFactor2 =  (double) lDenom / ((double) lNum * 4);
            this.ticksPerQuarterNote = (int) (conversionFactor2 * leastCommonMultiple);
            
            
            //Process one voice at a time, resets the tick- counter at the beginning of each loop
            for(String voice : body.getNotes().keySet()) {
                int counter = 0;
                for(NoteElement ne : body.getNotes().get(voice)) {
                    
                    if (ne.getType() == noteType.NOTE) {
                        double decimalDuration = ne.getDecimalDuration();
                        int ticks = (int) (leastCommonMultiple * decimalDuration * 1);
                        PlayableNotes toAdd = new PlayableNotes((Note) ne, counter, ticks);
                        toReturn.add(toAdd);
                        counter += ticks;
                    }
                    else if (ne.getType() == noteType.REST) {
                        double decimalDuration = ne.getDecimalDuration();
                        int ticks = (int) (leastCommonMultiple * decimalDuration * 1);
                        counter += ticks;
                    }
                    else if (ne.getType() == noteType.CHORD) {
                        int ticks = 0;
                        int maxTicks = 0;
                        for (Note note : ((Chord) ne).notes) {
                            double decimalDuration = note.getDecimalDuration();
                            maxTicks =  Math.max((int) (leastCommonMultiple * decimalDuration * 1), ticks);
                            ticks = (int) (leastCommonMultiple * decimalDuration * 1);
                            PlayableNotes toAdd = new PlayableNotes((Note) note, counter, ticks);
                            toReturn.add(toAdd);
                        }
                        counter += maxTicks;
                    }
                    else if (ne.getType() == noteType.NPLET) {
                        //Make sure to get Nplet time instead of regular duration. 
                        double decimalDuration = ne.getDecimalDuration();
                        for (NoteElement note : ((Nplet) ne).getNotes()) {
                            int ticks = (int) (leastCommonMultiple * decimalDuration * 1);
                            PlayableNotes toAdd = new PlayableNotes((Note) note, counter, ticks);
                            toReturn.add(toAdd);
                            counter += ticks;
                        }
                    }
                    
                }
            }            
            return toReturn;
        }

        /**
         * @return the beatsPerMinute
         */
        public int getBeatsPerMinute() {
            return beatsPerMinute;
        }

        /**
         * Default Getters and Setters.
         */
        public Header getHeader() {
            return header;
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
        
        /**
         * Getters and Setters for Beats Per Minutes
         * @return
         */
        public int getBeatsPerMinutes() {
            return beatsPerMinute;
        }
        public void setBeatsPerMinutes(int beatsPerMinutes) {
            this.beatsPerMinute = beatsPerMinutes;
        }

        /**
         * Getters and Setters for TicksPerQuarterNotes
         * @return
         */
        public int getTicksPerQuarterNote() {
            return ticksPerQuarterNote;
        }
        public void setTicksPerQuarterNote(int ticksPerQuarterNote) {
            this.ticksPerQuarterNote = ticksPerQuarterNote;
        }

        /**
         * Getter for the list of NoteElements contained in the Piece, 
         * maypped by the name of the voice. Default if there's only 1 voice
         * @return
         */
        public HashMap<String, List<NoteElement>> getNotes(){
            return body.getNotes();
        }
        
        /**
         * Returns the LCM of a list. Authored by grumpy mssand
         * @param thisIsKillingMe
         * @return
         */
        private int lcm(ArrayList<Integer> thisIsKillingMe) {
            int answer = 1;
            for(int i : thisIsKillingMe) {
                answer = i * (answer / gcd(i, answer));
            }
            return answer;
        }
        
        /**
         * Greatest Common Divisor Helper Function for Conversion.
         * Used in lcm
         * @param a
         * @param b
         * @requirs a, b, be positive 
         * @return the gcd of a and b
         */
        private static int gcd(int a, int b)
        {
            while (b > 0)
            {
                int temp = b;
                b = a % b; // % is remainder
                a = temp;
            }
            return a;
        }

        @Override
        public String toString() {
            return "Piece [header=" + header + ", body=" + body + ", fileName="
                    + fileName + ", beatsPerMinute=" + beatsPerMinute
                    + ", ticksPerQuarterNote=" + ticksPerQuarterNote + "]";
        }
        
        
}
