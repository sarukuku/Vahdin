package vahdin.view;

import java.io.File;
import java.util.List;

import vahdin.VahdinUI;
import vahdin.VahdinUI.LoginEvent;
import vahdin.VahdinUI.LoginListener;
import vahdin.component.VoteButton;
import vahdin.data.Bust;
import vahdin.data.Mark;
import vahdin.data.User;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class BustsView extends CustomLayout implements View {

    private int markId;
    private final LoginListener loginListener;
    private final VahdinUI ui = (VahdinUI) UI.getCurrent();
    private Button newBustButton;

    public BustsView() {
        super("single-mark-sidebar");

        loginListener = new LoginListener() {
            @Override
            public void login(LoginEvent event) {
                User user = ui.getCurrentUser();
                newBustButton.setVisible(user.isLoggedIn());
            }
        };
    }

    @Override
    public void enter(ViewChangeEvent event) {

        String[] s = event.getParameters().split("/");
        markId = Integer.parseInt(s[0]);

        final Mark mark = Mark.getMarkById(markId);
        List<Bust> busts = Bust.getBustByMarkId(markId);

        VerticalLayout bustsList = new VerticalLayout();

        Label markTitle = new Label("<h2>"
                + SafeHtmlUtils.htmlEscape(mark.getTitle()) + "</h2>",
                ContentMode.HTML);

        newBustButton = new Button();
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

        Label creationDate = new Label("<h4>" + mark.getTime() + "</h4>",
                ContentMode.HTML);

        Label ownerNick = new Label(User.getUserById(mark.getUserID())
                .getName());
        ownerNick.setStyleName("nickname");

        final Label markVotes = new Label((int) mark.getVoteCount() + "");
        markVotes.setStyleName("vote-count");

        final Button markUpvoteButton = new VoteButton(mark, VoteButton.Type.UP);
        markUpvoteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                markVotes.setValue((int) mark.getVoteCount() + "");
            }
        });

        final Button markDownvoteButton = new VoteButton(mark,
                VoteButton.Type.DOWN);
        markDownvoteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                markVotes.setValue((int) mark.getVoteCount() + "");
            }
        });

        String markDescription = mark.getDescription();
        if (markDescription.length() > 310) {
            markDescription.substring(0, 309);
        }
        Label markDesc = new Label("<p>"
                + SafeHtmlUtils.htmlEscape(markDescription) + "</p>",
                ContentMode.HTML);
        markDesc.setStyleName("mark-description");

        Button viewImage = new Button("View image");
        viewImage.setStyleName("view-image-button");
        viewImage.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                showImage(mark);
            }
        });

        for (int i = busts.size() - 1; i >= 0; i--) {
            CustomLayout layout = new CustomLayout("bust-row");
            final int bustId = busts.get(i).getId();
            final Bust bust = Bust.getBustById(bustId);

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

            final Label votes = new Label((int) bust.getVoteCount() + "");
            votes.setStyleName("vote-count");

            final Button upvoteButton = new VoteButton(bust, VoteButton.Type.UP);
            upvoteButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    votes.setValue((int) bust.getVoteCount() + "");
                }
            });

            final Button downvoteButton = new VoteButton(bust,
                    VoteButton.Type.DOWN);
            downvoteButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    votes.setValue((int) bust.getVoteCount() + "");
                }
            });

            layout.addComponent(upvoteButton, "bust-row-upvote-arrow");
            layout.addComponent(votes, "bust-row-vote-count");
            layout.addComponent(downvoteButton, "bust-row-downvote-arrow");
            layout.addComponent(title, "bust-row-title");
            bustsList.addComponent(layout);
        }

        addComponent(markVotes, "vote-count");
        addComponent(viewImage, "view-image-button");
        addComponent(markDesc, "mark-description");
        addComponent(markUpvoteButton, "upvote-arrow");
        addComponent(markDownvoteButton, "downvote-arrow");
        addComponent(ownerNick, "mark-submitter-nickname");
        addComponent(creationDate, "mark-creation-date");
        addComponent(markTitle, "mark-title");
        addComponent(newBustButton, "new-bust-button");
        addComponent(back, "back-button");
        addComponent(bustsList, "busts-list");

        loginListener.login(null); // force login actions

        ui.showBusts(mark);
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
        ui.clearMap();
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

        String basepath = System.getProperty("user.home");
        File imgDirectory = new File(basepath + "/contentimgs");
        String lookingForFilename = "m" + mark.getId();
        String tempFilename = null;
        String finalFilename = null;

        if (imgDirectory.isDirectory()) { // check to make sure it is a
                                          // directory
            String filenames[] = imgDirectory.list();
            for (int i = 0; i < filenames.length; i++) {
                if (filenames[i].contains(lookingForFilename)) {
                    tempFilename = filenames[i];
                    break;
                }
            }
        }

        if (tempFilename != null) {
            finalFilename = basepath + "/contentimgs/" + tempFilename;
            FileResource resource = new FileResource(new File(finalFilename));
            Image img = new Image(mark.getTitle(), resource);
            layout.addComponent(img);
        } else {
            Embedded img = new Embedded(mark.getTitle(), new ThemeResource(
                    "../vahdintheme/img/notfound.jpg"));
            layout.addComponent(img);
        }

        imagewin.setContent(layout);
        ui.addWindow(imagewin); // add modal window to main window
    }

}
