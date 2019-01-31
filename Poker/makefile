JFLAGS = -g
JC = javac
RMI = rmic
RMISRC = ../Server/CSConn_Stub.class
RMIDST = ./
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Evaluate.java \
	GUI.java \
	SConnInterface.java \
	TavoloPoker.java \
	TexasGame.java \
	ServerRMI.java \
	Utility.java \
	TexasHoldem.java

default: classes \
	genrmi \
	csconnrmi

classes: $(CLASSES:.java=.class)

genrmi: 
	rmic ServerRMI

csconnrmi: 
	cp $(RMISRC) $(RMIDST)
clean:
	$(RM) *.class
