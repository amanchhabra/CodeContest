package adventofcode.y2022.day15;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.lang.Math.*;

class BeaconExclusionZonePartTwo {
    public static final int MAX_VALUE = 4000000;
    static Pattern INPUT_PATTERN = Pattern.compile("^Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)$");
    static int MULTIPLYING_FACTOR = 4000000;

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> lines = readInput();
        int max_x = 0;
        int max_y = 0;
        List<Input> inputs = lines
                .stream()
                .map(data -> INPUT_PATTERN.matcher(data))
                .map(data -> {
                    data.find();
                    return new Input(new String[]{data.group(1), data.group(2), data.group(3), data.group(4)});
                }).toList();

        for (Input input : inputs) {
            max_x = max(max_x, input.s_x);
            max_y = max(max_y, input.s_y);
        }

        int final_max_x = min(max_x, MAX_VALUE);
        int final_max_y = min(max_y, MAX_VALUE);

        IntStream.range(0, final_max_y + 1).forEach(
                y -> {
                    List<int[]> collect = inputs.stream().filter(data -> (data.s_y + data.diff()) >= y)
                            .filter(data -> (data.s_y - data.diff()) <= y)
                            .map(data -> data.findAllAt(y, final_max_x))
                            .sorted(Comparator.comparingInt(o -> o[0])).toList();
                    int[] finalRange = null;
                    for (int[] range : collect) {
                        if (finalRange == null)
                            finalRange = range;
                        else if (range[0] >= finalRange[0] & range[0] <= finalRange[1] + 1) {
                            if (finalRange[1] < range[1]) finalRange[1] = range[1];
                        } else {
                            if (finalRange[0] != 0)
                                System.out.println(y);
                            else
                                System.out.println((long) (finalRange[1] + 1) * (long) MULTIPLYING_FACTOR + (long) y);
                        }
                    }
                });
    }


    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(BeaconExclusionZonePartTwo.class.getResource("input.txt")).toURI()));
    }

    private static class Input {
        int s_x;
        int s_y;
        int b_x;
        int b_y;

        public Input(String[] data) {
            this.s_x = Integer.parseInt(data[0]);
            this.s_y = Integer.parseInt(data[1]);
            this.b_x = Integer.parseInt(data[2]);
            this.b_y = Integer.parseInt(data[3]);
        }

        public int diff() {
            return diff(s_x, s_y, b_x, b_y);
        }

        private int diff(int x1, int y1, int x2, int y2) {
            return abs(x1 - x2) + abs(y1 - y2);
        }

        public int[] findAllAt(int y, int maxValue) {
            int diff = diff() - diff(s_x, s_y, s_x, y);
            int startRange = max(s_x - diff, 0);
            int endRange = s_x + diff + 1 < maxValue ? s_x + diff : maxValue;
            return new int[]{startRange, endRange};
        }
    }
}