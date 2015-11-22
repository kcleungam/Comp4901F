import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

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
        //accountList.printAll();
        readContact(contactFile, accountList);
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        accountList.printContact(805);
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
                    System.out.println(id1 + "  " + id2);
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


