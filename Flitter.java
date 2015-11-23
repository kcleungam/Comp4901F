import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Krauser on 21/11/2015.
 */
public class Flitter {
    static AccountList accountList = new AccountList();
    static String idName = "Flitter Names.txt";
    static String idLiving = "People-Cities.txt";
    static String contactFile = "Links_Table.txt";

    public static void main(String args[]) {
        readFile(idName,idLiving, accountList);
        readContact(contactFile, accountList);
        //accountList.printAll();
        //accountList.printContactOf(5000);       //// works perfectly


        System.out.println("--------------------Employee------------------");
        AccountList handlerList = new AccountList();
        handlerList = filterByContact(30,40,accountList);   //test success, work perfectly
        handlerList.printAll();
        //TODO Please store it into JSON

        System.out.println("--------------------Employee------------------");
        AccountList employeeList = new AccountList();
        employeeList = filterByContact(38,42,accountList);
       // employeeList.printAll();
        // TODO Please store it into    another       JSON

        System.out.println("--------------Match Employee Handler set------------------");

        ArrayList<CrimeStructure> structureList = new ArrayList<CrimeStructure>();
        structureList = employeeHandlerMatch(employeeList,handlerList);
        for(int i = 0 ; i < structureList.size();i++){
            structureList.get(i).printAll();            // Work perfectly, you can change the employee list range form 39-41 to play
        }
        System.out.println("-------------Find Middleman---------------");
        /*
        structureList = findMiddleMan(structureList,accountList);
        for(int i = 0; i< structureList.size(); i++){
            structureList.get(i).printAll();
        }
        */



    }

    /**
     *      This function is a little bit complicate. We have to find the middleMan in each crimeStructure
     *      So first, we get each crimeStructure, then we get all handlers ( 3 or 4) in that structure
     *      The method to find common Contact is simple,  add all the contact of these 3/4 handlers into one
     *      Also add the subclass recordRepeat to count the number of contact appears ,  1 means it appear once, 3 means it appear three times( that's what we want)
     *
     * @param matchList
     * @return
     */
    public static ArrayList<CrimeStructure> findMiddleMan(ArrayList<CrimeStructure> matchList, AccountList accountList){

        ArrayList<CrimeStructure> structureList = new ArrayList<CrimeStructure>();
/*
        class RecordRepeat{
            int count = 1;
            int id = -1;

            RecordRepeat(){
                int count = 1;
                int id = -1;
            }
        }
*/

        for(int i = 0; i < matchList.size(); i++){
            SortedMap<Integer,Integer> repeatList = new TreeMap<Integer,Integer>();
            ArrayList<Integer> combineContact = new ArrayList<Integer>();

            for(int j = 0; j < matchList.get(i).getHandler().size(); j++){
                SortedMap<Integer,Profile> oneHandlerContactList = matchList.get(i).getHandler().get(j).getContact();
                for(Profile profile: oneHandlerContactList.values()){
                    if(combineContact.contains(profile.getId())){
                        //repeatList.replace( profile.getId() , (repeatList.get(profile.getId()))+1 );    //get the old value and +1

                    } else{
                        combineContact.add(profile.getId());            // add to the conbineContact if it is first appear
                        repeatList.put(profile.getId(),1);
                    }

                }
                System.out.println("--------------");
                System.out.println(repeatList);
                System.out.println("--------------");

                // Then check this structure have middle man or not
                // We have to remove employee in repeatList, as handlers has common contact which is employee
                repeatList.remove(matchList.get(i).employee.getId());
                if(matchList.size() == 1){
                    matchList.get(i).setMiddleMan(accountList.getAccount(repeatList.firstKey()));   //only 1 key
                }
            }

            for(int k = 0; k < matchList.size(); k++){
                if(matchList.get(k).getMiddleMan() == null){
                    matchList.remove(k);                // will it be a problem? eg. I remove 3 and 4 replace 3, but next loop is 4
                }
            }
        }

        return matchList;
    }

    /**
     *      check the contact of each employee, to see if they consist of handler
     *
     * @param employee
     * @param handler
     * @return
     */
    public static ArrayList<CrimeStructure> employeeHandlerMatch (AccountList employee, AccountList handler){
        ArrayList<CrimeStructure> structureList = new ArrayList<CrimeStructure>();

        for(Account eAccount: employee.getAccountList().values()){  //give one employee every time
            CrimeStructure crimeStructure = new CrimeStructure();

            for(Account hAccount: handler.getAccountList().values()){   // then check all handlers
                if(eAccount.existContact(hAccount.getId())){
                    crimeStructure.setEmployee(eAccount);
                    crimeStructure.addHandler(hAccount);
                }
            }

            if (crimeStructure.getHandler().size() >= 3){
                structureList.add(crimeStructure);    // only need those structure with 1 employee and 3+ handler
            }else{
                crimeStructure = null;
            }

        }


        return structureList;
    }

    public static AccountList filterByContact(int rangeFrom, int rangeTo, AccountList accountList){
        AccountList al = new AccountList();

        for(Account account: accountList.getAccountList().values()){
            // Find those who are in the range
            if(   (account.getContactSize() >= rangeFrom) && ( account.getContactSize() <= rangeTo) ){
                al.addAccount(account);
            }
        }

        return al;
    }

    // static method, change the parameter pass in, but also can return to give options
    public static AccountList readFile(String idName, String idLiving, AccountList accountList) {
        ///
        try {

            //// -----First create Account, add ID and Name--------/////

            FileInputStream fis = new FileInputStream(idName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            String s;
            while (true) {
                s = dis.readLine();
                if (s != null) {
                    int id = Integer.parseInt(s.split("\t@")[0]);
                    String name = s.split("\t@")[1];
                    Account account = new Account();        /////Create Account here
                    account.setId(id);
                    account.setName(name);
                    accountList.addAccount(account);        ////Add account to accountlist
                } else {
                    break;
                }
            }
            dis.close();
            bis.close();
            fis.close();

            //////-------Then add living place, no need to create or add account--------//////

            FileInputStream fis2 = new FileInputStream(idLiving);
            BufferedInputStream bis2 = new BufferedInputStream(fis2);
            DataInputStream dis2 = new DataInputStream(bis2);
            dis2.readLine();
            dis2.readLine();         // the first two line of the file are useless, just throw it

            /////Remind that the account has been created and added above
            while (true) {
                s = dis2.readLine();
                if (s != null) {
                    int id = Integer.parseInt(s.split("\t")[0]);
                    String living = s.split("\t")[1];
                    accountList.getAccount(id).setLivingPlace(living);
                } else {
                    break;
                }
            }
            dis2.close();
            bis2.close();
            fis2.close();



        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public static void readContact(String contactFile, AccountList accountList){
        try{
            FileInputStream fis = new FileInputStream(contactFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            String s;
            dis.readLine();
            dis.readLine();             /// The first two lines are useless
            while (true) {
                s = dis.readLine();
                if (s != null ) {
                    int id1 = Integer.parseInt(s.split("\t")[0]);
                    int id2 = Integer.parseInt(s.split("\t")[1]);

                    if(id1 >6000|| id2 > 6000){  // the id>6000 is the label of City/Country, useless
                        continue;                // We better not use it to make things simple
                    }

                    accountList.addLink(id1,id2);
                    //System.out.println(id1 + "  " + id2);
                } else {
                    break;
                }
            }
            dis.close();
            bis.close();
            fis.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


