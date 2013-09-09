import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 9/4/13
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */

class Test{
    public static void main(String[] args){
        UnitTest u = new UnitTest();
        u.simpleTest();
    }
}
public class UnitTest {
    DistributedLog log;
    Thread logThread;

    InetAddress addr;
    String localAddress;
    public void setup(){
        try{
            addr = InetAddress.getLocalHost();
            localAddress = addr.getHostAddress();
            if((localAddress==Config.ipAddresses[0])){
                log = new DistributedLog(1);
            }
            if((addr.getHostAddress()==Config.ipAddresses[0])){
                DistributedLog machine = new DistributedLog(1);
            }
            if((addr.getHostAddress()==Config.ipAddresses[1])){
                log = new DistributedLog(1);

            }
            logThread = new Thread(log);
            logThread.start();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public boolean testGrepResult(){

            setup();
            ByteArrayInputStream in = new ByteArrayInputStream("key=key1".getBytes());
            System.setIn(in);


       return true;
    }
    public boolean testMachineDown(){
           setup();
        if((localAddress==Config.ipAddresses[0])){
            logThread.interrupt();
        }

        return true;

    }
    public void simpleTest(){
        log = new DistributedLog(1);
        logThread = new Thread(log);
        logThread.start();
    }
}
