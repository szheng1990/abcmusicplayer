package player;

public class Rest implements NoteElement{
    
    private String duration;
    
    public Rest(String duration){        
        if(duration.contains("/")) {
            if(duration.indexOf("/") == 0) {
                if(duration.length() == 1) {
                    this.duration = "1/2";
                }
                else {
                    this.duration = "1" + duration;
                }
            }
            else {
                int index = duration.indexOf("/");
                if(index == (duration.length() - 1)) {
                    this.duration = duration + "2";
                }
                else {
                    this.duration = duration;
                }
            }
        }
        else {
            this.duration = duration;
        }
    }
    
    @Override
    public noteType getType() {
        
        return noteType.REST;
    }

    public String getDuration() {
        return duration;
    }
    
    public double getDecimalDuration(){
        int index = this.duration.indexOf("/");
        // In the case that duration is not a fraction
        if(index < 0){
            return Double.parseDouble(this.duration);
        }
        float num = Float.parseFloat(this.duration.substring(0, index));
        float denom = Float.parseFloat(this.duration.substring(index+1));
        return num / denom;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Rest [duration=" + duration + "]";
    }

    
}
