package com.example.Day12;

import com.example.Helpers.Storage;
import lombok.ToString;

public class AbacusFramework {

    private static boolean noRed = false;

    public static void main(String[] args) {

        var storage = new Storage("/peculiar_storage.json");

        noRed = false;
        System.out.println("part 1 solution = " + recursiveBalance(storage.getMemory()));

        noRed = true;
        System.out.println("part 2 solution = " + recursiveBalance(storage.getMemory()));
    }

    private static int recursiveBalance(String text) {

        var pod = new Pod(0, 0);

        while (pod.index < text.length()) {
            pod = switch (text.charAt(pod.index)) {
                case ' ', '\n', '\r', '\t' -> skipWhitespace(text, pod);
                case '{' -> evaluateObject(text, pod);
                case '[' -> evaluateArray(text, pod);
                case '"' -> evaluateString(text, pod);
                case '-', '+', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> evaluateNumber(text, pod);
                case 'f' -> evaluateFalse(text, pod);
                case 't' -> evaluateTrue(text, pod);
                case 'n' -> evaluateNull(text, pod);
                default -> throw new RuntimeException(
                        "unexpected character '" + text.charAt(pod.index) + "' at " + pod.index);
            };
        }

        return pod.balance;
    }

    /**
     * An object is a comma-separated list of key:value pairs, where keys are strings and values are any
     * valid JSON element.
     */
    private static Pod evaluateObject(String text, Pod pod) {

        var index = pod.index + 1;
        var balance = pod.balance;
        pod = new Pod(index, 0);

        var isValue = false;
        var isRed = false;

        Character ch = null;
        while (index < text.length() && (ch = text.charAt(index)) != '}') {
            pod = switch (ch) {
                case ' ', '\n', '\r', '\t' -> skipWhitespace(text, new Pod(index, pod.balance));
                case '{' -> evaluateObject(text, new Pod(index, pod.balance));
                case '[' -> evaluateArray(text, new Pod(index, pod.balance));
                case '"' -> {
                    var p = evaluateString(text, new Pod(index, pod.balance));
                    isRed = isRed || p.isRed;
                    yield p;
                }
                case '-', '+', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
                        evaluateNumber(text, new Pod(index, pod.balance));
                case 'f' -> evaluateFalse(text, new Pod(index, pod.balance));
                case 't' -> evaluateTrue(text, new Pod(index, pod.balance));
                case 'n' -> evaluateNull(text, new Pod(index, pod.balance));
                case ',' -> {
                    isValue = false;
                    yield new Pod(pod.index + 1, pod.balance);
                }
                case ':' -> {
                    if (isValue) throw new RuntimeException("object: key without value");
                    isValue = true;
                    yield new Pod(pod.index + 1, pod.balance);
                }
                default -> throw new RuntimeException("object: unexpected character: '" + ch + "'");
            };
            index = pod.index;
        }

        if (ch != null && ch != '}') throw new RuntimeException("object: unexpected end of text");

        balance += (noRed && isRed) ? 0 : pod.balance;

        return new Pod(index + 1, balance, false);
    }

    /**
     * An array is a list of JSON objects separated by commas.
     */
    private static Pod evaluateArray(String text, Pod pod) {

        var index = pod.index + 1;
        pod = new Pod(index, pod.balance);

        Character ch = null;
        while (index < text.length() && (ch = text.charAt(index)) != ']') {
            pod = switch (ch) {
                case ' ', '\n', '\r', '\t' -> skipWhitespace(text, new Pod(index, pod.balance));
                case '{' -> evaluateObject(text, new Pod(index, pod.balance));
                case '[' -> evaluateArray(text, new Pod(index, pod.balance));
                case '"' -> evaluateString(text, new Pod(index, pod.balance));
                case '-', '+', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
                        evaluateNumber(text, new Pod(index, pod.balance));
                case 'f' -> evaluateFalse(text, new Pod(index, pod.balance));
                case 't' -> evaluateTrue(text, new Pod(index, pod.balance));
                case 'n' -> evaluateNull(text, new Pod(index, pod.balance));
                case ',' -> new Pod(pod.index + 1, pod.balance);
                default -> throw new RuntimeException("array: unexpected character: '" + ch + "'");
            };
            index = pod.index;
        }

        if (ch != null && ch != ']') throw new RuntimeException("array: unexpected end of text");

        return new Pod(index + 1, pod.balance);
    }

    private static Pod evaluateTrue(String text, Pod pod) {
        return skipString(text, pod, "true");
    }

    private static Pod evaluateFalse(String text, Pod pod) {
        return skipString(text, pod, "false");
    }

    private static Pod evaluateNull(String text, Pod pod) {
        return skipString(text, pod, "null");
    }

    private static Pod skipString(String text, Pod pod, String literal) {

        var index = pod.index;
        var len = literal.length();
        var sub = text.substring(index, index + len);

        if (!literal.equals(sub)) throw new RuntimeException("unknown literal '" + sub + "'");

        return new Pod(index + len, pod.balance);
    }

    private static Pod evaluateNumber(String text, Pod pod) {

        var index = pod.index;
        var balance = pod.balance;
        var literal = new StringBuilder();
        var len = text.length();

        Character ch = text.charAt(index);

        if (ch == '+' || ch == '-') {
            literal.append(ch);
            index += 1;
        }

        while (index < len && (Character.isDigit(ch = text.charAt(index)))) {
            literal.append(ch);
            index += 1;
        }

        try {
            balance += Integer.parseInt(literal.toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e.getMessage());
        }

        return new Pod(index, balance);
    }

    private static Pod evaluateString(String text, Pod pod) {

        var index = pod.index + 1;
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

    private static Pod skipWhitespace(String text, Pod pod) {

        var index = pod.index;
        var balance = pod.balance;

        while (index < text.length() && Character.isWhitespace(text.charAt(index))) index += 1;

        return new Pod(index, balance);
    }

    @ToString
    private static class Pod {

        int index;
        int balance;
        boolean isRed;

        Pod(int index, int balance) {
            this(index, balance, false);
        }

        Pod(int index, int balance, boolean isRed) {
            this.index = index;
            this.balance = balance;
            this.isRed = isRed;
        }
    }
}
