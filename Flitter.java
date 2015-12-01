import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
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

        ////Read data from file
        readFile(idName,idLiving, accountList);
        readContact(contactFile, accountList);
        international(accountList);
        //accountList.printAll();
        accountList.printContactOf(4994);       //// works perfectly

        //TODO Store the accountList which store all account into JSON, name as AccountList
        //toJson(accountList, "AccountList.json");

        System.out.println("--------------------Handler------------------");
        AccountList handlerList = new AccountList();
        handlerList = filterByContact(30,40,accountList);   //test success, work perfectly
        //handlerList.printAll();

        //TODO Please store it into JSON, name as HandlerList
        //toJson(handlerList, "HandlerList.json");


        System.out.println("--------------------Employee------------------");
        AccountList employeeList = new AccountList();
        employeeList = filterByContact(38,42,accountList);
        //employeeList.printAll();
        // TODO Please store it into   JSON, name as EmployeeList
        //toJson(employeeList, "EmployeeList.json");

        EHMatchJson(employeeList, handlerList, 38, 42);


        System.out.println("--------------Match Employee Handler set------------------");

        ArrayList<CrimeStructure> structureList = new ArrayList<CrimeStructure>();
        structureList = employeeHandlerMatch(employeeList,handlerList);
        /*
        for(int i = 0 ; i < structureList.size();i++){
            structureList.get(i).printAll();            // Work perfectly, you can change the employee list range form 39-41 to play
        }
        */

        // TODO Store this ArrayList< CrimeStructure>  into JSON, Remind that StructureList is different from AccountList, name as EmployeeHandlerMatch
        //toJson(structureList,"EmployeeHandlerMatch.json");
        middleManJson(structureList);

        System.out.println("-------------Find Middleman---------------");

        structureList = findMiddleMan(structureList,accountList);
        /*
        for(int i = 0; i< structureList.size(); i++){
            structureList.get(i).printAll();
        }
        */
        // TODO Store this ArrayList< CrimeStructure>  into JSON, Remind that StructureList is different from AccountList, name as Middleman
        //toJson(structureList, "Middleman.json");
        excelFearlessLeader(structureList, true);
        excelFearlessLeader(structureList, false);

        System.out.println("--------------Find Fearless Leader-----------------");

        for(CrimeStructure crimeStructure: structureList){
            for(Integer key: crimeStructure.getMiddleMan().getContact().keySet()){
                if(crimeStructure.getMiddleMan().getContact().get(key).getContactSize() > 100){
                    if(crimeStructure.getMiddleMan().getContact().get(key).consistInternationalLink){
                        crimeStructure.setFearlessLeader(accountList.getAccount(key));
                    }
                }
            }
        }

        /*
        for(int i = 0; i< structureList.size(); i++){
            structureList.get(i).printAll();
        }
        */

        //TODO TODO Store this ArrayList< CrimeStructure>  into JSON, Remind that StructureList is different from AccountList, name as FullStructure
        //toJson(structureList, "FullStructure.json");
        fullStructureJson(structureList);

    }

    public static void excelFearlessLeader(ArrayList<CrimeStructure> structureList, Boolean withBoolean){
        int count = 0;
        for(CrimeStructure crime: structureList) {
            System.out.println(crime.getMiddleMan().getId());
            if (withBoolean) {
                try {
                    FileWriter writer = new FileWriter("FearlessLeader_WithBoolean_"+ count + ".csv");
                    writer.write("id,status,count\n");
                    for(Profile profile: crime.getMiddleMan().getContact().values()){
                        writer.write(profile.getId() + ","+profile.checkInternationalLink() + "," + profile.getContactSize());
                        writer.write("\n");
                    }

                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    FileWriter writer = new FileWriter("FearlessLeader_" + count +".csv");
                    writer.write("id,count\n");
                    for(Profile profile: crime.getMiddleMan().getContact().values()){
                        writer.write(profile.getId() + "," + profile.getContactSize());
                        writer.write("\n");
                    }

                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            count++;
        }
    }


    public static void EHMatchJson(AccountList employeeList, AccountList handlerList, int rangeFrom, int rangeTo){

        Boolean firstElement = true;

        try {

            //write converted json data to a file named "file.json"
            FileWriter writer = new FileWriter("EmployeeHandler_" + rangeFrom +"to"+ rangeTo + ".json");
            writer.write("{\n");


            for(Account eAccount: employeeList.getAccountList().values()){
                for(Account hAccount: handlerList.getAccountList().values()){
                    if(eAccount.existContact(hAccount.getId())){
                        if(firstElement == true){
                            writer.write(hAccount.getId()+ ":{");
                            writer.write("employee:");
                            writer.write(  "\"" +  eAccount.getId()+  "\"}");
                            firstElement = false;
                        }else{
                            writer.write(",\n");
                            writer.write(hAccount.getId()+ ":{");
                            writer.write("employee:");
                            writer.write("\"" + eAccount.getId() + "\"}");
                        }
                    }
                }
            }

            writer.write("\n}");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     *          Basically this function do the following things:
     *          1. Chunk the useful part of Profile into chunkProfile
     *          2. add all people's chunkProfile related to the structure into one ArrayList
     *          3. Construct link table according to the structure
     *
     * @param structureList
     */

    public static void fullStructureJson(ArrayList<CrimeStructure> structureList){

        int count = 0;
        for(CrimeStructure crimeStructure: structureList){
            ArrayList<ChunkProfile> allProfile = new ArrayList<ChunkProfile>();
            ArrayList<ChunkLink> allLink = new ArrayList<ChunkLink>();

            ChunkProfile chunkProfile;
            ChunkLink chunkLink;
            ///Employee
            if(crimeStructure.getEmployee().getProfile().getId() != -1){
                //// Add employee himself
                chunkProfile = new ChunkProfile(crimeStructure.getEmployee().getProfile());
                if(compare(allProfile,chunkProfile)){
                    // don't put it in
                }else{
                    allProfile.add(chunkProfile);

                }
                // then other contact
                for(Profile p: crimeStructure.getEmployee().getContact().values()){
                    chunkProfile = new ChunkProfile(p);
                    if(compare(allProfile,chunkProfile)){

                    }else{
                        allProfile.add(chunkProfile);
                    }

                    chunkLink = new ChunkLink(crimeStructure.getEmployee().getProfile(),p);
                    if(compareLink(allLink,chunkLink)){

                    }else{
                        allLink.add(chunkLink);
                    }
                }
            }
            /////// Handler
            for(Account account: crimeStructure.getHandler()){
                if(account.getProfile().getId() != -1){
                    chunkProfile = new ChunkProfile(account.getProfile());
                    if(compare(allProfile,chunkProfile)){
                        // don't put it in
                    }else{
                        allProfile.add(chunkProfile);
                    }
                }

                for(Profile p: account.getContact().values()) {

                    chunkProfile = new ChunkProfile(p);
                    if(compare(allProfile,chunkProfile)){
                    }else{
                        allProfile.add(chunkProfile);
                    }

                    chunkLink = new ChunkLink(account.getProfile(), p);
                    if (compareLink(allLink,chunkLink)) {

                    } else {
                        allLink.add(chunkLink);
                    }
                }
            }

            ////// MiddleMan
            if(crimeStructure.getMiddleMan().getProfile().getId() != -1){
                chunkProfile = new ChunkProfile(crimeStructure.getMiddleMan().getProfile());
                if(compare(allProfile,chunkProfile)){
                    // don't put it in
                } else{
                    allProfile.add(chunkProfile);
                }
                for(Profile p: crimeStructure.getMiddleMan().getContact().values()){
                    chunkProfile = new ChunkProfile(p);
                    if(compare(allProfile,chunkProfile)){

                    }else{
                        allProfile.add(chunkProfile);
                    }

                    chunkLink = new ChunkLink(crimeStructure.getMiddleMan().getProfile(), p);
                    if (compareLink(allLink,chunkLink)) {

                    } else {
                        allLink.add(chunkLink);
                    }
                }
            }

            ///////    Fearless Leader
            if (crimeStructure.getFearlessLeader().getId() != -1){
                chunkProfile = new ChunkProfile(crimeStructure.getFearlessLeader().getProfile());
                if(compare(allProfile,chunkProfile)){
                    // don't put it in
                }else{
                    allProfile.add(chunkProfile);
                }
            }

            for(Profile p: crimeStructure.getFearlessLeader().getContact().values()){
                chunkProfile = new ChunkProfile(p);
                if(compare(allProfile,chunkProfile)){

                }else{
                    allProfile.add(chunkProfile);
                }

                chunkLink = new ChunkLink(crimeStructure.getFearlessLeader().getProfile(), p);
                if (compareLink(allLink,chunkLink)) {

                } else {
                    allLink.add(chunkLink);
                }
            }
            try {
                Thread.sleep(100);
            }catch (Exception e){}

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try {
                String chunkJson = gson.toJson(allProfile);
                String linkJson = gson.toJson(allLink);
                //write converted json data to a file named "file.json"
                FileWriter writer = new FileWriter("FullStructure_" + count +".json");
                writer.write("{\n");
                writer.write("\"nodes\":");
                writer.write(chunkJson);
                writer.write(",\n");
                writer.write("\"links\":");
                writer.write(linkJson);
                writer.write("\n}");
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            count++;
        }

    }

    public static void middleManJson(ArrayList<CrimeStructure> structureList){
        int count = 0;
        for(CrimeStructure crimeStructure: structureList){
            ArrayList<ChunkProfile> allProfile = new ArrayList<ChunkProfile>();
            ArrayList<ChunkLink> allLink = new ArrayList<ChunkLink>();

            ChunkProfile chunkProfile;
            ChunkLink chunkLink;
            /////// Handler


            for(Account account: crimeStructure.getHandler()){
                if(account.getProfile().getId() != -1 ){
                    chunkProfile = new ChunkProfile(account.getProfile());
                    if(compare(allProfile,chunkProfile)){
                        // don't put it in
                    }else{
                        allProfile.add(chunkProfile);
                    }
                }

                for(Profile p: account.getContact().values()) {
                    // Skip the contact handler-employee
                    if(p.getId() == crimeStructure.getEmployee().getId()){
                        continue;
                    }
                    chunkProfile = new ChunkProfile(p);
                    if(compare(allProfile,chunkProfile)){

                    }else{
                        allProfile.add(chunkProfile);
                    }

                    chunkLink = new ChunkLink(account.getProfile(), p);
                    if (compareLink(allLink,chunkLink)) {

                    } else {
                        allLink.add(chunkLink);
                    }
                }
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try {
                String chunkJson = gson.toJson(allProfile);
                String linkJson = gson.toJson(allLink);
                //write converted json data to a file named "file.json"
                FileWriter writer = new FileWriter("Middleman_" + count +".json");
                writer.write("{\n");
                writer.write("\"nodes\":");
                writer.write(chunkJson);
                writer.write(",\n");
                writer.write("\"links\":");
                writer.write(linkJson);
                writer.write("\n}");
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            count++;
        }

    }

    public static Boolean compare(ArrayList<ChunkProfile> list,ChunkProfile chunkProfile){
        Boolean same = false;
        for(ChunkProfile profile: list){
            if(profile.id.equals(chunkProfile.id)){
                same = true;
            }
        }
        return same;
    }

    public static Boolean compareLink(ArrayList<ChunkLink> list, ChunkLink chunkLink){
        Boolean same = false;
        for(ChunkLink link: list){
            if(link.source.equals(chunkLink.source)){
                if(link.target.equals(chunkLink.target)){
                    same = true;
                }
            } else if(link.source.equals((chunkLink.target))){

                if(link.target.equals(chunkLink.source)){
                    same = true;
                }
            }
        }

        return same;
    }


    public static void toJson(AccountList accountList, String fileName){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(accountList);

        try {
            //write converted json data to a file named "file.json"
            FileWriter writer = new FileWriter(fileName);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void toJson(ArrayList<CrimeStructure> structureList, String fileName){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(structureList);

        try {
            //write converted json data to a file named "file.json"
            FileWriter writer = new FileWriter(fileName);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *       This function is a little bit complicate. We have to find the middleMan in each crimeStructure
     *      So first, we get each crimeStructure, then we get all handlers ( 3 or above)  in that structure
     *      The method to find common Contact is simple,  add all the contact of these 3 handlers into one
     *      When each handler add his contact list to combineContact, there will be repetition, count the repetition in repeatList
     *       1 means it appear once, 3 means it appear three times( that's what we want)
     *
     * @param matchList      is the crime structure generated after employee handler match
     * @param accountList   is the list store all account
     * @return
     */

    public static ArrayList<CrimeStructure> findMiddleMan(ArrayList<CrimeStructure> matchList, AccountList accountList){

        for(int i = 0; i < matchList.size(); i++){
            SortedMap<Integer,Integer> repeatList = new TreeMap<Integer,Integer>();     // store  { id, repeat count}
            ArrayList<Integer> combineContact = new ArrayList<Integer>();               // store contact of all handler in one crimeStructure

            for(int j = 0; j < matchList.get(i).getHandler().size(); j++){
                SortedMap<Integer,Profile> oneHandlerContactList = matchList.get(i).getHandler().get(j).getContact();

                for(Profile profile: oneHandlerContactList.values()){
                    if(combineContact.contains(profile.getId())){
                        //get the old value(repeat count in repeatList) and +1
                        repeatList.replace(profile.getId(), (repeatList.get(profile.getId())) + 1);

                    } else{
                        combineContact.add(profile.getId());            // add to the conbineContact if it is first appear
                        repeatList.put(profile.getId(),1);
                    }

                }
            }
            // Then check this structure have middle man or not
            // We have to remove employee in repeatList, as handlers has common contact which is employee
            repeatList.remove(matchList.get(i).employee.getId());
            //Set the middleMan
            for(Integer id : repeatList.keySet()) {
                if(repeatList.get(id) >=3) {     //check repeat count, repeat 3 times means three handlers has this contact
                    matchList.get(i).setMiddleMan(accountList.getAccount(id));
                }
            }

        }

        // Remove those structure which has no middleman
        //// Can not delete directly, otherwise would have concurrent problem
        ArrayList<CrimeStructure> markDelete = new ArrayList<CrimeStructure>();
        for(CrimeStructure cs: matchList){
            if(cs.getMiddleMan().getId() == -1){
                markDelete.add(cs);
            }
        }
        for(int i = 0; i < markDelete.size(); i++){
            matchList.remove(markDelete.get(i));
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

    /**
     *
     *      This function filter the account which  the number of contact is within the range
     *       eg. filter those have 30-40 contact  ------> handler
     *
     * @param rangeFrom
     * @param rangeTo
     * @param accountList
     * @return
     */
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

            for(Account account: accountList.getAccountList().values()){
                for(Profile profile: account.getContact().values()){    // set the contact size of profile in contact
                    profile.setContactSize(accountList.getAccount(profile.getId()).getContactSize());
                }
            }
            dis.close();
            bis.close();
            fis.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     *      Check if there are international contact in each account, if so, change the Boolean "consistInternationalContact" into true
     *
     * @param accountList
     */
    public static void international(AccountList accountList){
        for(Account account: accountList.getAccountList().values()){
            for(Profile profile: account.getContact().values()){    // Profile in contact
                if(profile.checkLiveForeign() != account.getProfile().checkLiveForeign()){  // profile in account's contact != profile in account
                    account.setInternationalLink(true);
                }
            }
        }

    }
}


