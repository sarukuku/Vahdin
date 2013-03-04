package vahdin.view;

import com.vaadin.ui.CustomComponent;

/** Base class for the subviews shown in the sidebar. */
abstract public class Subview extends CustomComponent {

    private boolean open = false;

    /**
     * Opens the subview with the given parameters.
     * 
     * This should be overwritten by the inheriting class to handle the
     * parameters appropriately.
     */
    public void show(String[] params) {
        addStyleName("open");
        open = true;
    }

    /** Hides the subview. */
    public void hide() {
        removeStyleName("open");
        open = false;
    }

    /** Returns true if this subview is open, false otherwise. */
    public boolean isOpen() {
        return open;
    }

}
