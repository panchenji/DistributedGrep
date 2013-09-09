import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 8/31/13
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogClient implements Runnable {
    Socket s;
    BufferedReader in;
    PrintWriter out;
    Message msg;
    List<String> msgQueue;
    String address;
    int machineNum;
    int port;
    LogClient(String address, int port, Message msg, List<String> msgQueue, int num){
        this.address = address;
        this.msgQueue = msgQueue;
        this.msg = msg;
        this.port = port;
        this.machineNum = num;
        System.out.format("Create LogClient to address:%s:%d\n",address,port);

    }
    public void run(){
        try{
            if(msg.inSet(this.machineNum)){
            this.s = new Socket(address, port);
            this.in =
                    new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.out = new PrintWriter(s.getOutputStream(), true);

            this.out.println(msg.getContent());
            this.out.println("MSG_END");
            String nextline;
            StringBuilder msgResult = new StringBuilder();
            while (!(nextline = this.in.readLine()).equals("MSG_END")) {
                msgResult.append(nextline);
                msgResult.append("\n");
            }
            //System.out.println("LogClient:close()");
            this.out.close();
            this.in.close();
            this.s.close();
            this.msgQueue.add(msgResult.toString());
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
