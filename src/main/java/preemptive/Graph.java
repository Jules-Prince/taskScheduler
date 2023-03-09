package preemptive;

import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Graph {

    private int numberOfTicks;
    private Map<Integer, Task> processBar;
    private List<Task> tasks;

    public Graph(int numberOfTicks, List<Task> tasks) {
        this.numberOfTicks = numberOfTicks;
        this.processBar = new HashMap<>(this.numberOfTicks);
        this.tasks = tasks;

        this.go();
    }

    private void go() {
        for (int i = 0; i < this.getNumberOfTicks(); i++) {
            checkWhoProcess(i);
        }
    }

    private void checkWhoProcess(int currentTick) {
        updateTask(currentTick);
        Task taskToProcess = this.getHighestPriorityTask();
        if (taskToProcess != null)
            taskToProcess.setRestOfTickToProcess(taskToProcess.getRestOfTickToProcess() - 1);
        this.getProcessBar().put(currentTick, taskToProcess);
    }

    private void updateTask(int currentTick) {
        this.getTasks().forEach((t) -> t.updateNeedToProcess(currentTick));
    }

    private Task getHighestPriorityTask() {
        return this.getTasks().stream()
                .filter(Task::isNeedToProcess).filter((t) -> t.getRestOfTickToProcess() > 0)
                .min(Comparator.comparing(Task::getP)).orElse(null);
    }

    @Override
    public String toString() {
        String lineTop = this.getTopLine();
        String lineTask = this.getTaskLine();
        String lineBottom = this.getBottomLine();
        return lineTop + "\n" + lineTask + "\n" + lineBottom;
    }

    private String getBottomLine() {
        StringBuilder sb = new StringBuilder();
        for (Integer tick : this.getProcessBar().keySet()) {
            if (tick == 0)
                sb.append(0).append("-".repeat(this.getTasks().get(0).getName().length() + 3));
            else if (tick % 5 == 0){
                sb.append(tick).append("-".repeat(this.getTasks().get(0).getName().length() + 4 - String.valueOf(tick).length()));
            }

            else
                sb.append("-".repeat(this.getTasks().get(0).getName().length() + 4));
        }
        return sb.toString();
    }

    private String getTopLine() {
        StringBuilder sb = new StringBuilder();
        int lengthTaskString = this.getTasks().get(0).getName().length() + 4;
        for (Task t : this.getProcessBar().values()) {
            if (t != null)
                sb.append("_".repeat(lengthTaskString));
            else
                sb.append(" ".repeat(lengthTaskString));
        }
        return sb.toString();
    }


    private String getTaskLine() {
        StringBuilder sb = new StringBuilder();
        this.getProcessBar().values().forEach((t) -> {
            if (t == null)
                sb.append(" ".repeat(this.getTasks().get(0).getName().length() + 4));
            else
                sb.append("| ").append(t.getName()).append(" |");
        });
        return sb.toString();
    }
}
