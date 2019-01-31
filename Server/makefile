JFLAGS = -g
JC = javac
RMI = rmic
RMISRC = ../Poker/ServerRMI_Stub.class
RMIDST = ./
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	CSConn.java \
	SConnInterface.java \
	TexasGame.java \
	ServerForStart.java 

default: classes \
	genrmi \
	serverrmi

classes: $(CLASSES:.java=.class)

genrmi: 
	rmic CSConn

serverrmi: 
	cp $(RMISRC) $(RMIDST)
clean:
	$(RM) *.class
