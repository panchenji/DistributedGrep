import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 8/31/13
 * Time: 5:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Message {
    String msg;
    boolean valid;
    HashSet<Integer> commSets = new HashSet<Integer>();
    Message(String msg){
       this.msg = msg;
        this.valid = false;

    }
    public void addMachine(int num){
        commSets.add(num);

    }
    public void setValid(boolean b){
        this.valid = b;
    }
    public void setContent(String msg){
        this.msg = msg;
    }
    public boolean inSet(int num){
        if(commSets.size()==0) return true;
        return commSets.contains(num);
    }
    public String getContent(){
        return this.msg;
    }
    public void clear(){
        commSets = new HashSet<Integer>();
        valid = false;
    }
}
