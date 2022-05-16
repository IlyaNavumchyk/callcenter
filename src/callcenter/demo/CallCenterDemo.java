package callcenter.demo;

import callcenter.domain.Customer;
import callcenter.utils.CustomersQueue;
import callcenter.utils.OperatorsStaff;
import callcenter.utils.ReceptionTime;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CallCenterDemo {

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of operators: ");
        int numberOfOperators = scanner.nextInt();

        System.out.print("Enter number of customers: ");
        int numberOfCustomers = scanner.nextInt();

        //Creates a queue of customers
        BlockingQueue<Customer> customersQueue = new LinkedBlockingQueue<>();

        //Call center started. CustomersQueue is open. Set flag isWork = true
        System.out.println("\nCall center started work.\n");
        ReceptionTime.startCallCenter();

        //Creates a thread that runs a state of operators
        Thread operatorsStaff = new OperatorsStaff(numberOfOperators, customersQueue);
        operatorsStaff.start();
        TimeUnit.SECONDS.sleep(1);

        //Creates a thread that fills a queue with customers
        new Thread(new CustomersQueue(numberOfCustomers, customersQueue)).start();

        //Reception time 30 seconds while operatorsStaff is work
        for (int i = 0; i < 30; i++) {
            if (operatorsStaff.isAlive()) {
                TimeUnit.SECONDS.sleep(1);
            } else {
                break;
            }
        }

        //CustomersQueue is close. Set flag isWork = false
        ReceptionTime.stopCallCenter();

        //Waiting for the completion of the staff of operators
        operatorsStaff.join();

        //Call center closed
        System.out.println("Call center finished work.");

        scanner.close();
    }
}
