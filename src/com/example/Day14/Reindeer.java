package com.example.Day14;

final class Reindeer {

    private final String _name;
    private final int _velocity;
    private final int _runTime;
    private int _score;
    private int _distance;
    private int _seconds;
    private final int _cycleTime;

    Reindeer(String name, int velocity, int runTime, int offTime) {
        _name = name;
        _velocity = velocity;
        _runTime = runTime;
        _score = 0;
        _distance = 0;
        _seconds = 0;
        _cycleTime = _runTime + offTime;
    }

    String name() {
        return _name;
    }

    void addOnePoint() {
        _score += 1;
    }

    void tick() {
        _seconds = (_seconds + 1) % _cycleTime;
        if (_seconds < _runTime) _distance += _velocity;
    }

    int score() {
        return _score;
    }

    int realTimeDistance() {
        return _distance;
    }

    int distanceAfter(int seconds) {

        int numCycles = seconds / _cycleTime;
        int remaining = seconds % _cycleTime;

        return _velocity * (_runTime * numCycles + Math.min(remaining, _runTime));
    }
}
