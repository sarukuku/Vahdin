package vahdin.layout;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;

public class MenuBar extends CustomLayout {

    public MenuBar() {
        super("menubar");

        // TODO: create and add the necessary components
        addComponent(new Button("Moro!"), "logo-link");
    }
}