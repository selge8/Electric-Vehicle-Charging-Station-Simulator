//----------------------------------------------------
// Title: PriorityQueue class
// Author: Selin Göç
// ID: 32513063890
// Section: 4
// Assignment: 4
// Description: Binary heap max-priority queue for shuttles.
//----------------------------------------------------

public class PriorityQueue {
    private Shuttle[] heap;
    private int size;
    private int capacity;
    
    public PriorityQueue(int capacity)
    
    // To create a priority queue. 
    
    {
        this.capacity = capacity;
        this.heap = new Shuttle[capacity + 1]; // 1-indexed
        this.size = 0;
    }
    
    public boolean isEmpty()
    
    // Checks if the priority queue is empty. Returns true if empty, false otherwise.
    
    {
        return size == 0;
    }
    
    public int getSize()
    
    // Returns the current size of the priority queue. 
    
    {
        return size;
    }
    
    public void insert(Shuttle shuttle)
    
    // Inserts a shuttle into the priority queue.    
    {
        if (size >= capacity) {
            throw new RuntimeException("Priority queue is full");
        }
        
        size++;
        heap[size] = shuttle;
        heapifyUp(size);
    }
    
    public Shuttle extractMax()
    
    // Removes and returns the highest priority shuttle.    
    {
        if (isEmpty()) {
            throw new RuntimeException("Priority queue is empty");
        }
        
        Shuttle max = heap[1];
        heap[1] = heap[size];
        size--;
        if (size > 0) {
            heapifyDown(1);
        }
        return max;
    }
    
    private void heapifyUp(int index)
    

    
    {
        while (index > 1) {
            int parent = index / 2;
            if (hasHigherPriority(heap[index], heap[parent])) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }
    
    private void heapifyDown(int index)
    
    
    {
        while (index * 2 <= size) {
            int leftChild = index * 2;
            int rightChild = leftChild + 1;
            int largest = index;
            
            if (hasHigherPriority(heap[leftChild], heap[largest])) {
                largest = leftChild;
            }
            
            if (rightChild <= size && hasHigherPriority(heap[rightChild], heap[largest])) {
                largest = rightChild;
            }
            
            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }
    
    private boolean hasHigherPriority(Shuttle s1, Shuttle s2)
    
    // Compares two shuttles to determine priority order.
    
    {
        if (s1.getPriority() > s2.getPriority()) {
            return true;
        } else if (s1.getPriority() == s2.getPriority()) {
            // If same priority, earlier arrival has higher priority
            return s1.getArrivalTime() < s2.getArrivalTime();
        }
        return false;
    }
    
    private void swap(int i, int j)

    // Swaps two elements in the array.

    {
        Shuttle temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}