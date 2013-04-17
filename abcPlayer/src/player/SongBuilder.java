package player;

import java.io.*;

/**
 * handles the reading of the file, and passing of data from lexer to parser 
 * to internal state representing the notes in NoteElement form. A SongBuilder 
 * object is multiple-use: after being constructed, should be able to process 
 * multiple input files sequentially
 * 
 * @author kerengu 
 *
 */
public class SongBuilder {

        /**
         * Takes in a file name and outputs a Piece represending the song. 
         * @param abcFileName
         */
        public Piece buildSong(String abcFileName){
            //Open up the File
            String fileContent = getFile(abcFileName);
            
            //Create Lexer, pass string into lexer
            Lexer lexer = new Lexer(fileContent);
            
            //Pass lexer into Parser to get Piece
            Parser parser = new Parser(lexer);
            
            Piece toReturn = parser.parse();
            
            return toReturn;
            
        }
        
        /**
         * Takes in a file name and returns the same file 
         * as a string. Print stackTrace if file not found.
         * @param abcFileName
         * @return
         */
        public String getFile(String abcFileName){
            String toReturn = new String();
            try {
                FileInputStream fstream = new FileInputStream(abcFileName);
                //Get the object of DataInputStream
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                
                String thisLine = new String();
                while((thisLine = br.readLine()) != null){
                    toReturn += thisLine + "\n";
                }
                toReturn = toReturn.substring(0, toReturn.length()-1);
                
                in.close();
                
                
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
            return toReturn;
        }
}
