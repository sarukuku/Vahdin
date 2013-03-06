package vahdin.view;

import java.io.File;

import vahdin.VahdinUI;
import vahdin.VahdinUI.LoginEvent;
import vahdin.VahdinUI.LoginListener;
import vahdin.data.Bust;
import vahdin.data.User;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SingleBustView extends CustomLayout implements View {

    private int markId;
    private int bustId;
    private final VahdinUI ui = (VahdinUI) UI.getCurrent();
    private final LoginListener loginListener;
    private Button delete;

    public SingleBustView() {
        super("single-bust-sidebar");

        loginListener = new LoginListener() {
            @Override
            public void login(LoginEvent event) {
                User user = ui.getCurrentUser();
                delete.setVisible(user.isLoggedIn());
            }
        };

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

        delete = new Button();
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

        loginListener.login(null); // force login actions
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

    /*
     * Method that shows an image of a mark or bust in a new window on top of
     * the current interface.
     */
    public void showImage(Bust bust) {
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
        String lookingForFilename = "b" + bust.getId();
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
            Image img = new Image(bust.getTitle(), resource);
            layout.addComponent(img);
        } else {
            Embedded img = new Embedded(bust.getTitle(), new ThemeResource(
                    "../vahdintheme/img/notfound.jpg"));
            layout.addComponent(img);
        }

        imagewin.setContent(layout);
        ui.addWindow(imagewin); // add modal window to main window
    }
}
