/**
 * Date 11/2/2019
 * By ...
 * Shortest remaining time first(SRTF)
 * Using context Switching
 */

import java.util.List;
import java.util.Queue;

public class PriorityScheduling {
    /**
     * instructions:
     *
     * First you can add any function or attribute you want
     * but you have to write
     *                      1- clean code
     *                      2- readable Names
     *                      3- clear Structure
     */
    private Queue<Process> readyQueue; //The ready Processes Queue you can use any data Structure you want
    private Graph graph; // graph object not implemented yet.
    /*
     * Rearrangement for the ready queue to fit the SJF algorithm
     */
    private void rearrangement(Queue<Process> readyQueue){
        //TODO rearrangement Algorithm
    }

    /*
     * Constructor
     */
    public PriorityScheduling(){
        //TODO allocate th ready Queue
    }

    /*
     * Parameterized Constructor take the process readyQueue
     */
    public PriorityScheduling(Queue<Process> readyQueue){
        //TODO allocate the ready Queue with the readyQueu Parameter
    }

    /*
     * //the Call function to start the algorithm.
     */
    public List<Process> start(){
        //TODO do your alogrithem here
        return null;
    }
}
