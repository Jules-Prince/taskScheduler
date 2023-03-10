package schduler;

import lombok.Getter;

import java.util.LinkedHashMap;

@Getter
public class ProcessBar extends LinkedHashMap<Integer, Boolean> {

    private final String name;

    public ProcessBar(String name) {
        this.name = name;
    }

    public Boolean isLastValueTrue() {
        if (this.isEmpty()) {
            return false; // Return a default value if the map is empty.
        }
        return this.get(this.size() - 1);
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
        int maxLength = this.getName().length() + 4;
        for (Integer tick : this.keySet()) {
            int tickLength = String.valueOf(tick).length();
            int hyphenLength = maxLength - tickLength;
            if (tick == 0)
                sb.append(0).append("-".repeat(hyphenLength));
            else if (tick % 5 == 0)
                sb.append(tick).append("-".repeat(this.getName().length() + 4 - String.valueOf(tick).length()));
            else if (tick == this.keySet().stream().mapToInt(v -> v).max().orElse(0))
                sb.append("-".repeat(this.getName().length() + 4 - String.valueOf(tick).length())).append(tick + 1);
            else {
                sb.append("-".repeat(maxLength));
            }
        }
        return sb.toString();
    }

    private String getTopLine() {
        StringBuilder sb = new StringBuilder();
        int lengthTaskString = this.getName().length() + 4;
        for (Boolean b : this.values()) {
            if (b)
                sb.append("_".repeat(lengthTaskString));
            else
                sb.append(" ".repeat(lengthTaskString));
        }
        return sb.toString();
    }


    private String getTaskLine() {
        StringBuilder sb = new StringBuilder();
        this.values().forEach((t) -> {
            if (!t)
                sb.append(" ".repeat(this.getName().length() + 4));
            else
                sb.append("| ").append(this.getName()).append(" |");
        });
        return sb.toString();
    }
}
