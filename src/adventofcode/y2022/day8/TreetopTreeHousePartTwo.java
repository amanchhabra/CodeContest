package adventofcode.y2022.day8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class TreetopTreeHousePartTwo {

    static int[][] data;
    public static void main(String[] args) throws IOException, URISyntaxException {

        List<String> input = readInput();
        data = new int[input.get(0).length()][input.get(0).length()];
        int count=0;
        for(String inputString: input) {
            for(int j=0;j<inputString.length();j++)
            data[count][j] = Integer.parseInt(Character.toString(inputString.charAt(j)));
            count++;
        }

        int visible = 0;
        for(int i=0;i<data.length;i++){
            for(int j=0;j<data.length;j++) {
                visible = Math.max(visible, checkVisible(i,j));
            }
        }
        System.out.println(visible);

    }

    private static int checkVisible(int i, int j) {
        int height = data[i][j];
        int minHeight = -2;
        int view = 1;
        int t = i;
        int curView = 0;
        while (minHeight !=-1 && minHeight < height) {
            minHeight = getHeight(--t,j);
            if(minHeight!=-1)
            curView++;
        }
        view = view*curView;

        minHeight = -2;
        curView = 0;
        t = j;
        while (minHeight !=-1 && minHeight < height) {
            minHeight = getHeight(i,++t);
            if(minHeight!=-1)
                curView++;
        }
        view = view*curView;

        minHeight = -2;
        curView = 0;
        t = i;
        while (minHeight !=-1 && minHeight < height) {
            minHeight = getHeight(++t,j);
            if(minHeight!=-1)
                curView++;
        }
        view = view*curView;

        minHeight = -2;
        curView = 0;
        t = j;
        while (minHeight !=-1 && minHeight < height) {
            minHeight = getHeight(i,--t);
            if(minHeight!=-1)
                curView++;
        }
        view = view*curView;

        return view;
    }

    private static int getHeight(int i, int j) {
        if(i<0||i>=data.length||j<0||j>=data.length) return -1;
        return data[i][j];
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(TreetopTreeHousePartTwo.class.getResource("input.txt")).toURI()));
    }
}
