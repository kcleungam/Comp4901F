import java.lang.String;

/**
 * Created by Krauser on 21/11/2015.
 */


public class Profile {
    public int id;
    public String name;
    public String livingPlace;

    public Profile(){
        id = -1;
        name = "";
        livingPlace = "";
    }

    // Copy Constructor
    public Profile(Profile profile){
        this.id = profile.id;
        this.name = profile.name;
        this.livingPlace = profile.livingPlace;
    }

    public Profile(int id, String name, String livingPlace){
        this.id = id;
        this.name = name;
        this.livingPlace = livingPlace;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLivingPlace(String livingPlace) {
        this.livingPlace = livingPlace;
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
}
