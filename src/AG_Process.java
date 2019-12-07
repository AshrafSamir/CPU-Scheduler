import java.awt.Color;

public class AG_Process extends Process {
    private int quantum;
    private int AG_Factor;
    private int priority;

    public AG_Process() {
    	
	}
    
    public AG_Process(String name, Color color, int arrivalTime, int burstTime,int priority, int quantum){
    	super(name,color,arrivalTime,burstTime);
        this.quantum=quantum;
        this.priority=priority;
    }
    
    public int getQuantum() {
    	return this.quantum;
    }

    public void setQuantum(int quantum) {
    	this.quantum = quantum;
    }

    public int getPriority() {
    	return this.priority;
    }

    public void setPriority(int priority) {
    	this.priority = priority;
    }
    
    public int getFactor() {
    	return this.AG_Factor;
    }

    public void setFacotr(int factor) {
    	this.AG_Factor = factor;
    }
    
}
