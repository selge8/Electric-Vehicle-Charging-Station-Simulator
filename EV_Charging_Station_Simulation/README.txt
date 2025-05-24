
Files included:
- Simulator.java: Main simulation class that coordinates the optimization of the charging station.
- Shuttle.java: Depicts distinct shuttle objects along with their attributes.
- PriorityQueue.java: Implementation of a binary heap for waiting shuttle management.

*Runned the program by "java Simulator sample_input1.txt 6" command.

Use Cases:
1. The program selects waiting shuttles using a binary heap max-priority queue.

2. Priority selection follows these rules:
   - Higher priority number = most urgent
   - When there are several IDs available, chargers are allocated by the lowest ID.
   - The simulation uses discrete event processing, advancing time to the next significant event.
   - The program iteratively increases the number of chargers until the average waiting time constraint is satisfied.

Data Structures Used:
- Binary heap (in PriorityQueue.java): For waiting shuttle selection.
- ArrayList: For storing shuttle data and simulation logs.
- LinkedList Queue: For managing arriving shuttles.
- Arrays: For tracking charger availability times.
