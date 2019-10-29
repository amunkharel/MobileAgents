
package mobileAgents;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        Graph graph = new Graph("sample.txt");
        graph.readFile();
        graph.accessStoredInfoFromFile();
        graph.initializeThreads();
    }
}



