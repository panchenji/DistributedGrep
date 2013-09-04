import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 8/31/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class DistributedLog {
    static String[] addresses = {"127.0.0.1","192.168.1.102"};
    final static int port = 9999;
    final static long checkInterval = 10000;
    public static void main(String[] args) throws Exception{
         frequent(2);
    }
    public static void infrequent(int pattern) throws Exception{
        Thread serverThread = new Thread(new LogServer(port, pattern));
        serverThread.start();
        System.out.println("Log server started");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String cmd;
        List<String> msgQueue = Collections.synchronizedList(new LinkedList<String>());
        while(true){
            System.out.println("Enter your command: \n");
            cmd = br.readLine();

            for(String address: addresses){
                new Thread(new LogClient(address, port, cmd, msgQueue)).start();

            }
            while(msgQueue.size() < addresses.length){
                Thread.sleep(100);
            }
            for(String s: msgQueue){
                System.out.print(s);
            }
            msgQueue.clear();
        }
    }
    public static void frequent(int pattern) throws Exception{
        Thread serverThread = new Thread(new LogServer(port, pattern));
        serverThread.start();
        System.out.println("Log server started");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String cmd;
        List<String> msgQueue = Collections.synchronizedList(new LinkedList<String>());;
        Message msg = new Message("");
        HashMap<String, Thread> threadMap = new HashMap<String, Thread>();
        long lastCheckTime = System.currentTimeMillis();
        for(String address: addresses){
            Thread newThread = new Thread(new LogClient2(address, port,"", msgQueue, msg));
            threadMap.put(address, newThread);
            newThread.start();

        }

        while(true){
            if(System.currentTimeMillis() - lastCheckTime > checkInterval){
                lastCheckTime = System.currentTimeMillis();
                for(String address: addresses){
                    if(!threadMap.get(address).isAlive())
                        threadMap.get(address).start();
                }
            }
            System.out.println("Enter your command: \n");
            cmd = br.readLine();
            msg.setContent(cmd);
            synchronized (msg){
                msg.notifyAll();
            }
            while(msgQueue.size() < addresses.length){
                Thread.sleep(100);
            }
            for(String s: msgQueue){
                System.out.print(s);
            }
            msgQueue.clear();

        }
    }
}
