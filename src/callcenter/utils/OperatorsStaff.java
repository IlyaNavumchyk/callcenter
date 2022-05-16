package callcenter.utils;

import callcenter.domain.Customer;
import callcenter.domain.Operator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OperatorsStaff extends Thread {

    private static final int DEFAULT_ID = 1;

    private final int numberOfOperators;
    private final BlockingQueue<Customer> customersQueue;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public OperatorsStaff(int numberOfOperators, BlockingQueue<Customer> queue) {
        this.numberOfOperators = numberOfOperators;
        this.customersQueue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < numberOfOperators; i++) {
                executorService.execute(new Operator(i + DEFAULT_ID, customersQueue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        try {
            while (!executorService.isTerminated()) {
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("OperatorsStaff finished work.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
