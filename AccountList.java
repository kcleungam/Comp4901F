import java.util.HashMap;

/**
 * Created by Krauser on 21/11/2015.
 */
public class AccountList {
    public HashMap<Integer, Account> accountList;

    public AccountList(){
        accountList = new HashMap<Integer, Account>();
    }
    //Copy Constructor
    public AccountList(AccountList accountList){
        this.accountList = accountList.getAccountList();
    }

    public void setAccountList(HashMap<Integer, Account> accountList) {
        this.accountList = accountList;
    }

    public HashMap<Integer, Account> getAccountList() {
        return accountList;
    }

    public void addAccount(Account account){
        this.accountList.put(account.getProfile().getId(), account);
    }

    public Account getAccount(int id){
        return this.accountList.get(id);
    }

    ////////The following function provide a shortcut to access profile directly

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
