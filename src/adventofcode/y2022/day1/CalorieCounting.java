package adventofcode.y2022.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class CalorieCounting {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        List<Integer> calories = new ArrayList<>();
        int curCalorie=0;
        for (String calorie: input) {
            if(calorie.isBlank()) {
                calories.add(curCalorie);
                curCalorie = 0;
            } else {
                curCalorie+=Integer.parseInt(calorie);
            }
        }
        if(curCalorie!=0)
            calories.add(curCalorie);
        System.out.println(calories.stream().sorted(Comparator.reverseOrder()).findFirst().get());

    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(CalorieCounting.class.getResource("input.txt")).toURI()));
    }
}
