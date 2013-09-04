/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 8/31/13
 * Time: 5:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Message {
    String msg;
    Message(String msg){
       this.msg = msg;

    }
    public void setContent(String msg){
        this.msg = msg;
    }
    public String getContent(){
        return this.msg;
    }
}
