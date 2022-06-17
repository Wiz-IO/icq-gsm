import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import Variables.*;
import javax.microedition.lcdui.AlertType;

public class ERR extends Alert{
 public ERR(String title, String alertText, int time){
	super(title, "", null, AlertType.WARNING);
	setTimeout(time);	
	alertText = alertText.concat("\n")
	 .concat("RX = ").concat(Integer.toString((int)(Var.rx/1024))).concat(" kBytes\n")
	 .concat("TX = ").concat(Integer.toString((int)(Var.tx/1024))).concat(" kBytes\n")
	 .concat("TL = ").concat(Integer.toString((int)((Var.rx+Var.tx)/1024))).concat(" kBytes\n");
	setString(alertText);
	Main.D.setCurrent(this);
 }
 
  //public void commandAction(Command c, Displayable d){}
}