import java.util.ArrayList;

/**
 * Created by Krauser on 23/11/2015.
 */
public class CrimeStructure {
    public Account employee;
    public ArrayList<Account> handler;
    public Account middleMan;
    public Account fearlessLeader;

    public CrimeStructure(){
        employee = new Account();
        handler = new ArrayList<Account>();
        middleMan = new Account();
        fearlessLeader = new Account();
    }

    public void setEmployee(Account employee) {
        this.employee = employee;
    }

    public Account getEmployee() {
        return employee;
    }

    public  void addHandler(Account account){
        this.handler.add(account);
    }

    public void setHandler(ArrayList<Account> handler) {
        this.handler = handler;
    }

    public ArrayList<Account> getHandler() {
        return handler;
    }

    public void setMiddleMan(Account middleMan) {
        this.middleMan = middleMan;
    }

    public Account getMiddleMan() {
        return middleMan;
    }

    public void setFearlessLeader(Account fearlessLeader) {
        this.fearlessLeader = fearlessLeader;
    }

    public Account getFearlessLeader() {
        return fearlessLeader;
    }

    public void printAll(){
        System.out.println("Employee: " + employee.getId());
        for (int i = 0; i < handler.size();i++){
            System.out.println("Handlers: " + handler.get(i).getId());
        }
        if(middleMan != null) {
            System.out.println("MiddleMan" + middleMan.getId());
        }
        if(fearlessLeader != null){
            System.out.println("Fearless Leader" + fearlessLeader.getId());
        }
    }
}
