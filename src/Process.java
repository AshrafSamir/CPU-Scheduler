import java.awt.*;
import java.util.Comparator;

public class Process{
    private String name;
    private Color color;
    private int arrivalTime;
    private int burstTime;
    private int waitingTime;
    private int TurnaroundTime;
    private int finishTime;

    public Process(){

    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public Process(String name, Color color, int arrivalTime, int burstTime){
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
    public Process(String name, int arrivalTime, int burstTime){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public Color getColor() {
        return color;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public String getName() {
        return name;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return TurnaroundTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", waitingTime=" + waitingTime +
                ", TurnaroundTime=" + TurnaroundTime +
                ", finishTime=" + finishTime +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        TurnaroundTime = turnaroundTime;
    }



}
