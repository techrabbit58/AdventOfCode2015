package com.example.Day14;

record Reindeer(String name, int velocity, int runTime, int offTime) {

    int distanceAfter(int seconds) {

        var cycleTime = runTime + offTime;
        int numCycles = seconds / cycleTime;
        int remaining = seconds % cycleTime;

        return velocity * (runTime * numCycles + Math.min(remaining, runTime));
    }
}
