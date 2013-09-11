import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 9/4/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    final static String[] ipAddresses = {"192.17.237.159","172.16.150.161", "130.126.112.107"};
    final static int port = 9999;
    final static long checkInterval = 10000;
    final static HashMap<String,Integer> machineNumMap ;
    static{
       machineNumMap = new HashMap<String, Integer>();
       machineNumMap.put("192.17.237.159",1);
       machineNumMap.put("172.16.150.161",0);
        machineNumMap.put("130.126.112.107",2);
    }
}
