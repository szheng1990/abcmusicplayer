// mssand
package player;

import java.util.HashMap;

public class KeyToAccidentals {
    public static HashMap<String, String> getAccidentals(String key) {
        // create a map of keys to the appropriate map of the key signature
        HashMap<String, HashMap<String, String>> mapOKeys = new HashMap<String, HashMap<String, String>>();
        
        // C major and A minor
        HashMap<String, String> blank = blankKey();
        mapOKeys.put("C", blank);
        mapOKeys.put("Am", blank);
        
        // G major and E minor
        blank = blankKey();
        blank.put("F", "^");
        mapOKeys.put("G", blank);
        mapOKeys.put("Em", blank);

        // D major and B minor
        blank = blankKey();
        blank.put("F", "^");
        blank.put("C", "^");
        mapOKeys.put("D", blank);
        mapOKeys.put("Bm", blank);

        // A major and F# minor
        blank = blankKey();
        blank.put("F", "^");
        blank.put("C", "^");
        blank.put("G", "^");
        mapOKeys.put("A", blank);
        mapOKeys.put("^Fm", blank);
        
        // E major and C# minor
        blank = blankKey();
        blank.put("F", "^");
        blank.put("C", "^");
        blank.put("G", "^");
        blank.put("D", "^");
        mapOKeys.put("E", blank);
        mapOKeys.put("^Cm", blank);

        // B major and G# minor
        blank = blankKey();
        blank.put("F", "^");
        blank.put("C", "^");
        blank.put("G", "^");
        blank.put("D", "^");
        blank.put("A", "^");
        mapOKeys.put("B", blank);
        mapOKeys.put("^Gm", blank);
        
        // F# major and D# minor
        blank = blankKey();
        blank.put("F", "^");
        blank.put("C", "^");
        blank.put("G", "^");
        blank.put("D", "^");
        blank.put("A", "^");
        blank.put("E", "^");
        mapOKeys.put("^F", blank);
        mapOKeys.put("^Dm", blank);
        
        // C# major and A# minor
        blank = blankKey();
        blank.put("F", "^");
        blank.put("C", "^");
        blank.put("G", "^");
        blank.put("D", "^");
        blank.put("A", "^");
        blank.put("E", "^");
        blank.put("B", "^");
        mapOKeys.put("^C", blank);
        mapOKeys.put("^Am", blank);
        
        // F major and D minor
        blank = blankKey();
        blank.put("B", "_");
        mapOKeys.put("F", blank);
        mapOKeys.put("Dm", blank);
        
        // Bb major and G minor
        blank = blankKey();
        blank.put("B", "_");
        blank.put("E", "_");
        mapOKeys.put("_B", blank);
        mapOKeys.put("Gm", blank);
        
        // Eb major and C minor
        blank = blankKey();
        blank.put("B", "_");
        blank.put("E", "_");
        blank.put("A", "_");
        mapOKeys.put("_E", blank);
        mapOKeys.put("Cm", blank);
        
        // Ab major and F minor
        blank = blankKey();
        blank.put("B", "_");
        blank.put("E", "_");
        blank.put("A", "_");
        blank.put("D", "_");
        mapOKeys.put("_A", blank);
        mapOKeys.put("Fm", blank);
        
        // Db major and Bb minor
        blank = blankKey();
        blank.put("B", "_");
        blank.put("E", "_");
        blank.put("A", "_");
        blank.put("D", "_");
        blank.put("G", "_");
        mapOKeys.put("_D", blank);
        mapOKeys.put("_Bm", blank);
        
        // Gb major and Eb minor
        blank = blankKey();
        blank.put("B", "_");
        blank.put("E", "_");
        blank.put("A", "_");
        blank.put("D", "_");
        blank.put("G", "_");
        blank.put("C", "_");
        mapOKeys.put("_G", blank);
        mapOKeys.put("_Em", blank);
        
        // Cb major and Ab minor
        blank = blankKey();
        blank.put("B", "_");
        blank.put("E", "_");
        blank.put("A", "_");
        blank.put("D", "_");
        blank.put("G", "_");
        blank.put("C", "_");
        blank.put("F", "_");
        mapOKeys.put("_C", blank);
        mapOKeys.put("_Am", blank);
        
        return mapOKeys.get(key);
        
    }
    
    private static HashMap<String, String> blankKey() {
        HashMap<String, String> blank = new HashMap<String, String>();
        blank.put("A", "=");
        blank.put("B", "=");
        blank.put("C", "=");
        blank.put("D", "=");
        blank.put("E", "=");
        blank.put("F", "=");
        blank.put("G", "=");
        
        return blank;
        
    }
}
