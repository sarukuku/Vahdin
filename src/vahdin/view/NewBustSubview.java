package vahdin.view;

import vahdin.component.ImageUpload;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;

public class NewBustSubview extends Subview {

    public NewBustSubview() {

    }

    @Override
    public void show(String[] params) {

        CustomLayout newBustLayout = new CustomLayout("new-bust-sidebar");

        TextField title = new TextField();
        TextArea description = new TextArea();
        Upload up = new ImageUpload().createImageUpload("tissit");
        // TODO: date input
        DateField date = new DateField();
        Label lat = new Label("<h4>123.123</h4>", Label.CONTENT_XHTML);
        Label lon = new Label("<h4>456.456</h4>", Label.CONTENT_XHTML);

        Button cancel = new Button("Cancel");
        cancel.setStyleName("cancel-button");
        cancel.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("/busts/");
            }
        });

        Button submit = new Button("Submit");
        submit.setStyleName("submit-button");
        submit.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                // TODO Auto-generated method stub

            }
        });

        newBustLayout.addComponent(title, "new-bust-title-input");
        newBustLayout.addComponent(description, "new-bust-desc-texarea");
        newBustLayout.addComponent(up, "new-bust-image-input");
        newBustLayout.addComponent(date, "new-bust-datetime-input");
        newBustLayout.addComponent(lat, "latitude");
        newBustLayout.addComponent(lon, "longtitude");
        newBustLayout.addComponent(cancel, "new-bust-cancel-button");
        newBustLayout.addComponent(submit, "new-bust-submit-button");

        setCompositionRoot(newBustLayout);
        addStyleName("open");
        super.show(params);
    }
}
