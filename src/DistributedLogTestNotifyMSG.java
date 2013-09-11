import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

/**
* Created with IntelliJ IDEA.
* User: chenjipan
* Date: 9/10/13
* Time: 1:55 PM
* To change this template use File | Settings | File Templates.
*/
public class DistributedLogTestNotifyMSG {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    DistributedLog log;
    boolean setted =false;
    @Before
    public void setUp() throws Exception {
        if(setted) return;
      log = new DistributedLog(0);
        System.setOut(new PrintStream(outContent));
        log.initialize(true);
        sleep(100);
        setted = true;
    }
    /*
    @org.junit.Test
    public void testNotifyMSG() throws Exception {
        //ByteArrayInputStream in = new ByteArrayInputStream("key=key1;machine=0;".getBytes());
        //System.setIn(in);
        //System.err.println("start");

        //System.err.println("notify");

        log.notifyMSG("key=key1;");
        //System.err.println("notify end");
        sleep(100);
        //System.err.println(outContent.toString());
        boolean cap = outContent.toString().indexOf("key1:value1") >= 0;
        assertEquals(true, cap);


    }
     */

    @org.junit.Test
    public void testNotifyMSGException() throws Exception{
        log.notifyMSG("key=key1;");
        //System.err.println("notify end");
        sleep(100);
        boolean cap = outContent.toString().indexOf("DISCONNECTED ERROR") >= 0;
        assertEquals(true, cap);

    }

}
