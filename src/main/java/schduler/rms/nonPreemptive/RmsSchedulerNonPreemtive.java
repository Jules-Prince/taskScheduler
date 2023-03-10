package schduler.rms.nonPreemptive;

import schduler.rms.RmsScheduler;
import schduler.Task;
import schduler.exception.CantProceedTaskException;

import java.util.List;

public class RmsSchedulerNonPreemtive extends RmsScheduler {

    public RmsSchedulerNonPreemtive(List<Task> tasks, int numberOfTicks) {
        super(tasks, numberOfTicks);
    }

    @Override
    public Task checkWhoProcess(Task lastProcessedTask, int currentTick) throws CantProceedTaskException {
        updateTaskList(currentTick);
        Task highestPriorityTask = getHighestPriorityTask();

        if (highestPriorityTask != null) {
            highestPriorityTask.reduceRemainingProcessingTime();
        }

        if (lastProcessedTask != null && !lastProcessedTask.isTaskFinished()) {
            if (!highestPriorityTask.equals(lastProcessedTask)) {
                throw new CantProceedTaskException(highestPriorityTask, lastProcessedTask, currentTick);
            }
        }

        return highestPriorityTask;
    }

}
