import org.junit.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
    }

    @org.junit.Test
    public void testInfrequent() throws Exception {
      DistributedLog log = new DistributedLog(0);
        Thread t = new Thread(log);
        t.start();
        ByteArrayInputStream in = new ByteArrayInputStream("key=key1;machine=0;".getBytes());
        System.setIn(in);
        assertEquals("", outContent.toString());
    }
    @org.junit.Test(expected = Exception.class)
    public void testInfrequentException() throws Exception {
        DistributedLog log = new DistributedLog(0);
        Thread t = new Thread(log);
        t.start();
        ByteArrayInputStream in = new ByteArrayInputStream("key=key1;machine=1;".getBytes());
        System.setIn(in);
    }

    @Test
    public void testFrequent() throws Exception {

    }
}
