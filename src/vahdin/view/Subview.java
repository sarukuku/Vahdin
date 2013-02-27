package vahdin.view;

import com.vaadin.ui.CustomComponent;

abstract public class Subview extends CustomComponent {

    /**
     * Opens the subview with the given parameters.
     * 
     * This should be overwritten by the inheriting class to handle the
     * parameters appropriately.
     */
    public void show(String[] params) {
        addStyleName("open");
    }

    /** Hides the subview. */
    public void hide() {
        removeStyleName("open");
    }

}
