package vahdin.view;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class NewMarkSubview extends Subview {

    public NewMarkSubview() {

    }

    @Override
    public void show(String[] params) {

        CustomLayout newMarkLayout = new CustomLayout("new-mark-sidebar");

        TextField title = new TextField();
        TextArea description = new TextArea();

        newMarkLayout.addComponent(description, "new-mark-desc-textarea");
        newMarkLayout.addComponent(title, "new-mark-title-input");

        setCompositionRoot(newMarkLayout);
        addStyleName("open");
        super.show(params);
    }

}
