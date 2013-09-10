import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 9/4/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    final static String[] ipAddresses = {"192.17.237.167","172.16.169.185"};
    final static int port = 9999;
    final static long checkInterval = 10000;
    final static HashMap<String,Integer> machineNumMap ;
    static{
       machineNumMap = new HashMap<String, Integer>();
       machineNumMap.put("192.17.237.167",1);
       machineNumMap.put("172.16.169.185",0);
    }
}
