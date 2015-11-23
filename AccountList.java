import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Krauser on 21/11/2015.
 */
public class AccountList {
    public SortedMap<Integer, Account> accountList;

    public AccountList(){
        accountList = new TreeMap<Integer,Account>();
    }
    //Copy Constructor
    public AccountList(AccountList accountList){
        this.accountList = accountList.getAccountList();
    }

    public void setAccountList(SortedMap<Integer, Account> accountList) {
        this.accountList = accountList;
    }

    public SortedMap<Integer, Account> getAccountList() {
        return accountList;
    }

    public void addAccount(Account account){
        this.accountList.put(account.getProfile().getId(), account);
    }

    public Account getAccount(int id){
        return this.accountList.get(id);
    }

    ////////This function is very important, it add the contact to each other
    public void addLink(int id1, int id2){
        this.getAccount(id1).addContact(this.getProfile(id2));      // add profile of id2 to id1's contact
        this.getAccount(id2).addContact(this.getProfile(id1));     // add profile of id1 to id2's contact

    }
    public boolean checkLink(int id1, int id2){
        return this.getAccount(id1).existContact(id2);
    }

    public void printAll(){
        for (Account account: this.getAccountList().values()) {
            System.out.println(account.getId() + account.getName() + account.getLivingPlace() + account.checkInternational());
        }
        System.out.println(this.getAccountList());
    }

    public void printContactOf(int id){
        for(Profile profile: this.getAccount(id).getContact().values()){
            System.out.println(profile.getId()+profile.getName()+profile.getLivingPlace());
        }
    }

    ////////The following function provide a shortcut to access profile directly
    public Boolean checkInternational(int id){
        return this.getAccount(id).checkInternational();
    }

    public Profile getProfile(int id){
        return this.getAccount(id).getProfile();
    }

    public String getName(int id){
        return this.getAccount(id).getName();
    }

    public String getLivingPlace(int id){
        return this.getAccount(id).getLivingPlace();
    }


}
