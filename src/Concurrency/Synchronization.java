package Concurrency;

import Concurrency.Misc.BankAccount;

/* Synchronized Methods -- See bank account class for implementation
 * - Different invocations of synchronized methods, on the same object, are guardanteed to not interleave
 * - When one thread is executing a sunchornized method for an object, all other thread that invoke that synchornized method block/suspend their execution until the first thread is done with the execution
 * - When a synchornized method exists, it ensures that the state of the object is visible to all threads
 *
 * Critical section - definition
 * - Code that is referencing a shared resource - like a variable
 * - Only one thread at a time should be able to execute a critical section
 * - When all critical sections are synchronized, the class is thread safe
 * */

public class Synchronization {
    public static void main(String[] args) {

        BankAccount companyAccount = new BankAccount(10000);

        Thread thread1 = new Thread(() -> companyAccount.withdraw(2500));
        Thread thread2 = new Thread(() -> companyAccount.deposit(5000));
        Thread thread3 = new Thread(() -> companyAccount.withdraw(2500));
        Thread thread4 = new Thread(() -> companyAccount.withdraw(5000));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Balance: " + companyAccount.getBalance());
    }
}
