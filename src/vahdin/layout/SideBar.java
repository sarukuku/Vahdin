package vahdin.layout;

import vahdin.VahdinUI;
import vahdin.view.BustsView;
import vahdin.view.MarksView;
import vahdin.view.NewBustView;
import vahdin.view.NewMarkView;
import vahdin.view.SingleBustView;
import vahdin.view.Subview;
import vahdin.view.SuggestedMarkSubview;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;

public class SideBar extends CustomLayout implements View {

    private static final String defaultSubview = "list-view-container";

    private Subview currentSubview = null;

    /** Constructs the sidebar and its subviews. */
    public SideBar() {
        super("sidebar-container");
        addComponent(new MarksView(), "list-view-container");
        addComponent(new BustsView(), "busts");
        addComponent(new NewMarkView(), "newmark");
        addComponent(new NewBustView(), "newbust");
        addComponent(new SingleBustView(), "bust");
        addComponent(new SuggestedMarkSubview(), "suggested");

    }

    /** Opens a subview. */
    @Override
    public void enter(ViewChangeEvent event) {
        VahdinUI ui = (VahdinUI) UI.getCurrent();
        String viewParams = event.getParameters();

        // get the new subview
        String[] params = {};
        Subview target = null;
        if (viewParams != null && !"".equals(viewParams)) {
            params = viewParams.split("/");
            if (!"".equals(params[0])) {
                target = (Subview) getComponent(params[0]);
            }
        }
        if (target == null) {
            target = (Subview) getComponent(defaultSubview);
        }

        if (currentSubview != null) {
            currentSubview.hide();
        }
        target.show(params);
        currentSubview = target;
    }
}
