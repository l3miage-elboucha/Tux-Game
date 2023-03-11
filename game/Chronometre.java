package game;

public class Chronometre {
    private long begin;
    private long end;
    private long current;
    private int limite;

    public Chronometre(int limite) {
        //intialisation
        this.limite = limite;
        this.start();
        this.end = begin + limite;
        
    }
    
    public void start(){
        begin = System.currentTimeMillis();
    }
 
    public void stop(){
        end = current - begin;
    }
 
    public long getTime() {
        return end-begin;
    }
 
    public long getMilliseconds() {
        return end-begin;
    }
 
    public int getSeconds() {
        return (int) ((end - begin) / 1000.0);
    }
 
    public double getMinutes() {
        return (end - begin) / 60000.0;
    }
 
    public double getHours() {
        return (end - begin) / 3600000.0;
    }
    
    
    /**
    * Method to know the time spent in the current game
     * @return 
    */
    public int tempsPasse(){
        current = System.currentTimeMillis();
        int tempsPasse;
        tempsPasse = (int) ((current - begin)/1000.0);
        return tempsPasse;
    }
    
    /**
    * Method to know if it remains time.
     * @return 
    */
    public boolean remainsTime() {
        current = System.currentTimeMillis();
        int timeSpent;
        timeSpent = (int) ((current - begin) / 1000.0);
        int limite2 = limite/1000;
        return (timeSpent < limite2);
    }
     
}