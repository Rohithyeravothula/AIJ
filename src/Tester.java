import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tester {

    public void test() throws InterruptedException {
        Integer i;
        for(i = 0; i<1; i++)
            Thread.sleep(100);
    }

    public static void main(String [] args){
        Tester t = new Tester();
        try{
            t.test();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}