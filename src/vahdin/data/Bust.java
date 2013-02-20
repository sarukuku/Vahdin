package vahdin.data;

public class Bust {

    private String title;
    private int id;
    private String description;
    private int imageId;
    private String time;
    private double locationLat;
    private double locationLon;

    public Bust(String title, int id, String desc, int imgId, String time,
            double lat, double lon) {
        this.title = title;
        this.id = id;
        this.description = desc;
        this.imageId = imgId;
        this.time = time;
        this.locationLat = lat;
        this.locationLon = lon;
    }

    public String getTitle() {
        return this.title;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public int getImageId() {
        return this.imageId;
    }

    public String getTime() {
        return this.time;
    }

    public double getLocationLat() {
        return this.locationLat;
    }

    public double getLocationLon() {
        return this.locationLon;
    }

}
