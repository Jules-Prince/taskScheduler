package schduler.rms.preemptive;

import schduler.rms.RmsScheduler;
import schduler.Task;

import java.util.List;

public class RmsSchedulerPreemtive extends RmsScheduler {

    public RmsSchedulerPreemtive(List<Task> tasks, int numberOfTicks) {
        super(tasks, numberOfTicks);
    }

    @Override
    public Task checkWhoProcess(Task lastProcessedTask, int currentTick) {
        this.updateTaskList(currentTick);
        Task taskToProcess = this.getHighestPriorityTask();
        if (taskToProcess != null)
            taskToProcess.reduceRemainingProcessingTime();
        return taskToProcess;
    }



}
