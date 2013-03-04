package vahdin.view;

import vahdin.component.ImageUpload;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;

public class NewMarkView extends CustomLayout implements View {

    public NewMarkView() {
        super("new-mark-sidebar");

        final TextField title = new TextField();
        final TextArea description = new TextArea();
        Upload up = new ImageUpload().createImageUpload("tissit");

        Button cancel = new Button("Cancel");
        cancel.setStyleName("cancel-button");
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("");
            }
        });

        Button submit = new Button("Submit");
        submit.setStyleName("submit-button");
        submit.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Created new Mark with title: "
                        + title.getValue());
                // TODO: create new mark and add it to sql
            }
        });

        addComponent(submit, "new-mark-submit-button");
        addComponent(cancel, "new-mark-cancel-button");
        addComponent(up, "new-mark-image-input");
        addComponent(description, "new-mark-desc-textarea");
        addComponent(title, "new-mark-title-input");
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }
}
