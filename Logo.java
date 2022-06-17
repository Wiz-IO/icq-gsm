import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class Logo extends Canvas {
 public void paint( Graphics g ) {
 	try{ I = Image.createImage("/logo.png"); }catch(Exception e){}
 	g.drawImage(I, 0, 0, 20);
 	I = null;
 }
 private static 	Image 		I; 
}