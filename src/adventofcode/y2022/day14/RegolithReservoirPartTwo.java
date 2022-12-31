package adventofcode.y2022.day14;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegolithReservoirPartTwo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        List<Wall> walls = new ArrayList<>();
        input.forEach(inputString -> {
            String oldPoint = null;
          for(String point: inputString.split(" -> ")) {
              if (oldPoint==null)
                  oldPoint=point;
              else {
                  walls.add(new Wall(Integer.parseInt(oldPoint.split(",")[0]),
                          Integer.parseInt(oldPoint.split(",")[1]),
                          Integer.parseInt(point.split(",")[0]),
                          Integer.parseInt(point.split(",")[1])));
                  oldPoint = point;
              }
          }
        });
        Wall.max_y += 2;
        walls.add(new Wall(0, Wall.max_y, 100000, Wall.max_y));


        List<String> points = new ArrayList<>();
        int point_x = 500;
        int point_y = 0;
        while(true) {
            if(point_y >= Wall.max_y)
                break;
            boolean isBlockedDown = isBlocked(walls, points, point_x, point_y+1);
            boolean isBlockedLeft = isBlocked(walls, points, point_x-1, point_y+1);
            boolean isBlockedRight = isBlocked(walls, points, point_x+1, point_y+1);
            if(isBlockedDown && isBlockedLeft && isBlockedRight) {
                    points.add(point_x+"_"+point_y);
                    if(point_x == 500 && point_y == 0)
                        break;
                    point_y = 0;
                    point_x = 500;
                    continue;
            } else if(isBlockedDown) {
                point_x = isBlockedLeft ? point_x+1: point_x-1;
            }
            point_y++;
        }
        System.out.println(points.size());
    }

    private static boolean isBlocked(List<Wall> walls, List<String> points, int point_x, int point_y) {
        return walls.stream().filter(wall -> wall.intersectWithWall(point_x, point_y)).findAny().isPresent() || points.contains(point_x + "_" + point_y);
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(RegolithReservoirPartTwo.class.getResource("input.txt")).toURI()));
    }

    static class Wall {
        int point1_x;
        int point1_y;
        int point2_x;
        int point2_y;
        static int max_y;
        static int min_x;
        static int max_x;
        boolean verticalWall = false;

        public Wall(int point1_x, int point1_y, int point2_x, int point2_y) {
            this.point1_x = point1_x;
            this.point1_y = point1_y;
            this.point2_x = point2_x;
            this.point2_y = point2_y;
            max_y = Math.max(max_y, point1_y);
            max_y = Math.max(max_y, point2_y);
            min_x = Math.min(min_x, point1_x);
            min_x = Math.min(min_x, point2_x);
            max_x = Math.max(max_x, point1_x);
            max_x = Math.max(max_x, point1_x);
            verticalWall = point1_x == point2_x;
        }

        public boolean intersectWithWall(int x, int y) {
            if(verticalWall) {
                return x == point1_x && (point1_y > point2_y ? (y>=point2_y && y<= point1_y) : (y>=point1_y && y<= point2_y));
            } else {
                return y == point1_y && (point1_x > point2_x ? (x>=point2_x && x<= point1_x) : (x>=point1_x && x<= point2_x));
            }
        }
    }
}
