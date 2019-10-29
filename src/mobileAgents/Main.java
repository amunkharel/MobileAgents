
package mobileAgents;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        Graph graph = new Graph(args[0]);
        graph.readFile();
        graph.accessStoredInfoFromFile();
        graph.initializeThreads();
    }
}



