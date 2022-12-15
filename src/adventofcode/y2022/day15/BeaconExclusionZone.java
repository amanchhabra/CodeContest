package adventofcode.y2022.day15;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BeaconExclusionZone {
    static Pattern inputPattern = Pattern.compile("^Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)$");

    static int y = 2000000;
    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> lines = readInput();
        Set<Integer> integerSet = new HashSet<>();
            lines
                .stream()
                .map(data ->inputPattern.matcher(data))
                .map(data -> {data.find(); return new Input(new String[]{data.group(1),data.group(2),data.group(3),data.group(4)});})
                .filter(data -> (data.s_y+data.diff())>=y)
                .filter(data -> (data.s_y-data.diff())<=y)
                .forEach(data -> integerSet.addAll(data.findAllAt(y)));
        System.out.println(integerSet.size());
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(BeaconExclusionZone.class.getResource("input.txt")).toURI()));
    }

    private static class Input{
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

        private int diff(int x1, int y1,  int x2, int y2) {
            return Math.abs(x1 - x2) + Math.abs(y1 - y2);
        }

        public Collection<Integer> findAllAt(int y) {
            int diff = diff() - diff(s_x, s_y,s_x,y);
            return IntStream.range(s_x-diff, s_x+diff).boxed().collect(Collectors.toList());
        }
    }
}