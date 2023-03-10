package schduler.rms;

import lombok.Getter;
import lombok.Setter;
import schduler.Scheduler;
import schduler.Task;
import schduler.rms.nonPreemptive.RmsSchedulerNonPreemtive;
import schduler.rms.preemptive.RmsSchedulerPreemtive;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public abstract class RmsScheduler extends Scheduler {

    public RmsScheduler(List<Task> tasks, int numberOfTicks) {
        super(tasks, numberOfTicks);
    }

    public static RmsScheduler newRmsScheduler(List<Task> tasks, int numberOfTicks, boolean isPreemtive){
        return isPreemtive ? new RmsSchedulerPreemtive(tasks, numberOfTicks) : new RmsSchedulerNonPreemtive(tasks, numberOfTicks);
    }

    public Task getHighestPriorityTask() {
        return this.getTasks().stream()
                .filter(Task::isNeedToProcess).filter((t) -> t.getRestOfTickToProcess() > 0)
                .min(Comparator.comparing(Task::getP)).orElse(null);
    }

    public void updateTaskList(int currentTick) {
        this.getTasks().forEach((t) -> t.updateNeedToProcess(currentTick));
    }

}
