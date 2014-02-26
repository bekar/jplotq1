run: clean
	javac Graph.java
	java Graph

clean:
	rm -f *.class

py:
	./graph.py
