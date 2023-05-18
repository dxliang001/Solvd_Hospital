package com.solvd.Hospital.ConnectionPool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.solvd.Hospital.Main;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ConnectionManager {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final int POOL_SIZE = 5;
    private static final int TOTAL_THREADS = 7;
    private static ConnectionPool connectionPool;


    public static void initializePool() {
        // lazy initialization of the ConnectionPool
        if (connectionPool == null) {
            connectionPool = new ConnectionPool(POOL_SIZE);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_THREADS);

        for (int i = 0; i < TOTAL_THREADS; i++) {
            CompletableFuture.runAsync(() -> {
                try {
                    logger.info("Thread " + Thread.currentThread().getId() + " is requesting a connection...");
                    Connection connection = connectionPool.getConnection();
                    logger.info("Thread " + Thread.currentThread().getId() + " got a connection: " + connection);


                    Thread.sleep(2000);

                    connectionPool.releaseConnection(connection);
                    logger.info("Thread " + Thread.currentThread().getId() + " released the connection.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, executorService);
        }

        executorService.shutdown();
    }
}
