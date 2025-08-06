package org.test.task;

import org.test.task.model.ThreadInfo;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultithreadStarter {

    private final List<Lock> locks = List.of(
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock()
    );

    // Инициализируем список с информацией о потоках, взятой из ТЗ
    private final List<ThreadInfo> threadInfoList = List.of(
            new ThreadInfo(10, 3, 12),
            new ThreadInfo(5, 2, 7)
    );

    public void start() {
        // Создаём пул потоков, соответствующий количеству задач
        ExecutorService executorService = Executors.newFixedThreadPool(threadInfoList.size());

        // Запускаем все задачи - каждая с разным периодом и интервалом
        threadInfoList.stream()
                .map(threadInfo -> new WorkImitationThread(threadInfo, locks))
                .forEach(executorService::execute);
    }
}
