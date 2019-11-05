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
    

4. BUGS AND IMPROVEMENTS

    There are no bugs in this game. As for improvements, we could have handled 2 base stations more elegantly.
    As it stands, if 2 base stations are given, 1 is ignored and only 1 is a base station. We had an idea that if
    1 base station was on fire, we could transfer the agent(s) information to the other surviving base station
    and I think we could have coded that if not for time constraints. Other than this, our programs runs exactly 
    like the description warranted. 
    
