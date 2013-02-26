package vahdin.view;

import java.util.Date;

import vahdin.data.Bust;
import vahdin.data.Mark;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
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
        bustsList.addComponent(markTitle, "mark-title");

        for (int i = 0; i < m1.getBusts().size(); i++) {
            CustomLayout layout = new CustomLayout("bust-row");

            // Title of the Bust
            Label title = new Label("<h2>" + m1.getBusts().get(i).getTitle()
                    + "</h2>", Label.CONTENT_XHTML);

            // Creating back-button
            Button back = new Button("back");
            back.setIcon(new ExternalResource(
                    "VAADIN/themes/vahdintheme/img/back-button.png"));
            back.setStyleName("go-back-button");
            back.addClickListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    UI.getCurrent().getNavigator().navigateTo("/");
                }
            });

            layout.addComponent(back, "back-button");
            layout.addComponent(title, "bust-row-title");
            tmp.addComponent(layout);
        }

        bustsList.addComponent(tmp, "busts-list");
        setCompositionRoot(bustsList);

        addStyleName("open");
        super.show(params);
    }
}
