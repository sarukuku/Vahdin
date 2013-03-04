package vahdin.view;

import com.vaadin.ui.CustomLayout;

public class SuggestedMarkSubview extends Subview {

    public SuggestedMarkSubview() {

    }

    @Override
    public void show(String[] params) {
        CustomLayout sMark = new CustomLayout("single-suggested-mark-sidebar");

        setCompositionRoot(sMark);
        setStyleName("open");
        super.show(params);
    }

}
