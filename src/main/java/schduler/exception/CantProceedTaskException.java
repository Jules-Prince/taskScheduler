package schduler.exception;

import schduler.Task;

public class CantProceedTaskException extends Exception {
    public CantProceedTaskException(Task taskToProcess, Task taskThatCantBeStopped, int currentTick) {
        super("Error at tick : " + currentTick + "  " + taskToProcess.getName() + " can't proceed because " + taskThatCantBeStopped.getName() + " hasn't finished" +
                "");
    }
}
