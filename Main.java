import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import Variables.*;

public class Main extends MIDlet implements CommandListener{
 public Main(){
 	M = this;
 	F = new Form("WizQ - Main Menu");
 	D = Display.getDisplay(this);	
 }

 public void pauseApp(){
 	new ERR("DISCONNECT", "", -2 );	
 }
 public void destroyApp(boolean unconditional){
 	D = null;
 	notifyDestroyed();
 }
 
 public void startApp(){
 	D.setCurrent(S);
 	try { Thread.sleep(1500); } catch(Exception e){}
 	F.append(uin);
	F.append(pass);
	F.append(ifproxy);
	ifproxy.append("  Use Proxy",null);
	ifproxy.setSelectedIndex(0, true);
	F.append(proxy);
	F.append(port);
	F.addCommand(M_CONNECT);
	F.addCommand(M_EXIT);	 
	try{
		String s = Var.load();
 		if(s!=""){
 		 	uin.setString(Var.GetKey(s,"uin"));
 		 	pass.setString(Var.GetKey(s,"pass"));
 		 	proxy.setString(Var.GetKey(s,"proxy"));
 		 	port.setString(Var.GetKey(s,"port"));
 		 	String f = Var.GetKey(s,"ifproxy");
 		 	if ( f.indexOf("0") >-1 ) ifproxy.setSelectedIndex(0, false);
 		}	
 	} catch(Exception e){}
	F.setCommandListener(this); 
 	D.setCurrent(F);
 }

 void init(){
 	if(T != null ) T = null;	
 	if(H != null ) H = null; 
 	if(L != null ) L = null;	
 }
 
 public void commandAction(Command c, Displayable d){
 	if(c == M_EXIT) { destroyApp(true); } 
 	if(c == M_CONNECT){
 		Var.Cpass 	= Var.encrypt( pass.getString() );
 		if(ifproxy.isSelected(0)){
 			Var.MainServer = "".concat(Var.So).concat(proxy.getString());
 			if(port.getString()!="") Var.MainServer = Var.MainServer.concat(":").concat(port.getString());
 			Var.GET        = "GET http://".concat(Var.IcqServer); 	
 		}else{
 			Var.MainServer = "".concat(Var.So).concat(Var.IcqServer).concat(":80");
 			Var.GET 	   = "GET ";  			
 		} 		
		init();
 		H  = new Hello();	
 		D.setCurrent(H);
 		T = new Thread(H);
 		T.start(); 		
 	} 	
 	if(c == M_END){
 		init(); 
 		D.setCurrent(F);
 	}
 	if(c == M_LIST){
 		init();
 		L = new BList();
 		D.setCurrent(L);
 	}
 } 
 
 public static Main  		M; 
 public static Display  	D;
 public static Form 		F; 
 public static Hello  		H;
 public static Thread 		T; 
 public static BList  		L;
 public static Logo  		S = new Logo(); 
 
 public static Command 	M_END 	    = new Command("1", 4, 1); 
 public static Command 	M_LIST 	    = new Command("2", 4, 1); 
 public static Command 	M_CONNECT 	= new Command("Connect", 4, 1); 
 public static Command 	M_EXIT 	    = new Command("Exit", 2, 1);
 public static TextField uin		= new TextField("ICQ UIN",      "43865798", 	16, TextField.NUMERIC);
 public static TextField pass		= new TextField("PASSWORD",     "stoikaan", 	16, TextField.PASSWORD);
 public static TextField proxy		= new TextField("Proxy Server", "213.226.6.65", 16, TextField.ANY);//85.130.115.1
 public static TextField port  		= new TextField("Proxy Port",   "8080", 		16, TextField.NUMERIC); 
 public static ChoiceGroup ifproxy	= new ChoiceGroup("", Choice.MULTIPLE); 
}


//event=1000&trans=0&session=0&code=1000