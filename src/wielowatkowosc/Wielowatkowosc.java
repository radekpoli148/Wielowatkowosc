package wielowatkowosc;



public class Wielowatkowosc 
{
    public static void main(String[] args) 
    {
        //Runnable wypisanie = new WypisanieRunnable();
        
        /*Object lock = new Object();
        
        Thread watek = new Thread(new WypisanieRunnable(lock), "Watek 1");
        Thread watek2 = new Thread(new WypisanieRunnable(lock), "Watek 2");
        
        watek.start();
        watek2.start();*/
        
        Counter licznik = new Counter();
        
        Thread watek3 = new Thread(new CounterRunnable(licznik, false), "Maleje");
        Thread watek4 = new Thread(new CounterRunnable(licznik, true), "Rośnie");
        
        watek3.start();
        watek4.start();
    }
}

class WypisanieRunnable implements Runnable
{
    public WypisanieRunnable(Object lock)
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
        synchronized (lock) 
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
    }
    private Object lock;
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