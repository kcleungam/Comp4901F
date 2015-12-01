/**
 * Created by Krauser on 26/11/2015.
 */
public class ChunkLink {
        String source;
        String target;
        int value = 1;

        public ChunkLink(Profile p1, Profile p2){
            source = String.valueOf(p1.getId());
            target = String.valueOf(p2.getId());
        }
}
