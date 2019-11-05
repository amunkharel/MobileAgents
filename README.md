MOBILE AGENTS

NOTE: Documentation might take up till Friday, November 8. Coding finished already and you can see that from our commit
history.

1. HOW TO INITIATE PROGRAM

    Go to your terminal and go to the directory where the jar file is. To run the jar, type "java -jar 
    mobileAgents1.0_akharel_gautams.jar [fileName]" where fileName is the name of the file that you want to run, like
    sample.txt/skew.txt/Star.txt/lol.txt and the remaining ones.
    
2. SIMULATION LOGIC

    The game works 95% of what the Professor specified it to be, the last remaining 5%, I will expand on BUGS AND
    IMPROVEMENTS. The configuration file gives a fire location/locations and an agent A starts at a base station(if
    2 base stations are given, 1 is ignored) and makes its way over to neighbor node X of the fire. Since X is yellow, 
    A will replicate itself to the neighbors of X (excluding the fire). Now, if a neighbor of X is yellow(say node Y), 
    an agent B forms there. If a neighbor of Y is yellow(say node Z), an agent C forms there. If a neighbor of Z is
    blue(say node V), an agent D forms there. So, in a nutshell, agents will keep forming until a blue node is 
    encountered. I just gave one example. All the nodes that have agents on them check every neighbor of theirs and
    as a result, there is not a cap on how many agents can form a ring to surround the fire, it could be 1, it
    could be 10, it could be 100. After agent cloning finishes, the fire is ready to spread and it consumes a node,
    after which, nodes which share an edge with the fire node, become yellow and the agent cloning starts again. 
    In the event that an agent does not reach a node that is yellow(which means that the agent is at a different
    yellow node), fire might spread to the yellow node without an agent on it. This goes on for all of the simulation
    until the fire reaches the base station after which the simulation stops and the game ends.
    
3. ALGORITHM

    The simulation starts after a file is read. Sentences from the file are separated into categories of nodes, edges,
    fires and base stations, stored in array lists. After the file is completely read, every node is put into the 
    Sensor class as an object and every edge the specific node has is used to determine the node's neighbors. Also, 
    every sensor is given an ID and if an edge exists between two nodes, a blocking queue is set up between them as 
    well. If a sentence starts with "fire", a node is given the state 'r' at the start of the program. Also, in the
    case of multiple base stations, the last station given on the file is set as the base station. Then, the program
    calculates the size of the graph and makes adjustments accordingly. If the graph is too big, like "lol.txt", the 
    graph makes smaller sized nodes and edges in the GUI. If the graph is small, like "sample.txt", the graph makes
    bigger sized nodes and edges in the GUI. In short, the GUI scales accordingly to the size of the graph. Also,
    ScrollPane is used in the GUI this time. I tried to use borderPane and make "lol.txt" nice but "lol.txt" looked
    very bad when using BorderPane. To counter that, I used ScrollPane which allows for 3000*3000 display because of the
    scrolling feature. "lol.txt" looks half decent when using ScrollPane. On to the GUI, the base station is recorded
    while reading the file and it's node is given the color green. The nodes on fire are given color red. Everything
    else if given the color blue at the start. Once the threads start to run, the color of the bases change accordingly.
    
    Onto threads, the GUI is the main thread and runs accordingly with the other threads that are created down the line.
    The method 'updateCanvas' handles all of the GUI stuff that arises from the change in back end data that come from
    the other threads, for example, if a node's state is y[for yellow]/r[for red]/b[for blue], the node is given
    colors yellow or red or blue. Now, how do these states change? When a file is read at first, some nodes are given
    the color red. These nodes send messages to their neighbor nodes informing that they are on fire and the neighbor 
    nodes turn yellow. To prevent other threads from running, the Thread.join() method is used. Immediately after 
    the initial graph of the nodes is made in the GUI, an agent is sent from the base station (all the actions done
    by an agent are stored in the Agent class where each agent is given a name). The agent finds a yellow node and 
    settles itself there. The way the agent goes to the yellow node is through BFS. After all the cloning action of the 
    agent has been done(which uses recursion to find nodes and which stops after finding blue/red nodes), the program is 
    ready to launch a multitude of threads pertaining to the sensors and the agents that were made/ are going to be made 
    (again, the launching of the later threads is done after cloning, by using Thread.join()) and the simulation is not 
    over until the sensor that is the base station catches on fire. Next, there is an order to how the fire spreads. The 
    nodes that have the earlier agents die first and the nodes that have the later agents die last, FIFO. However, if a 
    yellow node does not have an agent and it is also the case that every neighbor of this node does not have an agent, 
    fire catches randomly on that node. This can be seen in "lol.txt" where red dots populate the GUI because there were 
    no agents to form a ring of fire. In both of the cases, the node newly lit on fire sends messages to it's neighbors, 
    who now turn yellow. If any neighbor had an agent, agents are places recursively around the fire to form a wall and 
    if the neighbor had no agent, the neighbor simply turns yellow until agent cloning gets to it eventually. This 
    process goes on until the base station is on fire.
    
    Throughout the simulation, the log of agents that were created is displayed in the GUI. Since the agents are 
    numbered, it is elementary to keep track of the total number of agents created. Our GUI also shows on which node
    each agent is created and also shows how the agents disappear after fire consumes a node. Our GUI also shows 
    the messages that are generated throughout the program. Let's say if a node is on fire, our GUI will display
    the message, "Node X is on fire and sending messages to its neighbors". It is a very good visual aid to follow
    the program and see what is happening in any region of the graph. The messages are created using the 'Log' class.

4. BUGS AND IMPROVEMENTS

    There are no bugs in this game. As for improvements, we could have handled 2 base stations more elegantly.
    As it stands, if 2 base stations are given, 1 is ignored and only 1 is a base station. We had an idea that if
    1 base station was on fire, we could transfer the agent(s) information to the other surviving base station
    and I think we could have coded that if not for time constraints. Other than this, our programs runs exactly 
    like the description warranted. 
    
