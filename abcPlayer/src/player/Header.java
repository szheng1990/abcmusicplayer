package player;

import java.util.ArrayList;
import java.util.List;

/**
 * Header Class contains all the information and possible header lines.
 * Some important values are the default length_L, and the number
 * and list of voices_V. 
 * @author kerengu
 *
 */
public class Header {

    private int trackNumber_X;
    private String name_T;
    private String composer_C;
    private String key_K;
    private String length_L;
    private String meter_M;
    private int tempo_Q;
    private List<String> voices_V;
    private int num_voices;
    
    /**
     * Create a Header object
     * @param X Track number
     * @param T Title
     * @param K Key
     */
    public Header(int X, String T, String K) {
        
        /** Check for ordering of the X, T, and K line. */
        if(X < 0){
            throw(new IllegalArgumentException("Track Number cannot be less than 0"));
        }
        if(T == null || T.equals("")){
            this.name_T = "Unknown";
        }
        if(K == null || K.equals("")){
            throw(new IllegalArgumentException("Must indicate a Key-value"));
        }
        
        this.trackNumber_X = X;
        this.name_T = new String(T);
        this.key_K = new String(K);
        
        //Set Default Attributes
        this.length_L = "1/8";
        this.meter_M = "4/4";
        this.composer_C = "Unknown";
        this.tempo_Q = 100;
        this.voices_V = new ArrayList<String>();
        this.voices_V.add("Default");
        this.num_voices = 1;
    }

    /**
     * Getters and Setters for EVERYTHING
     */
    public int getTrackNumber_X() {
        return trackNumber_X;
    }

    public void setTrackNumber_X(int trackNumber_X) {
        this.trackNumber_X = trackNumber_X;
    }

    public String getComposer_C() {
        return composer_C;
    }

    public void setComposer_C(String composer_C) {
        this.composer_C = composer_C;
    }

    public String getName_T() {
        return name_T;
    }

    public void setName_T(String name_T) {
        this.name_T = name_T;
    }

    public String getKey_K() {
        return key_K;
    }

    public void setKey_K(String key_K) {
        this.key_K = key_K;
    }

    public String getLength_L() {
        return length_L;
    }

    public void setLength_L(String length_L) {
        this.length_L = length_L;
    }

    public String getMeter_M() {
        return meter_M;
    }

    public void setMeter_M(String meter_M) {
        this.meter_M = meter_M;
    }

    public int getTempo_Q() {
        return tempo_Q;
    }

    public void setTempo_Q(int tempo_Q) {
        this.tempo_Q = tempo_Q;
    }

    public List<String> getVoices_V() {
        return new ArrayList<String>(voices_V);
    }

    public void setVoices_V(List<String> voices_V) {
        this.voices_V = new ArrayList<String>(voices_V);
    }

    //Special setter
    public void addVoice(String newVoiceName){
        this.voices_V.add(newVoiceName);
        this.num_voices++;
    }
    
    public int getNum_voices() {
        return num_voices;
    }

    public void setNum_voices(int num_voices) {
        this.num_voices = num_voices;
    }

    @Override
    public String toString() {
        return "Header [trackNumber_X=" + trackNumber_X + ", name_T=" + name_T
                + ", composer_C=" + composer_C + ", key_K=" + key_K
                + ", length_L=" + length_L + ", meter_M=" + meter_M
                + ", tempo_Q=" + tempo_Q + ", voices_V=" + voices_V
                + ", num_voices=" + num_voices + "]";
    }
    
}
