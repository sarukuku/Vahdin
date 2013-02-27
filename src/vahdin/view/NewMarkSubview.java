package vahdin.view;

import vahdin.component.ImageUpload;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;

public class NewMarkSubview extends Subview {

    public NewMarkSubview() {

    }

    @Override
    public void show(String[] params) {

        CustomLayout newMarkLayout = new CustomLayout("new-mark-sidebar");

        TextField title = new TextField();
        TextArea description = new TextArea();

        Upload up = new ImageUpload().createImageUpload("tissit");

        newMarkLayout.addComponent(up, "new-mark-image-input");
        newMarkLayout.addComponent(description, "new-mark-desc-textarea");
        newMarkLayout.addComponent(title, "new-mark-title-input");

        setCompositionRoot(newMarkLayout);
        addStyleName("open");
        super.show(params);
    }

}
