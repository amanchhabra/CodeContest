package adventofcode.y2022.day9;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RopeBridge {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        Set<String> pos = new HashSet<>();
        pos.add("0_0");
        int h_x = 0;
        int h_y = 0;
        int t_x = 0;
        int t_y = 0;
        for(String inputString: input) {
            switch (inputString.charAt(0)) {
                case 'R': for(int i=0;i<Integer.parseInt(inputString.substring(2));i++) {
                    h_x++;
                    if(checkConnect(h_x, h_y, t_x, t_y))
                        continue;
                    if(h_y==t_y) {t_x++;} else {t_y=h_y;t_x++;}
                    pos.add(t_x+"_"+t_y);
                }
                break;
                case 'D': for(int i=0;i<Integer.parseInt(inputString.substring(2));i++) {
                    h_y--;
                    if(checkConnect(h_x, h_y, t_x, t_y))
                        continue;
                    if(h_x==t_x) {t_y--;} else {t_x=h_x;t_y--;}
                    pos.add(t_x+"_"+t_y);
                }
                break;
                case 'U':  for(int i=0;i<Integer.parseInt(inputString.substring(2));i++) {
                    h_y++;
                    if(checkConnect(h_x, h_y, t_x, t_y))
                        continue;
                    if(h_x==t_x) {t_y++;} else {t_x=h_x;t_y++;}
                    pos.add(t_x+"_"+t_y);
                }
                    break;
                case 'L': for(int i=0;i<Integer.parseInt(inputString.substring(2));i++) {
                    h_x--;
                    if(checkConnect(h_x, h_y, t_x, t_y))
                        continue;
                    if(h_y==t_y) {t_x--;} else {t_y=h_y;t_x--;}
                    pos.add(t_x+"_"+t_y);
                }
                break;
            }
        }
        System.out.println(pos.size());
    }

    private static boolean checkConnect(int h_x, int h_y, int t_x, int t_y) {
        return h_y == t_y && (Math.abs(h_x - t_x) < 2) || (h_x == t_x && (Math.abs(h_y - t_y) < 2)) || (Math.abs(h_x - t_x) == 1 && (Math.abs(h_y - t_y) == 1));
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(RopeBridge.class.getResource("input.txt")).toURI()));
    }
}
