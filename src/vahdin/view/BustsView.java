package vahdin.view;

import java.util.List;

import vahdin.VahdinUI;
import vahdin.data.Bust;
import vahdin.data.Mark;
import vahdin.data.User;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class BustsView extends CustomLayout implements View {

    final private VahdinUI ui = (VahdinUI) UI.getCurrent();

    private int markId;

    public BustsView() {
        super("single-mark-sidebar");

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        String[] s = event.getParameters().split("/");
        markId = Integer.parseInt(s[0]);

        User user = ui.getCurrentUser();
        String userId = user.getUserId();

        Mark mark = Mark.getMarkById(markId);
        List<Bust> busts = Bust.getBustByMarkId(markId);

        VerticalLayout tmp = new VerticalLayout();

        Label markTitle = new Label("<h2>" + mark.getTitle() + "</h2>",
                Label.CONTENT_XHTML);

        Button newBust = new Button();
        newBust.setStyleName("new-bust-button");
        newBust.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/add-button.png"));
        newBust.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo("newbust/" + markId + "/");
            }
        });

        Button back = new Button();
        back.setStyleName("go-back-button");
        back.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/back-button.png"));
        back.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("");
            }
        });

        Label creationDate = new Label("<h4>" + mark.getTime() + "</h4>",
                Label.CONTENT_XHTML);

        Label ownerNick = new Label("Riku Riski");
        ownerNick.setStyleName("nickname");

        Button markUpvote = new Button();
        markUpvote.setStyleName("upvote");
        markUpvote.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/up-arrow.png"));

        Label markVotes = new Label(mark.getVoteCount() + "");
        markVotes.setStyleName("vote-count");

        Button markDownvote = new Button();
        markDownvote.setStyleName("downvote");
        markDownvote.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/down-arrow.png"));

        String markDescription = mark.getDescription();
        if (markDescription.length() > 310) {
            markDescription.substring(0, 309);
        }
        Label markDesc = new Label("<p>" + markDescription + "</p>",
                Label.CONTENT_XHTML);
        markDesc.setStyleName("mark-description");

        Button viewImage = new Button("View image");
        viewImage.setStyleName("view-image-button");
        viewImage.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                // TODO Auto-generated method stub
                Notification.show("View image");
            }
        });

        for (int i = 0; i < busts.size(); i++) {
            CustomLayout layout = new CustomLayout("bust-row");
            final int bustId = busts.get(i).getId();

            // Title of the Bust
            Button title = new Button(busts.get(i).getTitle());
            title.setStyleName("mark-title");
            title.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {
                    UI.getCurrent().getNavigator()
                            .navigateTo("bust/" + markId + "/" + bustId + "/");
                }
            });

            Button upvote = new Button();
            upvote.setStyleName("upvote");
            upvote.setIcon(new ExternalResource(
                    "VAADIN/themes/vahdintheme/img/up-arrow.png"));
            upvote.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {
                    // TODO Auto-generated method stub

                }
            });

            Button downvote = new Button();
            downvote.setStyleName("downvote");
            downvote.setIcon(new ExternalResource(
                    "VAADIN/themes/vahdintheme/img/down-arrow.png"));
            downvote.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {
                    // TODO Auto-generated method stub

                }
            });

            Label votes = new Label(busts.get(i).getVoteCount() + ""); // TODO:
                                                                       // real
                                                                       // votes
                                                                       // count
            votes.setStyleName("vote-count");

            layout.addComponent(upvote, "bust-row-upvote-arrow");
            layout.addComponent(votes, "bust-row-vote-count");
            layout.addComponent(downvote, "bust-row-downvote-arrow");
            layout.addComponent(title, "bust-row-title");
            tmp.addComponent(layout);
        }

        addComponent(markVotes, "vote-count");
        addComponent(viewImage, "view-image-button");
        addComponent(markDesc, "mark-description");
        addComponent(markUpvote, "upvote-arrow");
        addComponent(markDownvote, "downvote-arrow");
        addComponent(ownerNick, "mark-submitter-nickname");
        addComponent(creationDate, "mark-creation-date");
        addComponent(markTitle, "mark-title");
        addComponent(newBust, "new-bust-button");
        addComponent(back, "back-button");
        addComponent(tmp, "busts-list");

    }
}
