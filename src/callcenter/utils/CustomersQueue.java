package callcenter.utils;

import callcenter.domain.Customer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class CustomersQueue implements Runnable {

    private static final int DEFAULT_ID = 1;

    private final BlockingQueue<Customer> customersQueue;

    //This field is required for run method with customer limit
    private final int numberOfCustomers;

    //This field is required for run method without customer limit
    private int countOfCustomers;

    public CustomersQueue(int numberOfCustomers, BlockingQueue<Customer> customerQueue) {
        this.customersQueue = customerQueue;
        this.numberOfCustomers = numberOfCustomers;
    }

    //This method starts a thread that creates a client every 2 seconds while call center is running
    @Override
    public void run() {
        while (ReceptionTime.getIsReceptionTime()) {
            try {
                Customer customer = new Customer(countOfCustomers++ + DEFAULT_ID);
                System.out.println(customer.getName() + " is waiting for the operator.");
                customersQueue.put(customer);
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //This method starts a thread that creates *numberOfCustomers* Customers
    /*@Override
    public void run() {
        try {
            for (int i = 0; i < numberOfCustomers; i++) {
                Customer customer = new Customer(i + DEFAULT_ID);
                System.out.println(customer.getName() + " is waiting for the operator.");
                customersQueue.put(customer);
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}
