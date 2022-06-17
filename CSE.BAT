PATH "C:\Program Files\Java\jdk1.5.0_05\bin";"E:\EricJ2ME\J2ME_SDK\PC_Emulation\WTK1\bin"
javac -bootclasspath midpapi.zip -d tmpclasses -classpath tmpclasses *.java 
preverify -classpath midpapi.zip;.\tmpclasses -d .\classes .\tmpclasses
jar cmf MANIFEST.MF WizQ.jar -C .\classes\ .
jar umf MANIFEST.MF WizQ.jar -C .\res .