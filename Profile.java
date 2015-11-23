import java.lang.String;

/**
 * Created by Krauser on 21/11/2015.
 */


public class Profile {
    public int id;
    public int contactSize;
    public String name;
    public String livingPlace;
    public Boolean liveForeignCountry;
    public Boolean consistInternationalLink;

    public Profile(){
        id = -1;
        contactSize = -1;
        name = "";
        livingPlace = "";
        liveForeignCountry = false;
        consistInternationalLink = false;
    }

    // Copy Constructor
    public Profile(Profile profile){
        this.id = profile.id;
        this.contactSize = profile.contactSize;
        this.name = profile.name;
        this.livingPlace = profile.livingPlace;
        this.liveForeignCountry = profile.liveForeignCountry;
        this.consistInternationalLink = profile.consistInternationalLink;
    }

    public Profile(int id, String name, String livingPlace){
        this.id = id;
        this.name = name;
        this.livingPlace = livingPlace;
        this.setLiveForeignCountry(livingPlace);        // Boolean liveForeignCountry will change in this function too
        consistInternationalLink = false;           // By default, change later
        contactSize = -1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLivingPlace(String livingPlace) {
        this.livingPlace = livingPlace;
        this.setLiveForeignCountry(livingPlace);
    }

    public void setLiveForeignCountry(String livingPlace){
        if(livingPlace.equals("Otello") || livingPlace.equals("Transpasko") || livingPlace.equals("Tulamuk")
                || livingPlace.equals("Posana") || livingPlace.equals("Transak") || livingPlace.equals("Trium")){
            this.liveForeignCountry = true;
        }
    }

    public Boolean checkLiveForeign(){
        return this.liveForeignCountry;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLivingPlace() {
        return livingPlace;
    }

    public void setInternationalLink(Boolean international){
        this.consistInternationalLink = international;
    }

    public Boolean checkInternationalLink(){
        return consistInternationalLink;
    }

    public void setContactSize(int size){
        this.contactSize = size;
    }
    public int getContactSize(){
        return this.contactSize;
    }
}
