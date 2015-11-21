import java.util.HashMap;

/**
 * Created by Krauser on 21/11/2015.
 */

public class Account{
    public Profile profile;
    public HashMap<Integer,Profile> contact;

    public Account(){
        profile = new Profile();
        contact = new HashMap<Integer, Profile>();
    }

    public Account(Profile profile, HashMap<Integer, Profile> contact){
        this.profile = profile;
        this.contact = contact;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setContact(HashMap<Integer, Profile> contact) {
        this.contact = contact;
    }

    public HashMap<Integer, Profile> getContact() {
        return contact;
    }

    public void addContact(Profile profile){
        this.contact.put(profile.getId(), profile);
    }

    public void removeContact(int id){
        this.contact.remove(id);
    }

    public void removeContact(Profile profile){
        this.contact.remove(profile.getId());
    }

    public boolean existContact(Profile profile){
        return this.contact.containsKey(profile.getId());
    }

    public boolean existContact(int id){
        return this.contact.containsKey(id);
    }

}
