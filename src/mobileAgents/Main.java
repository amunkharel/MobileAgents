
package mobileAgents;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        Graph graph = new Graph("sample2.txt");
        graph.readFile();
        graph.accessStoredInfoFromFile();
        graph.initializeThreads();

    }
}



