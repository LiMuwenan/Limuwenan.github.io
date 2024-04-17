package cn.edu.xidian;

public class State {

    public static void main(String[] args) {
        Thread t1 = new Thread();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t1："+ t1.getState());//NEW

        Thread t2 = new Thread(){
            @Override
            public void run() {
                while  (true){

                }
            }
        };
        t2.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t2："+ t2.getState());//RUNNABLE


        Thread t3 = new Thread(){
            @Override
            public synchronized void run() {
                synchronized(State.class){
                    try {
                        Thread.sleep(200000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t3.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t3："+ t3.getState());//TIMED_WAITING

        Thread t4 = new Thread(){
            @Override
            public void run() {
//                System.out.println("t4 end");
            }
        };
        t4.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("t4："+ t4.getState());//TERMINATED
        Thread t5 = new Thread(){
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t5.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t5：" + t5.getState());//WAITING


        Thread t6 = new Thread(){
            @Override
            public void run() {
                synchronized(State.class){
                    try {
                        Thread.sleep(200000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t6.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t6：" + t6.getState());//BLOCKED

    }
}
