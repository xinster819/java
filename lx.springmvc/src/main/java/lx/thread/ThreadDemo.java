package lx.thread;

public class ThreadDemo {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("a");
                    Thread.currentThread().interrupt();
                }
                System.out.println("enter 2...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("b");
                }
            }
        });
        t.start();
        try {
            Thread.sleep(1000);
            t.interrupt();
            Thread.sleep(100000);
        } catch (InterruptedException e) {
        }
    }

}
