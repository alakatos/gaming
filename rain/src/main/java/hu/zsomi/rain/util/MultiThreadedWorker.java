package hu.zsomi.rain.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class MultiThreadedWorker<T> {
    private final int numberOfThreads;
    private final ExecutorService executor;
    
    public MultiThreadedWorker(int numberOfThreads) {
        executor = Executors.newFixedThreadPool(numberOfThreads);
        this.numberOfThreads = numberOfThreads;
    }

    public void work(final Iterable<T> iterable, final Consumer<T> task) throws InterruptedException, ExecutionException {
        final Iterator<T> iter = iterable.iterator();
        List<Future<Void>> futures = new LinkedList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            futures.add(executor.submit(()->{
                try {
                    while (iter.hasNext()) {
                        task.accept(iter.next());
                    }
                } catch (NoSuchElementException e) {
                    //exit loop
                }
                return null;
            }));
        }

        for (Future<Void> future : futures) {
            future.get(); //join
        }
    }
}
