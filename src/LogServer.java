import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 8/31/13
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogServer implements Runnable{
    ServerSocket listener;
    Socket socket;
    int pattern;
    LogServer(int port, int pattern) throws IOException{
        this.pattern = pattern;
        try{
            listener = new ServerSocket(port);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
   public void run(){
       try{
           while(true){
            socket = null;
            socket = listener.accept();
            System.out.format("Connect to %s\n", socket.getRemoteSocketAddress().toString());

               if(pattern == 0)
                   new Thread(new InputHandler(socket)).start();
               else
                   new Thread(new InputHandler2(socket)).start();
            }
       }catch(Exception e){
           System.err.format("LogServer: Fail to connect to:%s\n",socket.getRemoteSocketAddress().toString());
           e.printStackTrace();
       }

   }

}
