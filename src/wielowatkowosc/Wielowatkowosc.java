package wielowatkowosc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Wielowatkowosc 
{
    public static void main(String[] args) throws InterruptedException 
    {
        //Runnable wypisanie = new WypisanieRunnable();
        
        Lock lock = new ReentrantLock();
        
        Thread watek = new Thread(new WypisanieRunnable(lock), "Watek 1");
        Thread watek2 = new Thread(new WypisanieRunnable(lock), "Watek 2");
        
        watek.start();
        watek.join();//jezeli chcemy poczekać aż jakiś wątek się zakończy używamy metody join()
        
        System.out.println(Thread.currentThread().getName());
        System.out.println("cos się ztało po zakonczonym wątku watek");
        
        watek2.start();
        
        /*Counter licznik = new Counter();
        
        Thread watek3 = new Thread(new CounterRunnable(licznik, false), "Maleje");
        Thread watek4 = new Thread(new CounterRunnable(licznik, true), "Rośnie");
        
        watek3.start();
        watek4.start();*/
    }
}

class WypisanieRunnable implements Runnable
{
    public WypisanieRunnable(Lock lock)
    {
        this.lock = lock;
    }
    static String msg[] = {"To", "jest", "synchronicznie", "wypisana", "wiadomość"};
    @Override
    public void run() 
    {
        display(Thread.currentThread().getName());
    }
    
    public void display(String threadName)
    {
        lock.lock();//obsługa wątków synchronicznie za pomocą metody lock()
        //pozwala na wykonywanie wątków w kolejmości ich wywołania
        try
        {
            for(int i = 0; i < msg.length; i++)
            {
                System.out.println(threadName + ": " + msg[i]);

                try
                {
                    Thread.sleep(100);
                }
                catch(InterruptedException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        finally
        {
            lock.unlock();
        }
    }
    private Lock lock;
}

class Counter
{
    public void IncreaseCounter()
    {
        counter++;
        System.out.println(Thread.currentThread().getName()+": "+counter);
    }
    public void DecreaseCounter()
    {
        counter--;
        System.out.println(Thread.currentThread().getName()+": "+counter);
    }
    
    private int counter = 50;
}

class CounterRunnable implements Runnable
{
    public CounterRunnable(Counter licznik, boolean increase)
    {
        this.licznik = licznik;
        this.increase = increase;
    }

    @Override
    public void run() 
    {
        synchronized (licznik) 
        {
        
            for(int i = 0; i < 50; i++)
            {
                if(increase)
                    licznik.IncreaseCounter();
                else
                    licznik.DecreaseCounter();

                try
                {
                    Thread.sleep(100);
                }
                catch(InterruptedException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    boolean increase;
    Counter licznik;
}