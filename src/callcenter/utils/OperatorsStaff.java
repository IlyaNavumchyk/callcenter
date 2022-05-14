package callcenter.utils;

import callcenter.domain.Customer;
import callcenter.domain.Operator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class OperatorsStaff extends Thread {

    private static final int DEFAULT_ID = 1;

    private static AtomicInteger numberOfOperatorsWhoWork;

    private final int numberOfOperators;
    private final BlockingQueue<Customer> customersQueue;

    public OperatorsStaff(int numberOfOperators, BlockingQueue<Customer> queue) {
        this.numberOfOperators = numberOfOperators;
        this.customersQueue = queue;
    }

    @Override
    public void run() {
        try {
            numberOfOperatorsWhoWork = new AtomicInteger();

            for (int i = 0; i < numberOfOperators; i++) {
                new Thread(new Operator(i + DEFAULT_ID, customersQueue)).start();
                Thread.sleep(100);
            }
            //0 means none of the operators work
            while (numberOfOperatorsWhoWork.get() != 0) {
                TimeUnit.SECONDS.sleep(3);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void incrementNumberOfOperatorsWhoWork() {
        numberOfOperatorsWhoWork.incrementAndGet();
    }

    public static void decrementNumberOfOperatorsWhoWork() {
        numberOfOperatorsWhoWork.decrementAndGet();
    }
}
