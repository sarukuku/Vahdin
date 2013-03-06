package vahdin.view;

import vahdin.data.Bust;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class SingleBustView extends CustomLayout implements View {

    private int markId;
    private int bustId;

    public SingleBustView() {
        super("single-bust-sidebar");

    }

    @Override
    public void enter(ViewChangeEvent event) {

        String[] s = event.getParameters().split("/");
        markId = Integer.parseInt(s[0]);
        bustId = Integer.parseInt(s[1]);

        Bust bust = Bust.getBustById(bustId);

        Label title = new Label("<h2>" + bust.getTitle() + "</h2>",
                Label.CONTENT_XHTML);
        Label date = new Label("<h4>" + bust.getTime() + "</h4>",
                Label.CONTENT_XHTML);
        Label user = new Label("Riku Riski");
        user.setStyleName("nickname");

        Button voteup = new Button();
        voteup.setStyleName("upvote");
        voteup.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/up-arrow.png"));
        voteup.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                // TODO Auto-generated method stub
                Notification.show("Voteup clicked");
            }
        });

        Label votes = new Label("2");
        votes.setStyleName("vote-count");

        Button votedown = new Button();
        votedown.setStyleName("downvote");
        votedown.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/down-arrow.png"));
        votedown.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                // TODO Auto-generated method stub
                Notification.show("Votedown clicked");
            }
        });

        Button delete = new Button();
        delete.setStyleName("new-mark-button");
        delete.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/delete-button.png"));
        delete.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                // TODO Auto-generated method stub
                Notification.show("Delete clicked");
            }
        });

        Button back = new Button();
        back.setStyleName("go-back-button");
        back.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/back-button.png"));
        back.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo("busts/" + markId + "/");
            }
        });

        Label desc = new Label("<p>" + bust.getDescription() + "</p>",
                Label.CONTENT_XHTML);
        desc.setStyleName("mark-description");

        Button viewImage = new Button("View image");
        viewImage.setStyleName("view-image-button");
        viewImage.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                // TODO Auto-generated method stub
                Notification.show("View image");
            }
        });

        addComponent(title, "bust-title");
        addComponent(date, "bust-datetime");
        addComponent(user, "bust-submitter-nickname");
        addComponent(voteup, "bust-upvote-arrow");
        addComponent(votes, "bust-vote-count");
        addComponent(votedown, "bust-downvote-arrow");
        addComponent(delete, "bust-delete-button");
        addComponent(back, "bust-back-button");
        addComponent(desc, "bust-description");

    }
}
