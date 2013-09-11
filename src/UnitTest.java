import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
        //System.out.print(u.testGrepResult());
    }
}
public class UnitTest {
    DistributedLog log;
    Thread logThread;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    InetAddress addr;
    String localAddress;
    public void setup() throws Exception{
        System.setOut(new PrintStream(outContent));
        this.log = new DistributedLog(0);
        this.logThread = new Thread(log);
        this.logThread.start();
        Thread.sleep(1000);
        ByteArrayInputStream in1 = new ByteArrayInputStream(" ".getBytes());
        System.setIn(in1);

    }
    public boolean testGrepResult(){

        ByteArrayInputStream in = new ByteArrayInputStream("key=key1;machine=0;".getBytes());
        System.setIn(in);
        boolean cap = outContent.toString().indexOf("key1:value1") >= 0;



       return cap;
    }
    public boolean testMachineDown() throws Exception{
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
