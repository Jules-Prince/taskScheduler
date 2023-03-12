package task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task {

    private String name;
    private int C;
    private int R;
    private int D;
    private int P;
    private int O;
    private int L;
    private double U;

    private boolean needToProcess;
    private int restOfTickToProcess;

    public Task(String name, int c, int d, int p) {
        this.name = name;
        this.C = c;
        this.D = d;
        this.P = p;
        this.L = this.D - this.C;
        this.U = (double) this.C / this.P;
        this.R = this.L + this.C;

        this.needToProcess = true;
        this.restOfTickToProcess = c;
    }

    public void updateNeedToProcess(int currentTick){
        if(currentTick%this.getP() == 0){
            this.setNeedToProcess(true);
            if(this.isTaskFinished())
                this.setRestOfTickToProcess(this.getC());
        }
    }

    public boolean isTaskFinished(){
        return this.restOfTickToProcess==0;
    }

    @Override
    public String toString() {
        return "| " + this.getName() + " |" + "\n";
    }

    public void reduceRemainingProcessingTime() {
        this.setRestOfTickToProcess(this.getRestOfTickToProcess()-1);
    }
}
