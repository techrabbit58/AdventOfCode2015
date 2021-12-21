package com.example.Day18;

public record GridElement(int row, int column) {

    public static final char IS_ON = '#';
    public static final int[] neighbours = new int[]{
            -1, -1, // northwest
            -1, 0,  // north
            -1, 1,  // northeast
            0, 1,   // east
            1, 1,   // southeast
            1, 0,   // south
            1, -1,  // southwest
            0, -1   // west
    };

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }

    public NeighbourIterator neighbourIterator() {

        return new NeighbourIterator() {

            int _cursor = 0;

            public boolean hasNext() {
                return _cursor < neighbours.length;
            }

            public GridElement next() {
                var cursor = _cursor;
                _cursor += 2;
                return new GridElement(row + neighbours[cursor], column + neighbours[cursor + 1]);
            }
        };
    }
}
