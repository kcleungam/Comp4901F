/**
 * Created by Krauser on 26/11/2015.
 */
public class ChunkProfile {
        int id;
        String name;

        public ChunkProfile(Profile profile){
            id = profile.getId();
            name = profile.getName();
        }
}
