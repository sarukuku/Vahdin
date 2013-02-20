package vahdin.data;

import java.util.ArrayList;

public class Mark {

    private String title;
    private int id;
    private String time;
    private String description;
    private int photoId;
    private int voteCount = 0;
    private ArrayList<Bust> busts;

    public Mark(String name, String time, String description, int photoId,
            int id) {
        this.title = name;
        this.time = time;
        this.description = description;
        this.photoId = photoId;
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTime() {
        return this.time;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPhotoId() {
        return this.photoId;
    }

    public int getVoteCount() {
        return this.voteCount;
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Bust> getBusts() {
        return this.busts;
    }

    public void addBust(Bust bust) {
        this.busts.add(bust);
    }

}
