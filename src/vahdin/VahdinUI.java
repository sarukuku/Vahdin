package vahdin;

import vahdin.component.GoogleMap;
import vahdin.layout.MenuBar;
import vahdin.layout.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Main UI class
 */
@Theme("vahdintheme")
@SuppressWarnings("serial")
public class VahdinUI extends UI {

    private static final String GOOGLE_MAPS_API_KEY = "AIzaSyD723LQ68aCdI37_yhUNDQVHj3zzAfPDVo";

    /** Initializes the UI. */
    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Vahdin");

        GoogleMap map = new GoogleMap(GOOGLE_MAPS_API_KEY);
        map.setSizeFull();
        VerticalLayout sidebar = new VerticalLayout();
        MenuBar menu = new MenuBar();

        CustomLayout layout = new CustomLayout("main");
        layout.addComponent(menu, "menu");
        layout.addComponent(map, "map");
        layout.addComponent(sidebar, "sidebar");
        layout.setSizeFull();

        setContent(layout);
        ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(
                sidebar);
        Navigator navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addView("", SideBar.class);
    }
}