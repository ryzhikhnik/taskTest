package org.test.task.utils;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    /**
     * Возвращает случайное значение задержки в миллисекундах из заданного диапазона
     */
    public static long getRandomSleepTime(int from, int to) {
        return random.nextInt(from, to + 1) * 1000L;
    }

    /**
     * Возвращает два различных случайных индекса из диапазона [0, locksCount)
     */
    public static int[] getTwoRandomLockIndex(int locksCount) {
        int firstIdx = random.nextInt(locksCount);
        int secondIdx = random.nextInt(locksCount);

        while (firstIdx == secondIdx) {
            secondIdx = random.nextInt(locksCount);
        }

        return new int[]{firstIdx, secondIdx};
    }
}
