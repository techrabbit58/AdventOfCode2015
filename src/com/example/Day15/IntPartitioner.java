package com.example.Day15;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IntPartitioner implements Iterator<List<Integer>> {

    public static void main(String[] args) {

        var p = new IntPartitioner(100, 2);
        while (p.hasNext()) System.out.println(p.next());
    }

    private boolean _terminated;
    private final int _number;
    private final int _numParts;
    private IntPartitioner _other = null;
    private int _currentPart;

    public IntPartitioner(int number, int numParts) {

        if (number < 0)
            throw new IllegalArgumentException("number must be positive or zero, was '" + number + "'");

        if (numParts < 1)
            throw new IllegalArgumentException("number of parts must be positive, was '" + number + "'");

        _number = number;
        _currentPart = _number;
        _numParts = numParts;
        _terminated = false;
    }

    @Override
    public boolean hasNext() {

        if (_terminated || _currentPart < 0) return false;

        if (_numParts < 2) {
            return true;
        }

        if (_other == null) {
            _other = new IntPartitioner(_number - _currentPart, _numParts - 1);
        }

        if (_currentPart == 0) return _other.hasNext();

        if (!_other.hasNext()) {
            _currentPart -= 1;
            _other = new IntPartitioner(_number - _currentPart, _numParts - 1);
        }

        return _other.hasNext();
    }

    @Override
    public List<Integer> next() {

        if (_terminated) return List.of();

        return switch (_numParts) {
            case 0 -> throw new IllegalStateException("Oops!");
            case 1 -> {
                _terminated = true;
                yield List.of(_number);
            }
            default -> {
                var result = new ArrayList<>(_other.next());
                result.add(_currentPart);
                yield result;
            }
        };
    }
}
