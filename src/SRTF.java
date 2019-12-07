/**
 * Date 11/2/2019
 * By Ahmed Sayed
 *
 * Shortest remaining time first(SRTF)
 * Using context Switching
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;


public class SRTF {
    /**
     * Instructions:
     *
     * First you can add any function or attribute you want
     * but you have to write
     *                      1- clean code
     *                      2- readable Names
     *                      3- clear Structure
     */

    private static ArrayList<String> processess = new ArrayList<>();
    private static double averageTurnAroundTime = 0;
    private static double averageWaitingTime = 0;

    private Queue<Process> readyQueue; //The ready Processes Queue you can use any data Structure you want
    private Graph graph; // graph object not implemented yet.

    public static void main(String[] args) throws InterruptedException {



        ArrayList<Process> sortedByArrival = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        SRTF call = new SRTF();
        int arrival,burst,contextSwitch,processNum;
        String name;

        System.out.print("Enter The Context Switch : ");
        contextSwitch = input.nextInt();
        System.out.print("Enter The Number of Processes : ");
        processNum = input.nextInt();
        for (int i=0;i<processNum;i++){
            arrival = input.nextInt();
            burst = input.nextInt();
            name = input.next();
            Process process = new Process(name, arrival, burst);
            sortedByArrival.add(process);
        }

        call.printProcesses(sortedByArrival);

        ArrayList<Process> out;
        out = call.start(sortedByArrival,contextSwitch);
        call.printProcesses(out);
        call.printVector(processess);

        System.out.println("\nAverage Turn Around Time = " + averageTurnAroundTime/out.size());
        System.out.println("Average Waiting Time = " + averageWaitingTime/out.size());

    }


    /*
     * Rearrangement for the ready queue to fit the SJF algorithm
     */
    private void rearrangement(Queue<Process> readyQueue){
        //TODO rearrangement Algorithm
    }

    /*
     * Constructor
     */
    public SRTF(){
        //TODO allocate th ready Queue
    }

    /*
     * Parameterized Constructor take the process readyQueue
     */
    public SRTF(Queue<Process> readyQueue){
        //TODO allocate the ready Queue with the readyQueu Parameter
    }

    /*
     * //the Call function to start the algorithm.
     */
    public ArrayList<Process> start(ArrayList<Process> list,int cs) throws InterruptedException {
        //TODO do your alogrithem here


        int currentTime = 0;
        int contextSwitching = cs;
        int  currentProcessIndex = getSmallestBurst(list, currentTime);
        int tmp;
        int counter = 0;
        ArrayList<String> processHistory = new ArrayList<>();


        while(counter != list.size()){

            if(!processHistory.contains(list.get(currentProcessIndex).getName())){

                list.get(currentProcessIndex).setWaitingTime(currentTime - list.get(currentProcessIndex).getArrivalTime());
            }
            //System.out.println(currentProcessIndex);
            processHistory.add(list.get(currentProcessIndex).getName());

            processess.add(list.get(currentProcessIndex).getName());
            graph.add(list.get(currentProcessIndex), currentTime );
            System.out.println(processess);
            list.get(currentProcessIndex).setBurstTime(list.get(currentProcessIndex).getBurstTime()-1);
            currentTime += 1;

            if(list.get(currentProcessIndex).getBurstTime() == 0){
                list.get(currentProcessIndex).setFinishTime(currentTime + contextSwitching);
                graph.addTerminatedProcess(list.get(currentProcessIndex));
            }

            tmp = getSmallestBurst(list, currentTime);
            if (tmp != currentProcessIndex){
                //System.out.println(currentProcessIndex +  " : " + tmp );
                currentProcessIndex = tmp;
                currentTime += contextSwitching;
                for(int j = 0;j<contextSwitching;j++){

                    processess.add("--");
                }
                tmp = getSmallestBurst(list, currentTime);
                currentProcessIndex = tmp;
            }

            for(int j=0;j<list.size();j++){

                if(list.get(j).getBurstTime() == 0){

                    counter++;
                }
            }
            if(counter == list.size())break;
            counter = 0;
        }

        for(int i=0; i<list.size(); i++){

            list.get(i).setTurnaroundTime((list.get(i).getFinishTime() - list.get(i).getArrivalTime()));
            averageTurnAroundTime += list.get(i).getTurnaroundTime();
            averageWaitingTime += list.get(i).getWaitingTime();

        }

        return list;
    }

    private int getSmallestBurst(ArrayList<Process> arr, int currentTime){

        int smallestBurst = 0;
        int tmp = 0;
        for (int i = 0; i < arr.size(); i++ ){

            if (arr.get(i).getBurstTime() != 0) {smallestBurst = arr.get(i).getBurstTime(); tmp = i; break; }
        }
        int p = tmp;
        for (int i=tmp;i<arr.size();i++){

            //if (smallestBurst == 0) smallestBurst = arr.get(i).getBurstTime();

            if(arr.get(i).getBurstTime()<smallestBurst && arr.get(i).getArrivalTime() <= currentTime && arr.get(i).getBurstTime()!=0){
                smallestBurst = arr.get(i).getBurstTime();
                p = i;
            }


        }
        return p;
    }

    public void printProcesses(ArrayList<Process> p){

        for (int i=0;i<p.size();i++){
            System.out.println(p.get(i).toString());
        }


    }

    public void printVector (ArrayList<String> p){

        for (int i=0;i<p.size();i++){
            System.out.print(p.get(i) + " ");
        }


    }
}
