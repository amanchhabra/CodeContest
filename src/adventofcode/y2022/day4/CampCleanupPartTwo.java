package adventofcode.y2022.day4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class CampCleanupPartTwo {

    static Pattern inputPattern = Pattern.compile("^(\\d+)-(\\d+),(\\d+)-(\\d+)$");

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        System.out.println(input.stream().map(data ->inputPattern.matcher(data)).filter(data -> {
            data.find();
            return solve(Integer.parseInt(data.group(1)),Integer.parseInt(data.group(2)),Integer.parseInt(data.group(3)),Integer.parseInt(data.group(4)));

        }).count());
    }

    private static boolean solve(int start1, int end1, int start2, int end2) {
        if(start1 <= start2 && start2 <= end1) return true;
        if(start1 <= end2 && end2 <= end1) return true;
        if(start2 <= start1 && start1 <= end2) return true;
        if(start2 <= end1 && end1 <= end2) return true;
        return false;
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(CampCleanupPartTwo.class.getResource("input.txt")).toURI()));
    }
}
