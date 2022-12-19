package adventofcode.y2022.day10;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class CathodeRayTube {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        int counter = 0;
        int signal = 0;
        int x = 1;
        for(String inputString: input) {
            if(inputString.startsWith("noop"))
                counter++;
            else if (inputString.startsWith("addx")) {
                counter++;
            }
            if(counter == 20 || (counter-20)%40==0 ) {
                signal += (counter * x);
            }
            if (inputString.startsWith("addx")) {
                counter++;
                if(counter == 20 || (counter-20)%40==0 ) {
                    signal += (counter * x);
                }
                x+=Integer.parseInt(inputString.substring(5));
            }
        }
        System.out.println(signal);

    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(CathodeRayTube.class.getResource("input.txt")).toURI()));
    }
}
