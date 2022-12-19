package adventofcode.y2022.day11;

import java.util.*;
import java.util.stream.Collectors;

public class MonkeyInTheMiddle {

    public static void main(String[] args) {
        List<Monkey> monkeys = getActualData();
        int t=20;
        while (t-->0)
            monkeys.forEach(monkey -> monkey.throwItems(monkeys));
        List<Integer> inspectList = monkeys.stream().map(monkey -> monkey.inspectTimes).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(inspectList.get(0)*inspectList.get(1));

    }

    private static List<Monkey> getSampleData() {
        List<Monkey> monkeys = new ArrayList<>();

        Monkey monkey = new Monkey(false, 19,23,2,3,79,98);
        monkeys.add(monkey);

        monkey = new Monkey(true, 6,19,2,0,54,65,75,74);
        monkeys.add(monkey);

        monkey = new Monkey(false, -1,13,1,3,79,60,97);
        monkeys.add(monkey);

        monkey = new Monkey(true, 3,17,0,1,74);
        monkeys.add(monkey);

        return monkeys;
    }

    private static List<Monkey> getActualData() {
        List<Monkey> monkeys = new ArrayList<>();

        Monkey monkey = new Monkey(false, 17,13,6,7, 52, 60, 85, 69, 75, 75);
        monkeys.add(monkey);

        monkey = new Monkey(true, 8,7,0,7, 96, 82, 61, 99, 82, 84, 85);
        monkeys.add(monkey);

        monkey = new Monkey(true, 6,19,5,3, 95, 79);
        monkeys.add(monkey);

        monkey = new Monkey(false, 19,2,4,1, 88, 50, 82, 65, 77);
        monkeys.add(monkey);

        monkey = new Monkey(true, 7,5,1,0, 66, 90, 59, 90, 87, 63, 53, 88);
        monkeys.add(monkey);

        monkey = new Monkey(false, -1,3,3,4, 92, 75, 62);
        monkeys.add(monkey);

        monkey = new Monkey(true, 1,11,5,2,94, 86, 76, 67);
        monkeys.add(monkey);

        monkey = new Monkey(true, 2,17,6,2,57);
        monkeys.add(monkey);


        return monkeys;
    }

    static class Monkey {
        Queue<Integer> items = new ArrayDeque<>();
        boolean isSum;
        int sumMultiplyNumber;
        int testNumber;
        int onTrue;
        int onFalse;
        int inspectTimes=0;

        public Monkey(boolean isSum, int sumMultiplyNumber, int testNumber, int onTrue, int onFalse, Integer... items) {
            this.isSum = isSum;
            this.sumMultiplyNumber = sumMultiplyNumber;
            this.testNumber = testNumber;
            this.onTrue = onTrue;
            this.onFalse = onFalse;
            for (Integer item : items)
                this.items.add(item);
        }

        public void throwItems(List<Monkey> monkeys) {
            while(!this.items.isEmpty()) {
                int worryLevel = this.items.poll();
                inspectTimes++;
                worryLevel = isSum ? worryLevel + (sumMultiplyNumber == -1 ? worryLevel : sumMultiplyNumber) : worryLevel * (sumMultiplyNumber == -1 ? worryLevel : sumMultiplyNumber);
                worryLevel = worryLevel / 3;
                Monkey destinationMonkey = worryLevel % testNumber != 0 ? monkeys.get(onFalse) : monkeys.get(onTrue);
                destinationMonkey.items.add(worryLevel);
            }
        }
    }
}
