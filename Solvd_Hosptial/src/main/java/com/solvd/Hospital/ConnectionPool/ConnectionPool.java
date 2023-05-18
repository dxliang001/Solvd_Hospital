package com.solvd.Hospital.ConnectionPool;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class ConnectionPool {

    private ConcurrentLinkedQueue<Connection> pool;
    private Semaphore semaphore;

    public ConnectionPool(int size) {
        pool = new ConcurrentLinkedQueue<>();
        semaphore = new Semaphore(size);

        for (int i = 0; i < size; i++) {
            pool.add(new Connection("Connection-" + (i + 1)));
        }
    }

    public Connection getConnection() throws InterruptedException {
        semaphore.acquire();
        return pool.poll();
    }

    public void releaseConnection(Connection connection) {
        pool.add(connection);
        semaphore.release();
    }
}
