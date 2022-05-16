package callcenter.domain;

import callcenter.utils.ReceptionTime;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Operator implements Runnable {

    private static final String DEFAULT_NAME_OF_OPERATOR = "Operator";

    private final String name;
    private final BlockingQueue<Customer> customersQueue;

    public Operator(int id, BlockingQueue<Customer> customersQueue) {
        this.name = DEFAULT_NAME_OF_OPERATOR + id;
        this.customersQueue = customersQueue;
    }

    @Override
    public void run() {
        System.out.println(this.name + " started work.");

        while (ReceptionTime.getIsReceptionTime() || !customersQueue.isEmpty()) {
            try {
                Customer customer = customersQueue.take();
                System.out.println(customer.getName() + " is connected to the " + name);

                TimeUnit.SECONDS.sleep(5);

                System.out.println(name + " spoke to the " + customer.getName());

                //throw new InterruptedException();

            } catch (InterruptedException e) {
                System.out.println(name + " finished work with Error.");
                e.printStackTrace();
                break;
            }
        }
        if (!ReceptionTime.getIsReceptionTime() && customersQueue.isEmpty()) {
            System.out.println(name + " finished work.");
        }
    }
}
