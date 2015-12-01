/**
 * Created by Krauser on 26/11/2015.
 */
public class ChunkProfile {
        String id;
        String name;

        public ChunkProfile(Profile profile){
            id = String.valueOf(profile.getId());
            name = profile.getName();
        }
}
