//----------------------------------------------------
// Title: Simulator class
// Author: Selin Göç
// ID: 32513063890
// Section: 4
// Assignment: 4
// Description: Main simulator for EV charging station optimization.
//----------------------------------------------------

import java.io.*;
import java.util.*;

public class Simulator {
    private static ArrayList<Shuttle> shuttles;
    private static int maxAvgWaitingTime;
    private static ArrayList<String> simulationLog;
    
    public static void main(String[] args)
    
    // Main method to run the charging station simulator.
    
    {
        if (args.length != 2) {
            System.out.println("Usage: java Simulator <filename> <maxAvgWaitingTime>");
            return;
        }
        
        String filename = args[0];
        maxAvgWaitingTime = Integer.parseInt(args[1]);
        
        shuttles = readInputFile(filename);
        if (shuttles == null) {
            System.out.println("Error reading input file");
            return;
        }
        
        int minChargers = findMinimumChargers();
        runFinalSimulation(minChargers);
    }
    
    private static ArrayList<Shuttle> readInputFile(String filename)
    
    // Reads shuttle data from input file.
    // Returns list of shuttles or null if error.
    
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            int n = Integer.parseInt(reader.readLine().trim());
            ArrayList<Shuttle> shuttleList = new ArrayList<>();
            
            // Add debug output
            System.out.println("Reading " + n + " shuttles from file:");
            
            for (int i = 0; i < n; i++) {
                String[] parts = reader.readLine().trim().split("\\s+");
                int id = Integer.parseInt(parts[0]);
                int priority = Integer.parseInt(parts[1]);
                int arrival = Integer.parseInt(parts[2]);
                int service = Integer.parseInt(parts[3]);
                
                // Add debug output
                System.out.println("Shuttle " + id + ": priority=" + priority + 
                                 " arrival=" + arrival + " service=" + service);
                
                shuttleList.add(new Shuttle(id, priority, arrival, service));
            }
            
            reader.close();
            return shuttleList;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
    
    private static int findMinimumChargers()
    
    // Finds minimum number of chargers needed. Returns minimum number of chargers required.
    
    {
        int chargers = 1;
        double avgWaitTime;
        
        do {
            avgWaitTime = runSimulation(chargers, false);
            if (avgWaitTime > maxAvgWaitingTime) {
                chargers++;
            }
        } while (avgWaitTime > maxAvgWaitingTime);
        
        return chargers;
    }
    
    private static void runFinalSimulation(int numChargers)
    
    //Runs final simulation with logging enabled.
    //Simulation results are printed.
    
    {
        simulationLog = new ArrayList<>();
        double avgWaitTime = runSimulation(numChargers, true);
        
        System.out.println("Minimum number of chargers required: " + numChargers);
        System.out.println("Simulation with " + numChargers + " chargers:");
        
        // Print the simulation log in the required format
        for (String logEntry : simulationLog) {
            System.out.println(logEntry);
        }
        
        System.out.printf("Average waiting time: %.1f minutes%n", avgWaitTime);
    }
    
    private static double runSimulation(int numChargers, boolean logging)
    
    // Runs the simulation with specified number of chargers. Returns average waiting time for all shuttles.
    
    {
        // Initialize chargers 
        int[] chargerFreeTime = new int[numChargers];
        Arrays.fill(chargerFreeTime, 0);
        
        // Priority queue for waiting shuttles, binary heap.
        PriorityQueue waitingQueue = new PriorityQueue(shuttles.size());
        
        // Queue for arriving shuttles.
        Queue<Shuttle> arrivingQueue = new LinkedList<>();
        for (Shuttle shuttle : shuttles) {
            arrivingQueue.offer(new Shuttle(shuttle.getId(), shuttle.getPriority(), 
                                          shuttle.getArrivalTime(), shuttle.getServiceTime()));
        }
        
        int currentTime = 0;
        int totalWaitingTime = 0;
        int processedShuttles = 0;
        
        System.out.println("\nStarting simulation with " + numChargers + " chargers");
        
        while (!arrivingQueue.isEmpty() || !waitingQueue.isEmpty()) {
            
            System.out.println("\nTime: " + currentTime);
            System.out.println("Arriving shuttles: " + arrivingQueue.size());
            System.out.println("Waiting shuttles: " + waitingQueue.getSize());
            
            // Add all shuttles that arrive at current time to waiting queue
            while (!arrivingQueue.isEmpty() && arrivingQueue.peek().getArrivalTime() <= currentTime) {
                Shuttle arriving = arrivingQueue.peek();
                System.out.println("Shuttle " + arriving.getId() + " arrives at time " + arriving.getArrivalTime());
                waitingQueue.insert(arrivingQueue.poll());
            }
            
            // Find available chargers and assign shuttles
            for (int i = 0; i < numChargers; i++) {
                if (chargerFreeTime[i] <= currentTime && !waitingQueue.isEmpty()) {
                    Shuttle shuttle = waitingQueue.extractMax();
                    int waitTime = currentTime - shuttle.getArrivalTime();
                    shuttle.setWaitingTime(waitTime);
                    
                    chargerFreeTime[i] = currentTime + shuttle.getServiceTime();
                    totalWaitingTime += waitTime;
                    processedShuttles++;
                    
                    if (logging) {
                        String logEntry = String.format("Charger %d takes shuttle %d at minute %d (wait: %d mins)",
                            i, shuttle.getId(), currentTime, waitTime);
                        simulationLog.add(logEntry);
                    }
                }
            }
            
            // Find next event time
            if (!arrivingQueue.isEmpty() || !waitingQueue.isEmpty()) {
                int nextArrival = arrivingQueue.isEmpty() ? Integer.MAX_VALUE : 
                                arrivingQueue.peek().getArrivalTime();
                int nextChargerFree = Integer.MAX_VALUE;
                
                // Find earliest charger availability
                for (int time : chargerFreeTime) {
                    if (time > currentTime && time < nextChargerFree) {
                        nextChargerFree = time;
                    }
                }
                
                // Advance time to next event
                int nextTime = Math.min(nextArrival, nextChargerFree);
                if (nextTime == Integer.MAX_VALUE || nextTime <= currentTime) {
                    System.out.println("Warning: No valid next event time found, terminating simulation");
                    break;
                }
                currentTime = nextTime;
                
                System.out.println("Advanced time to: " + currentTime);
            }
        }
        
        
        System.out.println("\nSimulation complete:");
        System.out.println("Processed shuttles: " + processedShuttles);
        System.out.println("Total waiting time: " + totalWaitingTime);
        
        return processedShuttles > 0 ? (double) totalWaitingTime / processedShuttles : 0.0;
    }
}