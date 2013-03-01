package vahdin.component;

import java.lang.reflect.Method;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.event.MethodEventSource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;

public class GoogleMap extends CustomComponent implements MethodEventSource {

    private final String id;

    /** Constructs a new GoogleMap with the specified API key. */
    public GoogleMap(String apiKey) {
        final GoogleMap map = this;
        id = UUID.randomUUID().toString();
        setId(id);

        JavaScript.getCurrent().addFunction("GoogleMap.click",
                new JavaScriptFunction() {

                    @Override
                    public void call(JSONArray arguments) throws JSONException {
                        fireEvent(new ClickEvent(map, arguments.getDouble(0),
                                arguments.getDouble(1)));
                    }

                });

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

    /** Centers the map at the specified coordinates. */
    public void center(double lat, double lng) {
        JavaScript.getCurrent().execute(
                "window['" + id + "'].center(" + lat + "," + lng + ");");
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

    /**
     * Adds a click listener.
     * 
     * @param listener
     *            The listener to add.
     */
    public void addClickListener(ClickListener listener) {
        addListener(ClickEvent.class, listener, ClickListener.CLICK_METHOD);
    }

    /** Click event listener. */
    public static abstract class ClickListener {

        public static final Method CLICK_METHOD;

        static {
            try {
                CLICK_METHOD = ClickListener.class.getMethod("click",
                        ClickEvent.class);
            } catch (NoSuchMethodException e) {
                throw new Error(e);
            }
        }

        /***/
        public abstract void click(ClickEvent event);

    }

    /** Click event. */
    public static class ClickEvent extends Component.Event {

        public final double latitude;
        public final double longitude;

        private ClickEvent(GoogleMap source, double lat, double lng) {
            super(source);
            latitude = lat;
            longitude = lng;
        }
    }
}
