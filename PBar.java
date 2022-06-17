import javax.microedition.lcdui.*;
import java.util.*;
import Variables.*;

public class PBar extends TimerTask implements Runnable{
 public PBar(){
 	T = new Timer();
 	T.schedule(this, 0, 100);
 }
 
 public  void run(){//synchronized
  	Main.H.Repaint(0,-1);
 }
 
 Timer T;
} 