package com.example.Day12;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PushDownSummingUnit implements SummingUnit {

    private static final String OBJECT = "}";
    private static final String ARRAY = "]";
    private static final String STRING_LITERAL = "STRING";
    private static final String NUMBER = "NUMBER";
    private static final String BOOL = "BOOL";

    private final Stack<String> opStack = new Stack<>();
    private final List<Integer> allNumbers = new ArrayList<>();

    private StringBuilder currentAtom = null;
    int counter = 0;

    @Override
    public List<Integer> getAllNumbers() {
        return allNumbers;
    }

    @Override
    public void accept(String ch) {

        switch (ch) {

            case "{" -> {
                if (stackIs(STRING_LITERAL)) {
                    currentAtom.append(ch);
                } else {
                    opStack.push(OBJECT);
                }
            }

            case "}" -> {
                if (stackIs(STRING_LITERAL)) {
                    currentAtom.append(ch);
                } else {
                    if (stackIs(NUMBER)) {
                        adjustStack();
                    }
                    if (stackIs(BOOL)) {
                        adjustStack();
                    }
                    if (stackIsNot(OBJECT)) throw new RuntimeException("'}' without '{' at " + counter);
                    opStack.pop();
                }
            }

            case "[" -> {
                if (stackIs(STRING_LITERAL)) {
                    currentAtom.append(ch);
                } else {
                    opStack.push(ARRAY);
                }
            }

            case "]" -> {
                if (stackIs(STRING_LITERAL)) {
                    currentAtom.append(ch);
                } else {
                    if (stackIs(NUMBER)) {
                        adjustStack();
                    }
                    if (stackIs(BOOL)) {
                        adjustStack();
                    }
                    if (stackIsNot(ARRAY)) throw new RuntimeException("']' without '[' at " + counter);
                    opStack.pop();
                }
            }

            case "\"" -> {
                if (stackIs(STRING_LITERAL)) {
                    currentAtom.append("'");
                    adjustStack();
                } else {
                    opStack.push(STRING_LITERAL);
                    currentAtom = new StringBuilder("'");
                }
            }

            case ",", ":", " " -> {
                if (stackIs(STRING_LITERAL)) {
                    currentAtom.append(ch);
                } else {
                    if (stackIs(NUMBER)) {
                        adjustStack();
                    }
                    if (stackIs(BOOL)) {
                        adjustStack();
                    }
                }
            }

            case "-", "+", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                if (stackIs(STRING_LITERAL)) {
                    currentAtom.append(ch);
                } else {
                    if (stackIsNot(NUMBER)) {
                        opStack.push(NUMBER);
                        currentAtom = new StringBuilder(ch);
                    } else if (stackIs(NUMBER)) {
                        currentAtom.append(ch);
                    } else {
                        throw new RuntimeException("Unexpected token type: '" + opStack.peek() + "' at " + counter);
                    }
                }
            }

            case "t", "r", "u", "f", "a", "l", "s", "e" -> {
                if (stackIs(STRING_LITERAL)) {
                    currentAtom.append(ch);
                } else {
                    if (stackIsNot(BOOL) && (ch.equals("f") || ch.equals("t"))) {
                        opStack.push(BOOL);
                        currentAtom = new StringBuilder(ch);
                    } else if (stackIs(BOOL)) {
                        currentAtom.append(ch);
                    } else {
                        throw new RuntimeException("Unexpected token type: '" + opStack.peek() + "' at " + counter);
                    }
                }
            }

            default -> {
                if (stackIs(STRING_LITERAL)) {
                    currentAtom.append(ch);
                } else {
                    throw new RuntimeException("Oops! Char '" + ch + "' at " + counter);
                }
            }
        }

        counter += 1;
    }

    private void adjustStack() {

        if (opStack.peek().equals(NUMBER)) {
            var num = Integer.parseInt(currentAtom.toString());
            allNumbers.add(num);
        }
        opStack.pop();
        currentAtom = null;
    }

    private boolean stackIsNot(String ch) {
        return opStack.isEmpty() || !opStack.peek().equals(ch);
    }

    private boolean stackIs(String ch) {
        return !opStack.isEmpty() && opStack.peek().equals(ch);
    }
}
