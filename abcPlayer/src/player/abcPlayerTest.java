package player;

import org.junit.Test;

public class abcPlayerTest {

    @Test
    public void fur_elise(){
      Main.play("sample_abc/fur_elise.abc");
      System.out.println("Playing fui_elise.abc");
    }
    
    @Test
    public void invention(){
        Main.play("sample_abc/invention.abc");
        System.out.println("Playing invention.abc");
    }
    
    @Test
    public void little_night_music(){
        Main.play("sample_abc/little_night_music.abc");
        System.out.println("Playing little_night_music");
    }
    
    @Test
    public void paddy(){
        Main.play("sample_abc/paddy.abc");
        System.out.println("Player Paddy");
    }
    
    @Test
    public void piece1(){
        Main.play("sample_abc/piece1.abc");
    }
    
    @Test
    public void piece2(){
        Main.play("sample_abc/piece2.abc");
    }
    
    @Test
    public void pieceRepeat(){
        Main.play("sample_abc/pieceRepeat.abc");
    }
    
    @Test
    public void prelude(){
        Main.play("sample_abc/prelude.abc");
    }
    
    @Test
    public void scale(){
        Main.play("sample_abc/scale.abc");
      
    }
}
