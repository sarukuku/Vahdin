package vahdin.data;

/** Interface for objects that can be voted on. */
public interface Votable {

    /** @return An ID that can be used to reference the object. */
    public int getId();

    /** @return A name that can be used to reference the type of object. */
    public String getVotableName();
}
