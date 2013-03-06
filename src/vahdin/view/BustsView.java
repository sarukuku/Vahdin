package vahdin.view;

import java.io.File;
import java.util.Date;

import vahdin.VahdinUI;
import vahdin.VahdinUI.LoginEvent;
import vahdin.VahdinUI.LoginListener;
import vahdin.data.Bust;
import vahdin.data.Mark;
import vahdin.data.User;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class BustsView extends CustomLayout implements View {

    private String markId;
    private final LoginListener loginListener;
    private final VahdinUI ui = (VahdinUI) UI.getCurrent();

    public BustsView() {
        super("single-mark-sidebar");

        // FOR TESTING ONLY
        final Mark m1 = new Mark("Markin nimi", new Date(), "Kuvaus", 1, 1);
        m1.addBust(new Bust("Title", 0, "Kuvaus", 0, "aika", 2.2, 1.1));
        m1.addBust(new Bust("Title2", 1, "Toinen kuvaus", 1, "toka aika", 3.3,
                4.4));

        VerticalLayout bustsList = new VerticalLayout();

        Label markTitle = new Label("<h2>" + m1.getTitle() + "</h2>",
                Label.CONTENT_XHTML);

        final Button newBustButton = new Button();
        newBustButton.setStyleName("new-bust-button");
        newBustButton.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/add-button.png"));
        newBustButton.addClickListener(new Button.ClickListener() {

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

        Label creationDate = new Label("<h4>" + m1.getTime() + "</h4>",
                Label.CONTENT_XHTML);

        Label ownerNick = new Label("Riku Riski");
        ownerNick.setStyleName("nickname");

        Button markUpvote = new Button();
        markUpvote.setStyleName("upvote");
        markUpvote.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/up-arrow.png"));

        Label markVotes = new Label(m1.getVoteCount() + "");
        markVotes.setStyleName("vote-count");

        Button markDownvote = new Button();
        markDownvote.setStyleName("downvote");
        markDownvote.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/down-arrow.png"));

        String markDescription = m1.getDescription();
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
                showImage(m1);
            }
        });

        for (int i = 0; i < m1.getBusts().size(); i++) {
            CustomLayout layout = new CustomLayout("bust-row");
            final int bustId = m1.getBusts().get(i).getId();

            // Title of the Bust
            Button title = new Button(m1.getBusts().get(i).getTitle());
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

            Label votes = new Label(m1.getBusts().get(i).getVoteCount() + ""); // TODO:
                                                                               // real
                                                                               // votes
                                                                               // count
            votes.setStyleName("vote-count");

            layout.addComponent(upvote, "bust-row-upvote-arrow");
            layout.addComponent(votes, "bust-row-vote-count");
            layout.addComponent(downvote, "bust-row-downvote-arrow");
            layout.addComponent(title, "bust-row-title");
            bustsList.addComponent(layout);
        }

        addComponent(markVotes, "vote-count");
        addComponent(viewImage, "view-image-button");
        addComponent(markDesc, "mark-description");
        addComponent(markUpvote, "upvote-arrow");
        addComponent(markDownvote, "downvote-arrow");
        addComponent(ownerNick, "mark-submitter-nickname");
        addComponent(creationDate, "mark-creation-date");
        addComponent(markTitle, "mark-title");
        addComponent(newBustButton, "new-bust-button");
        addComponent(back, "back-button");
        addComponent(bustsList, "busts-list");

        loginListener = new LoginListener() {
            @Override
            public void login(LoginEvent event) {
                User user = ui.getCurrentUser();
                newBustButton.setVisible(user.isLoggedIn());
            }
        };
    }

    @Override
    public void attach() {
        super.attach();
        ui.addLoginListener(loginListener);
    }

    @Override
    public void detach() {
        super.detach();
        ui.removeLoginListener(loginListener);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        loginListener.login(null); // force login actions
    }

    /*
     * Method that shows an image of a mark or bust in a new window on top of
     * the current interface.
     */
    public void showImage(Mark mark) {
        final VahdinUI ui = (VahdinUI) UI.getCurrent(); // Get main window
        final Window imagewin = new Window(); // Create the window
        imagewin.setStyleName("single-image-window"); // Set style name
        imagewin.setModal(true); // Make it modal
        VerticalLayout layout = new VerticalLayout(); // Create layout for the
                                                      // image
        Button close = new Button("Click this bar to close the image",
                new Button.ClickListener() { // Add a close button for the image
                    public void buttonClick(ClickEvent event) { // inline
                                                                // click-listener
                        ((UI) imagewin.getParent()).removeWindow(imagewin); // close
                                                                            // the
                                                                            // window
                                                                            // by
                                                                            // removing
                                                                            // it
                                                                            // from
                                                                            // the
                                                                            // parent
                                                                            // window
                    }
                });
        layout.addComponent(close);

        String basepath = VaadinService.getCurrent().getBaseDirectory()
                .getAbsolutePath();
        File directory = new File(basepath
                + "/VAADIN/themes/vahdintheme/img/contentpictures");
        String filename = "m" + mark.getId();

        if (directory.isDirectory()) { // check to make sure it is a directory
            String filenames[] = directory.list();
            for (int i = 0; i < filenames.length; i++) {
                if (filenames[i].contains(filename)) {
                    filename = filenames[i];
                    break;
                } else {
                    filename = "notfound.png";
                }
            }
        }

        filename = "../vahdintheme/img/contentpictures/" + filename;

        Embedded img = new Embedded(mark.getTitle(),
                new ThemeResource(filename));
        layout.addComponent(img);
        imagewin.setContent(layout);
        ui.addWindow(imagewin); // add modal window to main window
    }

}
