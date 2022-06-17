package Variables;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
import javax.microedition.rms.*;


 public class Var{
 
  public static String load() throws IOException, RecordStoreException{
	byte[] buf;
	String s="";
	ByteArrayInputStream ais;
	DataInputStream dis;	
	RecordStore ini;
	ini = RecordStore.openRecordStore("setting", true);
	buf = ini.getRecord(1);
	ais = new ByteArrayInputStream(buf);
	dis = new DataInputStream(ais);
	s = dis.readUTF();
	ini.closeRecordStore();
	return s;
 } 
 
  public static void save(String s1, String s2, String s3, String s4, boolean f1) throws IOException, RecordStoreException{
	byte[] buf;
	ByteArrayOutputStream aos;
	DataOutputStream dos;	
	RecordStore ini = null;
	int i;
	try{ RecordStore.deleteRecordStore("setting"); } catch(Exception e){}
	ini = RecordStore.openRecordStore("setting", true);
	ini.addRecord(null, 0, 0);
	aos = new ByteArrayOutputStream();
	dos = new DataOutputStream(aos);
	String s5 = "0";
	if(f1) s5 = "1";
	dos.writeUTF(
		"&uin=".concat(s1)
		.concat("&pass=").concat(s2)
		.concat("&proxy=").concat(s3)
		.concat("&port=").concat(s4)
		.concat("&ifproxy=").concat(s5).concat("&")
	);
	buf = aos.toByteArray();
	ini.setRecord(1, buf, 0, buf.length);
	ini.closeRecordStore();
 }  
 
  public static String GetKey(String str, String key){
  String Result = "";
  int i = str.indexOf(key); if (i<0) return "";
  Result = str.substring(i, str.length() );
  i = Result.indexOf("="); if (i<0) return "";  
  Result = Result.substring(i+1, Result.length() );
  i = Result.indexOf("&"); if (i<0) return "";
  Result = Result.substring(0, i );  
  return Result;
 }
 
 public static String encode(String s) {
  if (s == null)  return null;
  StringBuffer buf = new StringBuffer();
  for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (ch < 48 || (ch > 57 && ch < 65) || ch > 122) {
      buf.append("%");
      buf.append(Integer.toHexString(ch));
      } else buf.append(ch);
  }
  return buf.toString();
 }
 
 public static String decode(String decode) {
  StringBuffer decoded = new StringBuffer();
  char nextChar;
  String encString;
  Integer encInteger;
  for(int index=0; index < decode.length(); index++) {
    nextChar = decode.charAt(index);
    if (nextChar == '+') { decoded.append(" "); }
    else if (nextChar == '%') {
       encInteger = Integer.valueOf( decode.substring(index+1,index+3),16);
       index += 2;
       nextChar = (char)encInteger.intValue();
       decoded.append(nextChar);
    } else { decoded.append(nextChar); }
  }
  return decoded.toString();
 }

 public static String encrypt(String s) {
	int L = s.length();
	int i = 0;	
	StringBuffer    b  = new StringBuffer();
	char c;
	while (i < L){
		c = s.charAt(i);
		b.append((char) (((c & 240) >> 4) + 65) ); 
		b.append((char) ((c & 15) + 65) );
		i++;
	} 
	return(b.toString());
 }

 public static String GetTrans(){
 	return Integer.toString(Var.ITrans++);
 }
 
 public static String Sender(String Q) throws IOException {
  	SocketConnection c = null;
  	InputStream     is = null;
  	OutputStream    os = null;
  	StringBuffer    b  = new StringBuffer();
  	String      Result = "";
    int 			ch = 0; 
    int 			 n = 0;	
  	try {
    	c = (SocketConnection)Connector.open(MainServer);
    	os = c.openOutputStream();
    	tx += Q.length();
    	os.write(Q.getBytes());
    	is = c.openInputStream();
    	while(( ch = is.read() ) != -1) b.append((char) ch); 
    		Result = b.toString();
    	rx += Result.length();
  	} finally {
    	if (is != null) is.close();
    	if (os != null) os.close();
    	if (c  != null)  c.close();
    	if ( Result.indexOf("200 OK")<0 ) Result = "";
  	}
  	return Result;
 }

 public static String GetStatus(String S){
 int s;
 try{s = Integer.valueOf(S)&0xFFFF;}catch(Exception e){ return "0"; } 	
 if (s > 256) s ^= 256;				//remove  INVISIBLE 
 if ((s & 5)   ==   5) return "4";	//STATUS_AWAY 
 if (s == 0)           return "1";	//STATUS_AVAILABLE
 if ((s & 1)   ==   1) return "2";	//STATUS_AWAY
 if ((s & 2)   ==   2) return "3";	//STATUS_DND 
 if ((s & 4)   ==   4) return "4";	//STATUS_NA 
 if ((s & 16)  ==  16) return "5";	//STATUS_OCCUPIED 
 if ((s & 256) == 256) return "6";	//STATUS_INVISIBLE
 return "1";
}
//event=3555&sn=294516469&status=268500992&online=1&pending=0&10010000
//event=3555&sn=294516469&status=268500993&online=1&pending=0&10010001

 
 public static String 	event			= "";
 public static String 	session			= "";
 public static String 	trans			= "";
 public static String 	Qserver			= "";
 public static String 	Qport			= "";	
 
 //
 public static String 	S_HTTP = " HTTP/1.0\r\nProxy-Connection: Keep-Alive\r\nAccept: */*\r\nUser-Agent: WizQ/MIDP-2.0/CLDC-1.0\r\n"; 

 public static String 	S 				= ""; 
 public static String 	So 				= "socket://";  
 public static String 	MainServer 		= ""; 
 public static String 	IcqServer 		= "64.12.202.153"; //
 public static String 	GET 			= ""; 
 public static String 	HELLO 			= "/hello?";
 public static String 	DATA 			= "/data?"; 
 public static String 	MONITOR			= "/monitor?";
 public static String 	Cpass 			= "";
 public static String 	Epass 			= ""; 
 public static int 		ITrans          = 1001;
 public static boolean 	connected 		= false; 
 
 public static long		rx          	= 0;
 public static long		tx          	= 0; 
}