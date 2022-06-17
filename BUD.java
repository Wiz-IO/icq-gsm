import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import Variables.*;

public class BUD{
 public BUD(int i){
 	
	bud_sn     	= new String[i];
	bud_alias  	= new String[i];
    bud_status 	= new String[i];
	bud_alias  	= new String[i];
	online	    = new boolean[i];	
	msg	        = new String[i];
 }
 
 public static String[] 	bud_sn 		;
 public static String[] 	bud_alias 	;
 public static String[] 	bud_status 	;
 public static boolean[] 	online;
 public static String[] 	msg;
}
