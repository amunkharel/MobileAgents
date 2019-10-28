
package mobileAgents;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        Graph graph = new Graph("BaseStationCenter.txt");
        graph.readFile();
        graph.accessStoredInfoFromFile();
        graph.initializeThreads();

    }
}



