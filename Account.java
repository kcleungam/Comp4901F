import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Krauser on 21/11/2015.
 */

public class Account{
    public Person person;
    public HashMap<int,Person> contact;

    public Account(){
        person = new Person();
        contact = new HashMap<int, Person>();
    }

    public Account(Person person, HashMap<int, Person> contact){
        this.person = person;
        this.contact = contact;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setContact(HashMap<int, Person> contact) {
        this.contact = contact;
    }

    public HashMap<int, Person> getContact() {
        return contact;
    }
    public void addContact(Person person){
        this.contact.put(person.getId(), person);
    }

    public void removeContact(int id){
        this.contact.remove(id);
    }
    public void removeContact(Person person){
        this.contact.remove(person.getId());
    }
}
