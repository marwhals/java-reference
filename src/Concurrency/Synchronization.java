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
 *
 * The object instance monitor
 * - Every object in Java has a built-in intrinsic lock - a.k.a monitor lock
 * - A thread acquires a lock by executing a synchornised method on the instance or by using a the instance as a parameter to a synchronised statement
 * - A thread releases a lock when it exits from a synchornised block or method ---- even if it throws an exception
 * - Only one thread at a time can acquire a lock - this prevents other threads accessing the instance state until the lock is released
 * - All other threads which want access to the instances state through synchornised code will block and wait until they can acquire a lock
 * NOTE:
 * - The synchronized block can use a different object on which to acquire its lock
 * - So code accessing a instance of the class will not have to unnecessarily block due to the intrinsic lock
 *
 * The synchronized statement
 * - Usually a better option since it limits the synchronisation to the critical section of code
 * - i.e the developer has better control over when threads should and shouln't be blocked
 *
 * Reentrant Synchronization
 * - Once a thread acquires a lock all other threads will block which require it.
 * - If the current thread has the lock any nested calls which try and acquire the lock wont block since the thread already has it
 * - Without this threads could block themselves
 * - Concept is built into Java and it is based on Monitors
 *
 * */

public class Synchronization {
    public static void main(String[] args) {

        BankAccount companyAccount = new BankAccount("Bub", 10000);

        Thread thread1 = new Thread(() -> companyAccount.withdraw(2500));
        Thread thread2 = new Thread(() -> companyAccount.deposit(5000));
        Thread thread5 = new Thread(() -> companyAccount.setName("Bob"));
        Thread thread3 = new Thread(() -> companyAccount.withdraw(2500));
        Thread thread4 = new Thread(() -> companyAccount.withdraw(5000));

        thread1.start();
        thread2.start();
        thread3.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread4.start();
        thread5.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Balance: " + companyAccount.getBalance());
    }
}
