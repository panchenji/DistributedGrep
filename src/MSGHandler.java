/**
 * Created with IntelliJ IDEA.
 * User: chenjipan
 * Date: 9/8/13
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class MSGHandler {
    public static void updateMSG(String input, Message msg){
        try{
            StringBuilder sb = new StringBuilder();
            int keyIndex = input.indexOf("key=");
            int valueIndex = input.indexOf("value=");
            int machineIndex = input.indexOf("machine=");

            if(machineIndex >= 0){

                String[] machines = input.substring(machineIndex+8, input.indexOf(";",machineIndex)).split(",");
                for(String machine:machines) {
                    System.out.println("MSGGenerator:"+machine);
                    msg.addMachine(Integer.parseInt(machine));
                }


            }



            if(keyIndex >= 0){
                sb.append(input.substring(keyIndex+4, input.indexOf(";",keyIndex)));

            }
            sb.append(":");
            if(valueIndex >= 0){
                sb.append(input.substring(keyIndex+6, input.indexOf(";",keyIndex)));

            }
            msg.setContent(sb.toString());
            msg.setValid(true);
        } catch (Exception e){
            e.printStackTrace();
             msg.setValid(false);

        }





    }

}
