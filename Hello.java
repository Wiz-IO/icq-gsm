import javax.microedition.lcdui.*; 
import Variables.*;
import java.util.Random;


public class Hello extends Canvas implements Runnable{
 public Hello(){
 	m   = 0;
 	c = 0x00FF00;
 	if(pbar != null) pbar = null;
 	pbar = new PBar(); 
 		
 }	
 
 public void paint( Graphics g ){
   	switch(m){
  		case 0:
 			g.setColor( 0xFFFFFF );
 			g.fillRect( 0, 0, getWidth(), getHeight() );
 			g.setColor( 0x000000 );
 			g.drawString(" WizQ - Connecting...", 0, 0, 20);
 			g.drawString(" Sponsor: The Wizard", 0, getHeight()- 20, 20);
 			g.setColor( c );
 			g.fillArc(40, 40, 100, 100, x, x + 5);
 			x += 5;
 			if (x > 355) x = 0; 
		break;

		case 2:
			g.setColor( 0xFFFFFF );
 			g.fillRect( 0, 0, getWidth(), getHeight() );
 			g.setColor( 0xFF0000 );
 			g.fillRect( 0, 0, getWidth(), 40 );
 			g.setColor( 0x000000 );
 			g.drawString(" Connection ERROR", 0, 15, 20);
		break; 			
   	}
   	m = 0;	
 }
 
 public static void Repaint(int i, int col){
 	if(col>-1) c = col;
  	Main.H.m = i; 
  	Main.H.repaint(); 
  	Main.H.serviceRepaints(); 		
 }
 
 void Back(){
 	pbar = null;
	Repaint(2, 0);
	try { new Thread().sleep(1000);} catch(Exception e){} 	
 	Main.M.commandAction(Main.M_END, null);
 }
 
 public void run(){
 	Repaint(0, 0x00FF00);	
 	try { Thread.sleep(300); } catch(Exception e){} 
 	Repaint(0, 0x00FF22);
 	Var.connected = false;
 	Var.event     = "";
    Var.session   = "";
    Var.trans     = "";
    Var.Qserver   = "";
    Var.Qport     = "";  	
    Var.ITrans    = 1001;
 	if(Main.M.ifproxy.isSelected(0)){
 		Var.MainServer = Var.So.concat(Main.M.proxy.getString()).concat(":").concat(Main.M.port.getString());
 		Var.GET        = "GET http://".concat(Var.IcqServer); 	
 	}else{
 		Var.MainServer = Var.So.concat(Var.IcqServer).concat(":80");
 		Var.GET 	   = "GET ";  			
 	}   
 	Repaint(0, 0x00FF44);
 	try{ Var.S = Var.Sender(Var.GET.concat(Var.HELLO).concat(//Proxy-Connection: Keep-Alive \r\nHost: bhm.icq.com \r\n
			"locale=en&clver=8003&clid=Wicked%5FTester&trans=1000&event=1500&%5F%5FiClient=%5Bobject%20Object%5D HTTP/1.0\r\n"+
 			"Accept: */* \r\nUser-Agent: WizICQ/LG8120/MIDP-2.0/CLDC-1.0 \r\n" +
 			"Cookie: geo=359; s_cc=true; s_sq=%5B%5BB%5D%5D \r\n\r\n")); 		
 	     Var.event   = Var.GetKey(Var.S,"event");
    	 Var.session = Var.GetKey(Var.S,"session");
    	 Var.Qserver = Var.decode(Var.GetKey(Var.S,"server"));
    	 Var.Qport   = Var.GetKey(Var.S,"port"); 
    	 if((Var.session=="")||(Var.Qserver=="")||(Var.Qport=="")||(Var.event.indexOf("1510") < 0 )) {Back(); return;}
 	} catch(Exception e){Back(); return;}//
 	if(Main.M.ifproxy.isSelected(0)){
 		Var.GET        = "GET http://".concat(Var.Qserver);	
 	}else{
 		Var.MainServer = Var.So.concat(Var.Qserver).concat(":").concat(Var.Qport);
 		Var.GET 	   = "GET ";  			
 	} 
 	Repaint(0, 0x00FF66);
	try{ Var.S = Var.Sender(Var.GET.concat(Var.DATA)
         	.concat("session=").concat(Var.session)
            .concat("&trans=").concat(Var.GetTrans())
            .concat("&sn=").concat(Main.M.uin.getString()).concat("&pass=").concat(Var.Cpass)
            .concat("&isicqemail=0&enc=1&status=0&event=2000&%5F%5FiClient=%5Bobject%20Object%5D")
            .concat(Var.S_HTTP).concat("\r\n"));	
    	Var.event   = Var.GetKey(Var.S,"event");   
        Var.session = Var.GetKey(Var.S,"session");
        Var.Epass = Var.GetKey(Var.S,"epass");
    	if((Var.session=="")||(Var.Epass=="")||(Var.event.indexOf("2010") < 0 )) {Back(); return;}  	
 	} catch(Exception e){Back(); return;}
 	Repaint(0, 0x00FFAA);
	try{ Var.S = Var.Sender(Var.GET.concat(Var.MONITOR)
         	.concat("session=").concat(Var.session)
           	.concat("&trans=").concat(Var.GetTrans())
           	.concat("&event=1900&%5F%5FiClient=%5Bobject%20Object%5D")
           	.concat(Var.S_HTTP).concat("\r\n"));	 
		Var.session = Var.GetKey(Var.S,"session");
        if(Var.S=="") {Back(); return;}                     	         		
 	} catch(Exception e){Back(); return;}
 	Repaint(0, 0x00FFDD);	
 	try { Var.save( Main.M.uin.getString(), 
 					Main.M.pass.getString(), 
 					Main.M.proxy.getString(), 
 					Main.M.port.getString(), 
 					Main.M.ifproxy.isSelected(0)
 				  );
 	} catch(Exception e){}	 	
  	Var.connected = true;	
 	Main.M.commandAction(Main.M_LIST, null);						
 } 

 public static int m = 0;
 public static int c;	 
 int x = 0;
 PBar pbar;	
}