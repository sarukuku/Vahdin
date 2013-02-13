package vahdin.component;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.JavaScript;

@com.vaadin.annotations.JavaScript("js/google_map.js")
public class GoogleMap extends CustomComponent {

    public GoogleMap(String apiKey) {
        CustomLayout layout = new CustomLayout("google_map");
        setCompositionRoot(layout);
        setSizeFull();
        layout.setSizeFull();

        JavaScript.getCurrent().execute("new GoogleMap('" + apiKey + "');");
    }
}
