package schduler;

import graph.Graph;
import graph.ProcessBar;
import lombok.Getter;
import schduler.exception.CantProceedTaskException;
import task.Task;

import java.util.List;

@Getter
public abstract class Scheduler {

    private List<Task> tasks;
    private Graph graph;
    private int numberOfTicks;

    private boolean isPreemtive;

    public Scheduler(List<Task> tasks, int numberOfTicks, boolean isPreemtive) {
        this.tasks = tasks;
        this.numberOfTicks = numberOfTicks;
        this.graph = new Graph();
        this.isPreemtive = isPreemtive;
        this.initGraph();
    }

    public void initGraph(){
        for (Task task : this.getTasks()) {
            this.getGraph().put(task, new ProcessBar(task.getName()));
        }
    }

    public void startScheduling() {
        for (int tick = 0; tick < this.getNumberOfTicks(); tick++) {
            Task lastTask = this.getLastTaskFromProgressBar();
            try {
                Task taskToProceed = this.getNextTaskToProcess(tick, lastTask);
                if (taskToProceed == null) continue;

                this.putProcessValueInGraph(tick, taskToProceed);
            } catch (CantProceedTaskException e) {
                System.err.println(e.getMessage());
                return;
            }
        }
    }

    private Task getNextTaskToProcess(int tick, Task lastTask) throws CantProceedTaskException {
        Task taskToProceed = this.checkWhoProcess(lastTask, tick);
        if (taskToProceed == null) {
            for (ProcessBar processBar : this.getGraph().values()) {
                processBar.put(tick, false);
            }
            return null;
        }
        return taskToProceed;
    }

    private void putProcessValueInGraph(int tick, Task taskToProceed) {
        this.getGraph().get(taskToProceed).put(tick, true);
        for (ProcessBar processBar : this.getGraph().values()) {
            if (processBar != this.getGraph().get(taskToProceed)) {
                processBar.put(tick, false);
            }
        }
    }

    private Task getLastTaskFromProgressBar() {
        Task lastTask = null;
        for (ProcessBar processBar : this.getGraph().values()) {
            if (processBar.isLastValueTrue())
                return this.getGraph().getKeyFromValue(processBar);

        }
        return lastTask;
    }


    public abstract Task checkWhoProcess(Task lastProcessedTask, int currentTick) throws CantProceedTaskException;

}
