import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 8/31/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class DistributedLog implements Runnable{
    //static String[] addresses = {"127.0.0.1","192.168.1.102"};

    //public static void main(String[] args) throws Exception{
    //     frequent(2);
    //}
    int pattern; //0:infrequent 1:frequent
    Message msg;
    HashMap<String, Thread> threadMap;
    List<String> msgQueue;
    Thread serverThread;
    long lastCheckTime;

    DistributedLog(int pattern){
       this.pattern = pattern;
    }
    public void run(){
        try{
        if(pattern==0) infrequent();
        else frequent();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void infrequent() throws Exception{
        initialize(false);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String cmd;
        while(true){
            System.out.println("Enter your command: \n");
            cmd = br.readLine();
            //Message msg = new Message("");
            MSGHandler.updateMSG(cmd,msg);
            for(String address: Config.ipAddresses){
                new Thread(new LogClient(address, Config.port, msg , msgQueue,Config.machineNumMap.get(address))).start();

            }
            int threshold;
            //System.out.println(msg.commSets.size());
            if(msg.commSets.size()==0) threshold = Config.ipAddresses.length;
            else threshold = msg.commSets.size();
            while(msgQueue.size() < threshold){
                //System.out.println(msg.commSets.size());
                //System.out.println(msgQueue.size());
                Thread.sleep(100);
            }
            for(String s: msgQueue){
                System.out.print(s);
            }
            msgQueue.clear();
        }
    }
    public void frequent() throws Exception{
        String cmd;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        initialize(false);
        while(true){

            System.out.println("Enter your command:");
            cmd = br.readLine();
            //msg.setContent(cmd);
            notifyMSG(cmd);
        }
    }
    public void notifyMSG(String cmd) throws Exception{



        MSGHandler.updateMSG(cmd, msg);


       if(pattern==1){
            if(System.currentTimeMillis() - lastCheckTime > Config.checkInterval){
                lastCheckTime = System.currentTimeMillis();
                for(String address: Config.ipAddresses){
                    if(!threadMap.get(address).isAlive())  {
                        threadMap.put(address, new Thread(new LogClient2(address, Config.port, msg, msgQueue, Config.machineNumMap.get(address))));
                        threadMap.get(address).start();
                    }
                }
            }
            if(!msg.valid) {
                System.out.println("Invalid input");
                return;
            }
            synchronized (msg){
                msg.notifyAll();
            }
       }
        else{
           for(String address: Config.ipAddresses){
               new Thread(new LogClient(address, Config.port, msg , msgQueue,Config.machineNumMap.get(address))).start();

           }

       }
        int threshold;
        //System.out.println(msg.commSets.size());
        if(msg.commSets.size()==0) threshold = Config.ipAddresses.length;
        else threshold = msg.commSets.size();
        while(msgQueue.size() < threshold){
            Thread.sleep(100);
        }
        for(String s: msgQueue){
            System.out.print(s);
        }
        msgQueue.clear();
        msg.clear();

    }
    public void initialize(boolean test) throws Exception{
        serverThread = new Thread(new LogServer(Config.port, pattern));
        serverThread.start();
        System.out.println("Log server started");
        this.msgQueue = Collections.synchronizedList(new LinkedList<String>());;
        this.msg = new Message("");
        if(pattern==0) return;

        this.threadMap = new HashMap<String, Thread>();
        this.lastCheckTime = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        if(!test){
            String cmd;
            System.out.println("Enter press if all the machines are set");
            cmd = br.readLine();
        }

        for(String address: Config.ipAddresses){
            Thread newThread = new Thread(new LogClient2(address, Config.port, msg,  msgQueue, Config.machineNumMap.get(address)));
            threadMap.put(address, newThread);
            newThread.start();

        }

    }
}
