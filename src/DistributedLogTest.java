import org.junit.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

/**
* Created with IntelliJ IDEA.
* User: chenjipan
* Date: 9/9/13
* Time: 11:47 PM
* To change this template use File | Settings | File Templates.
*/
public class DistributedLogTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    DistributedLog log;
    Thread t;
    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        this.log = new DistributedLog(0);
        this.t = new Thread(log);
        this.t.start();
        sleep(1000);
        ByteArrayInputStream in1 = new ByteArrayInputStream(" ".getBytes());
        System.setIn(in1);
    }

    @org.junit.Test
    public void testInfrequent() throws Exception {

        ByteArrayInputStream in = new ByteArrayInputStream("key=key1;machine=0;".getBytes());
        System.setIn(in);
        boolean cap = outContent.toString().indexOf("key1:value1") >= 0;
        assertEquals(true, cap);
    }

    @Test
    public void testFrequent() throws Exception {

    }
}
