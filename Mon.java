import javax.microedition.lcdui.*;
import java.util.*;
import Variables.*;

public class Mon extends Thread implements Runnable{
 public Mon(){}
 
 void Disconnect(String S) {
	Var.connected = false;
	Main.L.setTitle("Disconnect");
	Main.L.deleteAll();
	new Thread(new Play("/error.mid")).start();
	new ERR("ERROR", S, -2 );
 } 
 	
 public void run(){  
 	while( Var.connected ) execute();
 }

 public void execute(){
 	String S = "";
 	Main.L.setTitle("Online *");	
 	try { Thread.sleep(2000); } catch(Exception e){}
 	if(Var.connected){
		try{S = Var.Sender(Var.GET.concat(Var.MONITOR)
			.concat("session=").concat(Var.session)
			.concat("&trans=").concat(Integer.toString(Var.ITrans++))
    		.concat("&event=1900&%5F%5FiClient=%5Bobject%20Object%5D")
        	.concat(Var.S_HTTP).concat("\r\n"));   				
 		}catch(Exception e){ Disconnect("MONITOR: Time Out"); }
		Var.connected = DecodeEvent(S);	
	} 		
 }

 boolean DecodeEvent(String S){
 	int n = -1;
 	n = Integer.valueOf(Var.GetKey(S, "event"));	 	
 	switch(n){
 		case 3555:	BList.SetBuddyStatus(Var.GetKey(S, "sn"), Var.GetKey(S, "online"), Var.GetKey(S, "status")); 
 					return(true);		
 					
 		case 5050:	BList.SetBuddyMessage(Var.GetKey(S, "sn"), Var.decode(Var.GetKey(S, "msg")));
 					return(true);
 					
 		case 1000:  Disconnect("MONITOR: Error = 1000");	
 					return(false);
 					
  		//case 1100: return(true);//SCPing																
 	}
 	return(true);
 }
 
}