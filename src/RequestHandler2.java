import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 8/31/13
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestHandler2 extends RequestHandler {
    RequestHandler2(Socket s){
        super(s);

    }
    @Override
    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true);
            StringBuilder cmdBuider = new StringBuilder();
            String nextline;

            while ((nextline = in.readLine())!=null) {
                if(!nextline.equals("MSG_END"))
                    cmdBuider.append(nextline);
                else{
                    System.out.format("RequestHandler2: new request from:%s\n",this.socket.getRemoteSocketAddress().toString());
                    String cmdResult = this.socket.getRemoteSocketAddress().toString().substring(1);
                    System.out.println("command:"+cmdBuider.toString());
                    cmdResult += runCmd(cmdBuider.toString());

                    out.println(cmdResult);
                    out.println("MSG_END");
                    cmdBuider = new StringBuilder();

                }

            }
            System.err.format("RequestHandler2: Remote %s Disconnected\n",this.socket.getRemoteSocketAddress());
            out.close();
            in.close();
            socket.close();
        }catch(IOException e){
            System.out.println("RequestHandler: run() fails");
            e.printStackTrace();
        }
    }
}
