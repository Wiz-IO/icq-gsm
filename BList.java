import javax.microedition.lcdui.*;
import Variables.*;

public class BList extends List implements CommandListener{
 public BList(){
 	super( "Online", List.IMPLICIT); 	
 	addCommand(B_READ);
	addCommand(B_SEND);
	setCommandListener(this);	
	 
	String bmax = Var.GetKey(Var.S, "buddies");
    Bmax = Integer.valueOf(bmax);
 	if(Bmax > 0){
 		B = new BUD(Bmax);
    	for(int i=0; i<Bmax; i++){
    		SetBuddy(i, Var.S);
    		if (B.online[i])  DrawBuddy(i, 0, 3, true);   			
    	}  	 		
 	}	

	ping  = new Ping(60);
	mon   = new Mon();
	TMon  = new Thread(mon);
	TMon.start();
	new Thread(new Play("/start.mid")).start();	
 }

 public void DrawBuddy(int n, int p, int f, boolean t){
 	IMG = null;
 	if(f > 0 ){
 		if (t){
 			try{ IMG = Image.createImage("/s".concat(B.bud_status[n]).concat(".png")); }catch(Exception e) {}
 		}else{
 			try{ IMG = Image.createImage("/mes.png"); }catch(Exception e){}
 		}
	}
    switch (f){
    	case 0: delete(p);													 break;
    	case 1:	set(p, B.bud_alias[n].concat("-").concat(B.bud_sn[n]), IMG); break;
    	case 2:	set(p, B.bud_alias[n].concat("#").concat(B.bud_sn[n]), IMG); break;
    	case 3: append(B.bud_alias[n].concat("-").concat(B.bud_sn[n]), IMG); break;	                                                   
    }	
 }
 
 public static void SetBuddy(int i, String S){
 	String I = Integer.toString(i);
	B.bud_sn[i]     = Var.GetKey( S, "bud".concat(I).concat("_sn") );
	B.bud_alias[i]  = Var.GetKey( S, "bud".concat(I).concat("_alias") );
	B.bud_alias[i]  = Var.decode( B.bud_alias[i] );	
	B.online[i]     = ( Var.GetKey( S, "bud".concat(I).concat("_online") ).indexOf("0") > -1  ) ? false : true;
    B.bud_status[i] = Var.GetKey( S, "bud".concat(I).concat("_status") );
    B.bud_status[i] = Var.GetStatus( B.bud_status[i] );
	B.msg[i]	    = "";
 }

 public static int ListFindUIN(String S){
	for(int i=0; i<Main.L.size(); i++) 
		if ( Main.L.getString(i).indexOf(S) > -1 ) return(i);
	return(-1); 	
 }

 public static int ListGetSelectedUIN(){
 	for(int i=0; i<Bmax; i++) 
		if ( Main.L.getString(Main.L.getSelectedIndex()).indexOf(B.bud_sn[i]) > -1 ) return(i);
	return(-1);	
 }

 public static int BuddyFindUIN(String S){
	for(int i=0; i<Bmax; i++) 
		if ( S.indexOf(B.bud_sn[i]) > -1 ) return(i);
	return(-1);	
}

 public static void SetBuddyStatus(String N, String O, String Z){
 	int i = BuddyFindUIN(N);
 	if( i == -1 ) return; 	
 	int j = ListFindUIN(N); 	
 	B.bud_status[i] = Var.GetStatus(Z); 
 	if ( O.indexOf("0") > -1  ) {													// remove 
		if( j > -1 ) Main.L.delete(j);
 		B.online[i] = false;				
	} else { 																		// change image	
		if( j > -1 ){	
			Main.L.DrawBuddy(i, j, 1, true);									
		} else {																	// add
			new Thread(new Play("/online.mid")).start();	
			Main.L.DrawBuddy(i, 0, 3, true);							
		}
	}
 }

 public static void SetBuddyMessage(String N, String M){
	int j = BuddyFindUIN(N);
	if( j == -1 ) return; 
 	if(  B.msg[j] == "" ) B.msg[j] = M;
 	else B.msg[j] = B.msg[j].concat("\n").concat(M);	
	int i = ListFindUIN(N);	 
 	if( i > -1 ){
 		Main.L.DrawBuddy(j, i, 2, false);
 		new Thread(new Play("/message.mid")).start();
 	}	 	
 }

 public void ReadMsg(int i, int j){  	
 	if( i > -1 ){
 		DrawBuddy(i, j, 1, true);
 		R.setTitle( "Message from ".concat(B.bud_alias[i]) );
 		R.setString( B.msg[i] );
 		B.msg[i] = "";
		R.setCommandListener(this);
 		Main.D.setCurrent(R);  			
 	}	
 }

 public void WriteMsg(int i){
 	if( i > -1 ){
 		W.setTitle( "Write to ".concat(B.bud_alias[i]) );
 		W.setString( "" );
		W.setCommandListener(this);
 		Main.D.setCurrent(W); 
	}	
 }

 public void commandAction(Command c, Displayable d){
  	String S = "";
 	if(c == W.W_CANSEL) Main.D.setCurrent(this);
 	if(c == R.R_CANSEL) Main.D.setCurrent(this); 
   	int n = ListGetSelectedUIN();
 	if(c == B_SEND) WriteMsg(n);
 	if(c == B_READ) { ReadMsg(n, getSelectedIndex()); }
 	if(c == W.W_SEND){	
 		if(Var.connected){
 			try{S = Var.Sender(Var.GET.concat(Var.DATA)
 				.concat("session=").concat(Var.session)
 				.concat("&msg=").concat(Var.encode(W.getString()))
 				.concat("&sn=").concat(B.bud_sn[n])
 				.concat("&trans=").concat(Integer.toString(Var.ITrans++))
 				.concat("&auto=0&event=5000&%5F%5FiClient=%5Bobject%20Object%5D")
 				.concat(Var.S_HTTP).concat("\r\n"));	
 			}catch(Exception e){ new ERR("ERROR", S, -2 );}
 			if(S!="") {
 				new Thread(new Play("/msgsent.mid")).start();
 				Main.D.setCurrent(this);
 			}else new Thread(new Play("/error.mid")).start();
 		} else new ERR("ERROR", S, -2 );	
 	}
 	if(c == R.R_REPLY) WriteMsg(n);	
 }
 
 public static Image 	IMG;  
 public static BUD		B;
 public static int 	 	Bmax; 
 public static Command 	B_READ 	= new Command("Read", 4, 1); 
 public static Command 	B_SEND  = new Command("Write", 2, 1);  
 public static MSG 		R       = new MSG(true);
 public static MSG 		W       = new MSG(false); 
 Mon    	mon;
 Ping   	ping;
 Thread 	TMon;
 Thread 	TPing;
} 
