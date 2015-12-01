/**
 * Created by Krauser on 26/11/2015.
 */
public class ChunkLink {
        String Source;
        String Target;
        String value = "1";

        public ChunkLink(Profile p1, Profile p2){
            Source = String.valueOf(p1.getId());
            Target = String.valueOf(p2.getId());
        }
}
