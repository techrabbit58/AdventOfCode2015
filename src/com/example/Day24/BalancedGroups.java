package com.example.Day24;

import com.example.Helpers.Assertions;
import com.example.Helpers.Miscellaneous;
import com.example.Helpers.Parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

import static com.example.Helpers.Mappers.intArraySum;
import static com.example.Helpers.Validations.isIntArrayDifferent;

public class BalancedGroups {

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 24: It Hangs in the Balance");

        List<Integer> input;
        int[] weights;
        int totalWeight;

        // T E S T

        input = Parsers.resourceToIntegerList("/weighted_things_for_test.txt");
        weights = input.stream().sorted(Comparator.reverseOrder()).mapToInt(i -> i).toArray();
        totalWeight = intArraySum(weights);

        Assertions.assertEquals(99L,
                solution(weights, totalWeight, 3, BalancedGroups::canCombineThree));
        Assertions.assertEquals(44L,
                solution(weights, totalWeight, 4, BalancedGroups::canCombineFour));

        // P U Z Z L E

        input = Parsers.resourceToIntegerList("/weighted_things.txt");
        weights = input.stream().sorted(Comparator.reverseOrder()).mapToInt(i -> i).toArray();
        totalWeight = intArraySum(weights);

        System.out.println("part 1 solution: "
                + solution(weights, totalWeight, 3, BalancedGroups::canCombineThree));

        System.out.println("part 2 solution: "
                + solution(weights, totalWeight, 4, BalancedGroups::canCombineFour));
    }

    private static long solution(
            int[] weights, int totalWeight, int numGroups, BiFunction<int[], List<int[]>, Boolean> canCombine) {

        var allGroupsOfFour = getAllDistinctGroups(weights, totalWeight / numGroups);

        var minGroupLengthOfFour = allGroupsOfFour.stream()
                .map(a -> a.length).reduce(Integer::min).orElse(-1);

        var combinableFirstGroupsOfFour = getCombinableGroups(
                allGroupsOfFour, minGroupLengthOfFour, canCombine);

        return getMinQE(combinableFirstGroupsOfFour);
    }

    private static long getMinQE(List<int[]> combinableFirstGroups) {
        var minQE = Long.MAX_VALUE;
        for (int[] a : combinableFirstGroups) {
            var qe = quantumEntanglement(a);
            minQE = Math.min(qe, minQE);
        }
        return minQE;
    }

    private static List<int[]> getCombinableGroups(
            List<int[]> allGroups, Integer minGroupLength, BiFunction<int[], List<int[]>, Boolean> canCombine) {
        return allGroups.stream()
                .filter(group -> group.length == minGroupLength)
                .filter(group -> canCombine.apply(group, allGroups))
                .sorted(Comparator.comparingInt(a -> a.length))
                .toList();
    }

    private static long quantumEntanglement(int[] a) {

        var qe = 1L;
        for (var i : a) qe *= i;
        return qe;
    }

    private static Boolean canCombineThree(int[] group, List<int[]> allGroups) {

        for (var a = 0; a < allGroups.size(); a++) {
            if (isIntArrayDifferent(allGroups.get(a), group)) {
                for (var b = a + 1; b < allGroups.size(); b++) {
                    if (isIntArrayDifferent(allGroups.get(b), group)
                            && isIntArrayDifferent(allGroups.get(a), allGroups.get(b))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean canCombineFour(int[] group, List<int[]> allGroups) {

        for (var a = 0; a < allGroups.size(); a++) {
            if (isIntArrayDifferent(allGroups.get(a), group)) {
                for (var b = a + 1; b < allGroups.size(); b++) {
                    if (isIntArrayDifferent(allGroups.get(b), group)
                            && isIntArrayDifferent(allGroups.get(a), allGroups.get(b))) {
                        for (var c = b + 1; c < allGroups.size(); c++) {
                            if (isIntArrayDifferent(allGroups.get(c), group)
                                    && isIntArrayDifferent(allGroups.get(a), allGroups.get(c))
                                    && isIntArrayDifferent(allGroups.get(b), allGroups.get(c))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static List<int[]> getAllDistinctGroups(int[] weights, int groupWeight) {

        List<int[]> groups = new ArrayList<>();

        groupDfs(new int[]{}, Arrays.copyOf(weights, weights.length), groupWeight, groups);

        return groups;
    }

    private static void groupDfs(int[] groupSoFar, int[] candidates, int groupWeight, List<int[]> groups) {

        var currentWeight = intArraySum(groupSoFar);

        if (currentWeight >= groupWeight) {
            if (currentWeight == groupWeight) {
                groups.add(groupSoFar);
            }
            return;
        }

        for (var i = 0; i < candidates.length; i++) {
            var head = candidates[i];
            var length = groupSoFar.length;
            var newGroupSoFar = Arrays.copyOf(groupSoFar, length + 1);
            newGroupSoFar[length] = head;
            groupDfs(newGroupSoFar, Arrays.copyOfRange(candidates, i + 1, candidates.length), groupWeight, groups);
        }
    }
}
