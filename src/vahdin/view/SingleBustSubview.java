package vahdin.view;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;

public class SingleBustSubview extends Subview {

    public SingleBustSubview() {

    }

    @Override
    public void show(String[] params) {
        if (params.length < 2) {
            UI.getCurrent().getNavigator().navigateTo("/");
        }

        CustomLayout SingleBust = new CustomLayout("single-bust-sidebar");

        setCompositionRoot(SingleBust);
        addStyleName("open");
        super.show(params);
    }

}
