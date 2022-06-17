import javax.microedition.lcdui.*;
import java.util.*;
import Variables.*;

public class Ping extends TimerTask implements Runnable{
 public Ping(int i){
 	J = i;
 	x = 0;	
 	T = new Timer();
 	T.schedule(this, 0, 1000);
 	StartTimer();
 }
 
 void StartTimer(){ f = true; } 
 void StopTimer() { f = false; }
 
 void Disconnect(String S) {
	Var.connected = false;
	T.cancel();
	T = null; 	
 	Main.L.setTitle("Disconnect");
 	Main.L.deleteAll();
 	new Thread(new Play("/error.mid")).start();
 	StopTimer();
 	new ERR("ERROR", S, -2 );
 } 

 public void run(){  
 	t = !t;   
  	if(t) Main.L.setTitle("Online");
 	else  Main.L.setTitle("  Online");		 	
   	if(f){
   		x++;
 		if(x > J){
 			Main.L.setTitle("ONLINE");
 			execute();
 			x = 0;
 		}
 	} else x = 0;
 }
 	
 public void execute(){
 	String S = "";
	StopTimer();	
 	if( Var.connected ){
		try{S = Var.Sender(Var.GET.concat(Var.DATA)
 				.concat("session=").concat(Var.session)
    			.concat("&trans=").concat(Integer.toString(Var.ITrans++))
    			.concat("&event=1110&%5F%5FiClient=null")
        		.concat(Var.S_HTTP).concat("\r\n"));   				
 		}catch(Exception e){ Disconnect("DATA: Time Out"); }	
	} 	
	if ( Var.connected && DecodePing(S) ) StartTimer();	
 }

 boolean DecodePing(String S){
 	if ( Var.GetKey(S,"session") != "" ) return(true);
 	else return(false);	 	
 }
 
 static Timer T;
 int x;
 int J; 
 boolean f;
 boolean t;
}