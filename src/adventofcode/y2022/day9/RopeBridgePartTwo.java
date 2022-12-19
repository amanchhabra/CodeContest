package adventofcode.y2022.day9;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RopeBridgePartTwo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        Set<String> pos = new HashSet<>();
        pos.add("2000_2000");
        int[][] rope = {{2000,2000},{2000,2000},{2000,2000},{2000,2000},{2000,2000},{2000,2000},{2000,2000},{2000,2000},{2000,2000},{2000,2000}};
        for(String inputString: input) {
            for(int j=0;j<Integer.parseInt(inputString.substring(2));j++) {
                switch (inputString.charAt(0)) {
                    case 'R':
                        rope[0][0]++;
                        for (int n = 0; n < 9; n++) {

                        }
                        break;
                    case 'D':
                        rope[0][1]--;
                        break;
                    case 'U':
                        rope[0][1]++;

                        break;
                    case 'L':
                        rope[0][0]--;

                        break;
                }
                for (int n = 0; n < 9; n++) {
                    int[] newPos = getNewEndpoint(rope[n][0],rope[n][1], rope[n+1][0], rope[n+1][1]);
                    rope[n+1][0]= newPos[0];
                    rope[n+1][1]= newPos[1];
                }
                pos.add(rope[9][0] + "_" + rope[9][1]);
            }
        }
        System.out.println(pos.size());
    }

    private static int[] getNewEndpoint(int a1, int a2, int c1, int c2) {
        if(a1==c1 && a2==c2); // Same Position
        if(Math.abs(c2-a2)<=1&&Math.abs(c1-a1)==1); // Connecting possition
        if(Math.abs(c1-a1)<=1&&Math.abs(c2-a2)==1); // Connecting possition
        if(Math.abs(c2-a2)==2&&Math.abs(c1-a1)==0) {
            c2=a2>c2?a2-1:a2+1;
        }
        if(Math.abs(c1-a1)==2&&Math.abs(c2-a2)==0) {
            c1=a1>c1?a1-1:a1+1;
        }
        if(Math.abs(c2-a2)==2&&Math.abs(c1-a1)==1) {
            c1=a1;
            c2=a2>c2?a2-1:a2+1;
        }
        if(Math.abs(c1-a1)==2&&Math.abs(c2-a2)==1) {
            c2=a2;
            c1=a1>c1?a1-1:a1+1;
        }
        if(Math.abs(c1-a1)==2&&Math.abs(c2-a2)==2) {
            c1=a1>c1?a1-1:a1+1;
            c2=a2>c2?a2-1:a2+1;
        }
        return new int[]{c1,c2};
    }

    private static boolean checkConnect(int h_x, int h_y, int t_x, int t_y) {
        return h_y == t_y && (Math.abs(h_x - t_x) < 2) || (h_x == t_x && (Math.abs(h_y - t_y) < 2)) || (Math.abs(h_x - t_x) == 1 && (Math.abs(h_y - t_y) == 1));
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(RopeBridgePartTwo.class.getResource("input.txt")).toURI()));
    }
}
