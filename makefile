JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	bbst.java \	RedBlackTree.java \

default:
	javac bbst.java	RedBlackTree.java

#classes:
#	$(CLASSES:.java=.class)

clean:	
	$(RM) *.class