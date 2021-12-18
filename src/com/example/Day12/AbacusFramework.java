package com.example.Day12;

public class AbacusFramework {

    public static void main(String[] args) {

        var storage = new Storage("/peculiar_storage.json");
        var summingUnit = new PushDownSummingUnit();
        int result;

        for (var ch : storage) summingUnit.accept(ch);

        result = summingUnit.getAllNumbers().stream().reduce(Integer::sum).orElse(0);
        System.out.println("part 1 solution = " + result);


        result = recursiveBalanceNoRed(storage.getPeculiarStorage());
        System.out.println("part 2 solution = " + result);

        System.out.println(
                "skip five blanks: " + skipBlanks("     ", new Pod(0, 0, false), 0)
        );
        System.out.println(
                "recognize a string: " + evaluateString(
                        "\"foo +1234:,true\"", new Pod(0, 0, false), 1
                )
        );
        System.out.println(
                "recognize 'red': " + evaluateString("\"red\"", new Pod(0, 0, false), 1)
        );
        System.out.println(
                "recognize a positive number and change balance: " + evaluateNumber(
                        "123", new Pod(0, 77, false), 0
                )
        );
        System.out.println(
                "recognize a negative number and change balance: " + evaluateNumber(
                        "-123", new Pod(0, 323, false), 0
                )
        );
        System.out.println(
                "recognize a signed number and change balance: " + evaluateNumber(
                        "+123", new Pod(0, 77, false), 0
                )
        );
        System.out.println(
                "'red' context with number does not change balance: " + evaluateNumber(
                        "123", new Pod(0, 77, true), 0
                )
        );
        System.out.println("can handle 'null' values: " +
                evaluateNull("null", new Pod(0, 0, false), 0)
        );
        System.out.println("can handle 'true' values: " +
                evaluateTrue("true", new Pod(0, 0, false), 0)
        );
        System.out.println("can handle 'false' values: " +
                evaluateFalse("false", new Pod(0, 0, false), 0)
        );
    }

    private static int recursiveBalanceNoRed(String text) {

        var pod = new Pod(0, 0, false);

        while (pod.index < text.length()) {
            pod = switch (text.charAt(pod.index)) {
                case ' ', '\n', '\r', '\t' -> skipBlanks(text, pod, 0);
                case '{' -> evaluateObject(text, pod, 1);
                case '[' -> evaluateArray(text, pod, 1);
                case '"' -> evaluateString(text, pod, 1);
                case '-', '+', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> evaluateNumber(text, pod, 0);
                case 'f' -> evaluateFalse(text, pod, 0);
                case 't' -> evaluateTrue(text, pod, 0);
                case 'n' -> evaluateNull(text, pod, 0);
                default -> throw new RuntimeException(
                        "unexpected character '" + text.charAt(pod.index) + "' at " + pod.index);
            };
        }

        return pod.balance;
    }

    private static Pod evaluateObject(String text, Pod pod, int bias) {

        var index = pod.index + bias;
        var balance = pod.balance;

        Character ch = null;
        while (index < text.length() && (ch = text.charAt(index)) != '}') {
            if (ch == '{') {
                pod = evaluateObject(text, new Pod(index, balance,false), 1);
                index = pod.index;
                balance = pod.balance;
            } else {
                index += 1;
            }
        }

        if (ch != '}') throw new RuntimeException("object: unexpected end of text");

        return new Pod(index + 1, balance, pod.isRed);
    }

    /**
     * An array is a list of JSON objects separated by commas.
     */
    private static Pod evaluateArray(String text, Pod pod, int bias) {

        return new Pod(pod.index + bias, pod.balance, pod.isRed);
    }

    private static Pod evaluateTrue(String text, Pod pod, int bias) {
        return skipString(text, pod, bias, "true");
    }

    private static Pod evaluateFalse(String text, Pod pod, int bias) {
        return skipString(text, pod, bias, "false");
    }

    private static Pod evaluateNull(String text, Pod pod, int bias) {
        return skipString(text, pod, bias, "null");
    }

    private static Pod skipString(String text, Pod pod, int bias, String literal) {

        var index = pod.index + bias;
        var len = literal.length();
        var sub = text.substring(index, index + len);

        if (!literal.equals(sub)) throw new RuntimeException("unknown literal '" + sub + "'");

        return new Pod(index + len, pod.balance, pod.isRed);
    }

    private static Pod evaluateNumber(String text, Pod pod, int bias) {

        var index = pod.index + bias;
        var balance = pod.balance;
        var literal = new StringBuilder();
        var len = text.length();

        Character ch = (index < len) ? text.charAt(index) : '0';

        if (index < len && (ch == '+' || ch == '-')) {
            literal.append(ch);
            index += 1;
        }

        while (index < len && (Character.isDigit(ch = text.charAt(index)))) {
            literal.append(ch);
            index += 1;
        }

        try {
            balance += pod.isRed ? 0 : Integer.parseInt(literal.toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e.getMessage());
        }

        return new Pod(index, balance, false);
    }

    private static Pod evaluateString(String text, Pod pod, int bias) {

        var index = pod.index + bias;
        var balance = pod.balance;
        var literal = new StringBuilder();
        var len = text.length();

        Character ch;
        while (index < len && (ch = text.charAt(index)) != '"') {
            literal.append(ch);
            index += 1;
        }

        if (index >= len) throw new RuntimeException("string: unexpected end of text");

        return new Pod(index + 1, balance, literal.toString().equals("red"));
    }

    private static Pod skipBlanks(String text, Pod pod, int bias) {

        var index = pod.index + bias;
        var balance = pod.balance;

        while (index < text.length() && Character.isWhitespace(text.charAt(index))) index += 1;

        return new Pod(index, balance, false);
    }

    private record Pod(int index, int balance, boolean isRed) {
    }
}
