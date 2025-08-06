package org.test.task.model;

public class ThreadInfo {
    private final int period;
    private final int intervalFrom;
    private final int intervalTo;

    public ThreadInfo(int period, int intervalFrom, int intervalTo) {
        this.period = period;
        this.intervalFrom = intervalFrom;
        this.intervalTo = intervalTo;
    }

    public long getPeriodMillis() {
        return period * 1000L;
    }

    public int getIntervalFrom() {
        return intervalFrom;
    }

    public int getIntervalTo() {
        return intervalTo;
    }
}
