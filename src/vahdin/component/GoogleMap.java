package vahdin.component;

import java.util.UUID;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.JavaScript;

@com.vaadin.annotations.JavaScript("js/google_map.js")
public class GoogleMap extends CustomComponent {

    private final String id;

    /** Constructs a new GoogleMap with the specified API key. */
    public GoogleMap(String apiKey) {
        id = UUID.randomUUID().toString();
        setId(id);

        JavaScript.getCurrent().execute(
                "window['" + id + "'] = new GoogleMap('" + id + "', '" + apiKey
                        + "');");
    }

    /**
     * Adds a marker at the given coordinates.
     * 
     * @return The created Marker object.
     */
    public Marker addMarker(double lat, double lng) {
        String markerId = UUID.randomUUID().toString();
        JavaScript.getCurrent().execute(
                "window['" + id + "'].addMarker(" + lat + "," + lng + ",'"
                        + markerId + "');");
        return new Marker(lat, lng, markerId);
    }

    /** Removes the specified marker. */
    public void removeMarker(Marker marker) {
        JavaScript.getCurrent().execute(
                "window['" + id + "'].removeMarker('" + marker.id + "')");
    }

    public class Marker {

        public final double latitude;
        public final double longitude;

        private final String id;

        /** Constructs a new marker with the given coordinates. */
        private Marker(double lat, double lng, String id) {
            latitude = lat;
            longitude = lng;
            this.id = id;
        }
    }
}
