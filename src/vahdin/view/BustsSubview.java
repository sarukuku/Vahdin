package vahdin.view;

import java.util.Date;

import vahdin.data.Bust;
import vahdin.data.Mark;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class BustsSubview extends Subview {

    public BustsSubview() {

    }

    @Override
    public void show(String[] params) {
        // FOR TESTING ONLY
        Mark m1 = new Mark("Markin nimi", new Date(), "Kuvaus", 1, 1);
        m1.addBust(new Bust("Title", 0, "Kuvaus", 0, "aika", 2.2, 1.1));
        m1.addBust(new Bust("Title2", 1, "Toinen kuvaus", 1, "toka aika", 3.3,
                4.4));

        // String view = params[0];
        // String markId = params[1];

        CustomLayout bustsList = new CustomLayout("single-mark-sidebar");
        VerticalLayout tmp = new VerticalLayout();

        Label markTitle = new Label("<h2>" + m1.getTitle() + "</h2>",
                Label.CONTENT_XHTML);

        Button newBust = new Button();
        newBust.setStyleName("new-mark-button");
        newBust.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/add-button.png"));
        newBust.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                // TODO Auto-generated method stub
                Notification.show("Adding new Bust");
            }
        });

        Button back = new Button();
        back.setStyleName("back-button.png");
        back.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/back-button.png"));
        back.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("/");
            }
        });

        for (int i = 0; i < m1.getBusts().size(); i++) {
            CustomLayout layout = new CustomLayout("bust-row");

            // Title of the Bust
            Label title = new Label("<h2>" + m1.getBusts().get(i).getTitle()
                    + "</h2>", Label.CONTENT_XHTML);

            layout.addComponent(title, "bust-row-title");
            tmp.addComponent(layout);
        }

        bustsList.addComponent(markTitle, "mark-title");
        bustsList.addComponent(newBust, "new-bust-button");
        bustsList.addComponent(back, "back-button");
        bustsList.addComponent(tmp, "busts-list");
        setCompositionRoot(bustsList);

        addStyleName("open");
        super.show(params);
    }
}
