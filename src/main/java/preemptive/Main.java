package preemptive;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Task task1 = new Task("task1", 1, 5, 5);
        Task task2 = new Task("task2", 2, 10, 10);
        Task task3 = new Task("task3", 7, 29, 29);
        Task task4 = new Task("task4", 3, 8, 8);

        Graph graph = new Graph(100, List.of(task1, task2, task3, task4));

        System.out.println(graph);
    }
}
