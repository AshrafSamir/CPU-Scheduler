/**
 * Date 11/25/2019
 * By Ashraf Samer
 * Shortest Job First Algorithm
 */


import java.lang.reflect.Array;
import java.util.*;

public class SJF {
    /**
     * Instructions:
     *
     * First you can add any function or attribute you want
     * but you have to write
     *                      1- clean code
     *                      2- readable Names
     *                      3- clear Structure
     */

     //The ready Processes Queue you can use any data Structure you want


    private Graph graph = new Graph("SJF"); // graph object not implemented yet.
    private ArrayList<Process> Out = new ArrayList<Process>();
    public static void main(String[] args) throws InterruptedException {

        ArrayList<Process> sortedByArrival = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        SJF call = new SJF();
        int arrival,burst;
        String name;

        for (int i=0;i<5;i++){
            arrival = input.nextInt();
            burst = input.nextInt();
            name = input.next();
            Process process = new Process(name, arrival, burst);
            sortedByArrival.add(process);
        }

        ArrayList<Process> out;
        out = call.start(sortedByArrival);
        call.print(out);
        System.out.println(call.averageWaiting());
        System.out.println(call.averageTurnaround());



    }

    private void rearrangement(ArrayList<Process> sortedByArrival){
        Collections.sort(sortedByArrival, (o1, o2) -> {
            int value = o1.getArrivalTime();
            if (o2.getArrivalTime() == value) {

               if (o2.getBurstTime() < value) return 1;
                else if (o2.getBurstTime() > value) return -1;
                else return 0;
            } else {

               if (o2.getArrivalTime() < value) return 1;
               else if (o2.getArrivalTime() > value) return -1;
                else return 0;
           }
       });
    }
    public SJF(){
        //TODO allocate th ready Queue
    }
    public SJF(ArrayList<Process> readyQueue){
    }
    public ArrayList<Process> start(ArrayList<Process> sortedByArrival) throws InterruptedException {
        ArrayList<Process> output = new ArrayList<>();
        Process process;
        rearrangement(sortedByArrival);
        int lastFinishTime = 0;

        int time = 0;
        while(sortedByArrival.size()>0){
            ArrayList<Process> windowOfArrived = new ArrayList<>();
            for(int j=0;j<sortedByArrival.size();j++){
                if(sortedByArrival.get(j).getArrivalTime()<=lastFinishTime){
                    windowOfArrived.add(sortedByArrival.get(j));
                }
            }
            if((windowOfArrived.size()==0)){
                output.add(sortedByArrival.get(0));
                time = sortedByArrival.get(0).getArrivalTime();
                for (int j = 0; j <sortedByArrival.get(0).getBurstTime(); j++) {
                    graph.add(sortedByArrival.get(0), time);
                    time++;
                }
                sortedByArrival.get(0).setWaitingTime(0);
                sortedByArrival.get(0).setTurnaroundTime(sortedByArrival.get(0).getBurstTime());
                lastFinishTime=sortedByArrival.get(0).getArrivalTime()+sortedByArrival.get(0).getBurstTime();
                graph.addTerminatedProcess(sortedByArrival.get(0));
                sortedByArrival.remove(sortedByArrival.get(0));
            }
            else {
                process = getSmallestBurst(windowOfArrived);
                process.setWaitingTime(lastFinishTime-process.getArrivalTime());
                process.setTurnaroundTime(process.getWaitingTime()+process.getBurstTime());
                lastFinishTime += process.getBurstTime();
                output.add(process);
                for (int j = 0; j <process.getBurstTime(); j++) {
                    graph.add(process, time);
                    time++;
                }
                graph.addTerminatedProcess(process);
                windowOfArrived.clear();
                sortedByArrival.remove(process);
            }
        }

        Out = output;
        return output;
    }
    private Process getSmallestBurst(ArrayList<Process> arr){
        int smallestBurst = arr.get(0).getBurstTime();
        Process p = arr.get(0);
        for (int i=0;i<arr.size();i++){
            if(arr.get(i).getBurstTime()<smallestBurst){
                smallestBurst = arr.get(i).getBurstTime();
                p = arr.get(i);
            }
        }
        return p;
    }

    private void print(ArrayList<Process> p){
        for (int i=0;i<p.size();i++){
            if(p.get(i)==null){
                System.out.println("Null");
                continue;
            }
            System.out.println(p.get(i).toString());
        }


    }
    private void print(Process p){
        System.out.println(p.toString());
    }
    private double averageWaiting(){
        double sum = 0;
        for(int i=0;i<Out.size();i++){
            sum+=Out.get(i).getWaitingTime();
        }
        return (sum/Out.size());
    }
    private double averageTurnaround(){
        double sum = 0;
        for(int i=0;i<Out.size();i++){
            sum+=Out.get(i).getTurnaroundTime();
        }
        return (sum/Out.size());
    }

    /*
0 5 P1
2 3 P2
4 2 P3
6 4 P4
7 1 P5

0 7 P1
2 4 P2
4 1 P3
5 2 P4

0 1 P1
3 5 P2
4 2 P3
6 4 P4
7 1 P5
 */

}
