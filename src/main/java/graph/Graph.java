package graph;

import lombok.Getter;
import task.Task;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class Graph extends LinkedHashMap<Task, ProcessBar> {


    public Task getKeyFromValue(ProcessBar value) {
        return entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(value))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ProcessBar processBar : this.values()) {
            sb.append(processBar).append("\n");
        }
        return sb.toString();
    }
}
