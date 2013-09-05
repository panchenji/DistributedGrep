import java.io.*;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 8/31/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class InputHandler implements Runnable {
    Socket socket;
    InputHandler(Socket s){
        this.socket = s;
    }
    public void run(){

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true);
            StringBuilder cmdBuider = new StringBuilder();
            String nextline;
            while (!(nextline = in.readLine()).equals("MSG_END")) {
                cmdBuider.append(nextline);
                //System.out.format("InputHandler: Get Command:%s\n",nextline);
            }


            //out.println("Get command. Thanks");
            String cmdResult = this.socket.getRemoteSocketAddress().toString().substring(1);
            cmdResult += runCmd(cmdBuider.toString());

            out.println(cmdResult);

            out.println("MSG_END");
            while ((nextline = in.readLine())!=null) {

            }
            //System.out.println("InputHandler:close()");
            out.close();
            in.close();
            socket.close();
         }catch(IOException e){
            System.out.println("InputHandler: run() fails");
            e.printStackTrace();
         }
    }
    protected static String runCmd(String cmd) throws IOException{
        StringBuilder re = new StringBuilder();
        //re.append(this.socket.getRemoteSocketAddress().toString().substring(1));
        re.append(":\n");
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(cmd);
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));
        String s;
        while ((s = stdInput.readLine()) != null) {
            re.append(s);
            re.append("\n");
        }
        while ((s = stdError.readLine()) != null) {
            re.append(s);
            re.append("\n");
        }
        return re.toString();

    }
}
