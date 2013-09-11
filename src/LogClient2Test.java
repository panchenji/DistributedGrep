import org.junit.Before;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

/**
* Created with IntelliJ IDEA.
* User: chenjipan
* Date: 9/10/13
* Time: 5:31 PM
* To change this template use File | Settings | File Templates.
*/
public class LogClient2Test {
    LogClient2 client;
    List<String> msgQueue;
    Message msg;
    @Before
    public void setUp() throws Exception {
        String address = Config.ipAddresses[0];
         msgQueue = new LinkedList<String>();
        msg = new Message("");
        client = new LogClient2(address, Config.port, msg, msgQueue, Config.machineNumMap.get(address));

    }

    @org.junit.Test
    public void testRun() throws Exception {
        Thread t = new Thread(client);
        t.start();
        sleep(100);
        MSGHandler.updateMSG("key=key1;",msg);
        synchronized (msg) {
            msg.notify();
        }
        sleep(100);
        boolean cap = msgQueue.get(0).indexOf("key1:value1")>=0;
        assertEquals(true, cap);
    }
}
