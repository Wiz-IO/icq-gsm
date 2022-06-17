//import javax.microedition.midlet.*;
import javax.microedition.lcdui.*; 

public class MSG extends TextBox implements CommandListener{
	
 public MSG(boolean T){
 	super("", "", 512, TextField.ANY);	
 	if (T){
 		addCommand(R_REPLY);
		addCommand(R_CANSEL);
	}else{
 		addCommand(W_SEND);
		addCommand(W_CANSEL);		
	}	
 }
 
 public void commandAction(Command c, Displayable d){} 	
 
 public static Command 	R_REPLY 	= new Command("Reply", 4, 1); 
 public static Command 	R_CANSEL    = new Command("Cansel", 2, 1); 
 public static Command 	W_SEND 	 	= new Command("Send", 4, 1); 
 public static Command 	W_CANSEL 	= new Command("Cansel", 2, 1); 
}