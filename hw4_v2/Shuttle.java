//------------------------------------------------------
// Title: Shuttle class
// Author: Selin Göç
// ID: 32513063890
// Section: 4
// Assignment: 4
// Description: This class represents a shuttle object.
//------------------------------------------------------

public class Shuttle {
    private int id;
    private int priority;
    private int arrivalTime;
    private int serviceTime;
    private int waitingTime;
    
    public Shuttle(int id, int priority, int arrivalTime, int serviceTime)
    
    // Constructor to create a shuttle object.
    {
        this.id = id;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.waitingTime = 0;
    }
    
    public int getId()
    
    // Returns the shuttle's ID.
    
    {
        return id;
    }
    
    public int getPriority()
    
    // Returns the shuttle's priority.
    
    {
        return priority;
    }
    
    public int getArrivalTime()
    
    // Summary: Returns the shuttle's arrival time.
    
    {
        return arrivalTime;
    }
    
    public int getServiceTime()
    
    // Summary: Returns the shuttle's service time.
    
    {
        return serviceTime;
    }
    
    public int getWaitingTime()
    
    // Returns the shuttle's waiting time.
    
    {
        return waitingTime;
    }
    
    public void setWaitingTime(int waitingTime)
    
    // Sets the shuttle's waiting time.
    
    {
        this.waitingTime = waitingTime;
    }
}