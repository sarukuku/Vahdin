package vahdin.layout;

import vahdin.VahdinUI;
import vahdin.view.BustsSubview;
import vahdin.view.MarksSubview;
import vahdin.view.NewBustSubview;
import vahdin.view.NewMarkSubview;
import vahdin.view.SingleBustSubview;
import vahdin.view.Subview;
import vahdin.view.SuggestedMarkSubview;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;

public class SideBar extends CustomLayout implements View {

    private static final String defaultSubview = "list-view-container";

    /** Constructs the sidebar and its subviews. */
    public SideBar() {
        super("sidebar-container");
        addComponent(new MarksSubview(), "list-view-container");
        addComponent(new BustsSubview(), "busts");
        addComponent(new NewMarkSubview(), "newmark");
        addComponent(new NewBustSubview(), "newbust");
        addComponent(new SingleBustSubview(), "bust");
        addComponent(new SuggestedMarkSubview(), "suggested");

    }

    /** Opens a subview. */
    @Override
    public void enter(ViewChangeEvent event) {
        VahdinUI ui = (VahdinUI) UI.getCurrent();
        String newViewParams = event.getParameters();
        String oldViewParams = ui.getNavigator().getState();

        // get the new subview
        String[] params = {};
        Subview target = null;
        if (newViewParams != null && !"".equals(newViewParams)) {
            params = newViewParams.split("/");
            if (!"".equals(params[0])) {
                target = (Subview) getComponent(params[0]);
            }
        }
        if (target == null) {
            target = (Subview) getComponent(defaultSubview);
        }

        // get the old subview
        Subview source = null;
        if (oldViewParams != null && !"".equals(oldViewParams)) {
            String[] oldParams = oldViewParams.split("/");
            if (oldParams.length > 1) {
                source = (Subview) getComponent(oldParams[1]);
            }
        }
        if (source == null) {
            source = (Subview) getComponent(defaultSubview);
        }

        // do the switch
        source.hide();
        target.show(params);
    }
}
