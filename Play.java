import javax.microedition.lcdui.*;
import javax.microedition.media.*;

public class Play implements Runnable{
 public Play(String f){
 	F = f;	
 	if(f.indexOf(".mid")>-1) T = "audio/midi";
 	else
 		if(f.indexOf(".wav")>-1) T = "audio/X-wav";
 }

 public void run(){
 	PlaySound();
 }
 
 public void PlaySound(){
	try{
		p = Manager.createPlayer(Main.M.getClass().getResourceAsStream(F), T);
		p.realize();
		p.prefetch();
		p.setLoopCount(1);
		p.start();
		while(p.getState()==Player.STARTED){}
		p.stop();
		p.close();
		p = null;
	}catch(Exception e){}
 } 

 Player p;
 String F;
 String T;  
}