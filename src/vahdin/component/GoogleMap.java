package vahdin.component;

import java.util.UUID;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.JavaScript;

@com.vaadin.annotations.JavaScript("js/google_map.js")
public class GoogleMap extends CustomComponent {

    public GoogleMap(String apiKey) {
        String id = UUID.randomUUID().toString();
        setId(id);

        JavaScript.getCurrent().execute(
                "new GoogleMap('" + id + "', '" + apiKey + "');");
    }
}
