/**
 * Created by Krauser on 26/11/2015.
 */
public class ChunkLink {
        int Source;
        int Target;
        int value = 1;

        public ChunkLink(Profile p1, Profile p2){
            Source = p1.getId();
            Target = p2.getId();
        }
}
