import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Krauser on 21/11/2015.
 */

public class Account{
    public Profile profile;
    public SortedMap<Integer,Profile> contact;

    public Account(){
        profile = new Profile();
        contact = new TreeMap<Integer, Profile>();
    }

    public Account(Profile profile, SortedMap<Integer, Profile> contact){
        this.profile = profile;
        this.contact = contact;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setContact(SortedMap<Integer, Profile> contact) {
        this.contact = contact;
    }

    public SortedMap<Integer, Profile> getContact() {
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

    //// This function is very important, it check how many contact in an account
    public int getContactSize(){
        return contact.size();
    }

    ////////// The follwing function provide the shortcut to access profile directly
    public void setId(int id){
        this.getProfile().setId(id);
    }

    public void setName(String name){
        this.getProfile().setName(name);
    }

    public void setLivingPlace(String livingPlace){
        this.getProfile().setLivingPlace(livingPlace);
    }
    public int getId(){
        return this.getProfile().getId();
    }

    public String getName(){
        return this.getProfile().getName();
    }

    public String getLivingPlace(){
        return this.getProfile().getLivingPlace();
    }

    public Boolean checkInternational(){
        return this.getProfile().checkInternational();
    }

}
