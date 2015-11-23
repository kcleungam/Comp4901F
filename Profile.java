import java.lang.String;

/**
 * Created by Krauser on 21/11/2015.
 */


public class Profile {
    public int id;
    public String name;
    public String livingPlace;
    public Boolean isInternational;

    public Profile(){
        id = -1;
        name = "";
        livingPlace = "";
        isInternational = false;
    }

    // Copy Constructor
    public Profile(Profile profile){
        this.id = profile.id;
        this.name = profile.name;
        this.livingPlace = profile.livingPlace;
        this.isInternational = profile.isInternational;
    }

    public Profile(int id, String name, String livingPlace){
        this.id = id;
        this.name = name;
        this.livingPlace = livingPlace;
        this.setInternational(livingPlace);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLivingPlace(String livingPlace) {
        this.livingPlace = livingPlace;
        this.setInternational(livingPlace);
    }

    public void setInternational(String livingPlace){
        if(livingPlace.equals("Otello") || livingPlace.equals("Transpasko") || livingPlace.equals("Tulamuk")
                || livingPlace.equals("Posana") || livingPlace.equals("Transak") || livingPlace.equals("Trium")){
            this.isInternational = true;
        }
    }

    public Boolean checkInternational(){
        return this.isInternational;
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
