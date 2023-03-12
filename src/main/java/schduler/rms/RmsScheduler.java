package schduler.rms;

import lombok.Getter;
import lombok.Setter;
import schduler.Scheduler;
import task.Task;
import schduler.exception.CantProceedTaskException;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class RmsScheduler extends Scheduler {

    public RmsScheduler(List<Task> tasks, int numberOfTicks, boolean isPreemtive) {
        super(tasks, numberOfTicks, isPreemtive);
    }

    public Task getHighestPriorityTask() {
        return this.getTasks().stream()
                .filter(Task::isNeedToProcess).filter((t) -> t.getRestOfTickToProcess() > 0)
                .min(Comparator.comparing(Task::getP)).orElse(null);
    }

    public void updateTaskList(int currentTick) {
        this.getTasks().forEach((t) -> t.updateNeedToProcess(currentTick));
    }

    @Override
    public Task checkWhoProcess(Task lastProcessedTask, int currentTick) throws CantProceedTaskException {
        this.updateTaskList(currentTick);
        Task taskToProcess = this.getHighestPriorityTask();
        if (taskToProcess != null)
            taskToProcess.reduceRemainingProcessingTime();

        if(!isPreemtive()){
            checkIfTaskCanStart(lastProcessedTask, currentTick);
        }

        return taskToProcess;
    }

    private void checkIfTaskCanStart(Task lastProcessedTask, int currentTick) throws CantProceedTaskException {
        if (lastProcessedTask != null && !lastProcessedTask.isTaskFinished()) {
            if (!this.getHighestPriorityTask().equals(lastProcessedTask)) {
                throw new CantProceedTaskException(this.getHighestPriorityTask(), lastProcessedTask, currentTick);
            }
        }
    }
}
