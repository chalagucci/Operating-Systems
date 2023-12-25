import java.util.Random;
import java.util.function.Consumer;

public class Boundbuffer {
    static int [] buffer = new int[100];
    static int next_in = 0, next_out = 0;
    static int count = 0;
    static boolean done = false;
    static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    producer();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    consumer();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

    public static void producer() throws InterruptedException{
        while(true) {
            int k1 = (int) (Math.random() * buffer.length / 3) + 1;
            for (int i = 0; i < k1; i++)
                buffer[(next_in + 1) % buffer.length] += 1;
            next_in = (next_in + k1) % buffer.length;
            Thread.sleep((int) (Math.random()*900 + 100));
        }
    }

    public static void consumer() throws InterruptedException{
        while(true) {
            Thread.sleep((int) (Math.random()*900 + 100));
            int k2 = (int) (Math.random() * buffer.length / 3) + 1;
            for (int i = 0; i < k2; i++) {
                int data = buffer[(next_out + 1) % buffer.length];
                if(data > 1){
                    System.out.println("Race condition: Consumer to slow");
                    System.exit(1);
                }

                else if (data == 0) {
                    System.out.println("Race condition: Producer to slow");
                    System.exit(1);
                }

                else buffer[(next_out + 1) % buffer.length] = 0;
            }

            next_out = (next_out + k2) % buffer.length;
            System.out.println("Round " + ++count + "no race condition");
        }
    }

}
