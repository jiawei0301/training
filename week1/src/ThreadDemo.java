import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo {

    // Thread safety with synchronized and volatile
    private volatile boolean running = true; // Volatile for visibility
    private int counter = 0;                 // Not thread-safe
    private AtomicInteger atomicCounter = new AtomicInteger(0); // CAS (non-locking)

    private final ReentrantLock lock = new ReentrantLock();     // Lock interface

    public static void main(String[] args) throws Exception {
        ThreadDemo demo = new ThreadDemo();
        demo.startAll();
    }

    void startAll() throws Exception {
        // Thread by extending Thread
        Thread thread1 = new MyThread();
        thread1.start();

        // Thread by implementing Runnable
        Thread thread2 = new Thread(new MyRunnable());
        thread2.start();

        // Thread with Callable (thread pool)
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Integer> future = pool.submit(new MyCallable());

        // Thread Pool usage
        pool.submit(() -> System.out.println("Task in thread pool"));

        // Demonstrate thread safety and CAS
        Thread incrementer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                incrementSync();             // synchronized
                incrementWithLock();         // Lock
                incrementCAS();              // CAS (AtomicInteger)
            }
        });
        incrementer.start();

        // Demonstrate wait, notify, and thread lifecycle
        Object monitor = new Object();
        Thread waiter = new Thread(() -> {
            synchronized (monitor) {
                System.out.println("Waiter: waiting...");
                try {
                    monitor.wait(); // Wait and release lock
                    System.out.println("Waiter: notified and running!");
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        });

        Thread notifier = new Thread(() -> {
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            synchronized (monitor) {
                System.out.println("Notifier: notifying waiter...");
                monitor.notify();
            }
        });

        waiter.start();
        notifier.start();

        // Demonstrate sleep (pausing a thread)
        Thread sleeper = new Thread(() -> {
            System.out.println("Sleeper: sleeping for 500ms...");
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            System.out.println("Sleeper: awake!");
        });
        sleeper.start();

        // Demonstrate volatile
        Thread stopper = new Thread(() -> {
            while (running) { /* busy wait */ }
            System.out.println("Stopper: detected running == false!");
        });
        stopper.start();

        Thread.sleep(200); // Let stopper start
        running = false;   // Volatile ensures immediate visibility

        // Wait for all threads to finish
        thread1.join();
        thread2.join();
        incrementer.join();
        waiter.join();
        notifier.join();
        sleeper.join();
        stopper.join();
        pool.shutdown();

        System.out.println("Callable returned: " + future.get());
        System.out.println("Demo complete. Counter = " + counter + ", AtomicCounter = " + atomicCounter.get());
    }

    // Synchronized method (thread safety)
    private synchronized void incrementSync() {
        counter++;
        System.out.println("synchronized: counter = " + counter);
    }

    // Lock interface usage (thread safety)
    private void incrementWithLock() {
        lock.lock();
        try {
            counter++;
            System.out.println("Lock: counter = " + counter);
        } finally {
            lock.unlock();
        }
    }

    // CAS (non-locking atomic update)
    private void incrementCAS() {
        int val = atomicCounter.incrementAndGet();
        System.out.println("CAS (AtomicInteger): value = " + val);
    }

    // Extending Thread
    static class MyThread extends Thread {
        public void run() {
            System.out.println("MyThread: running!");
        }
    }

    // Implementing Runnable
    static class MyRunnable implements Runnable {
        public void run() {
            System.out.println("MyRunnable: running!");
        }
    }

    // Implementing Callable
    static class MyCallable implements Callable<Integer> {
        public Integer call() {
            System.out.println("MyCallable: running and returning 123");
            return 123;
        }
    }
}
