package com.example.Day18;

public record Light(int row, int column) {

    public static final char IS_ON = '#';
    public static final char IS_OFF = '.';

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }
}
