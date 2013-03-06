package vahdin.view;

import java.io.File;
import java.util.Date;

import vahdin.VahdinUI;
import vahdin.data.Bust;
import vahdin.data.Mark;

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
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SingleBustView extends CustomLayout implements View {

    private int markId;

    public SingleBustView() {
        super("single-bust-sidebar");

        System.out.println("Mark id: " + markId);

        Mark m1 = new Mark("Markin nimi", new Date(), "Markin kuvaus", 1, 1);
        Bust b1 = new Bust("Bustin nimi", 1, "Bustin kuvaus", 1, "1.3.2013",
                123.123, 456.465);
        m1.addBust(b1);

        Label title = new Label("<h2>" + b1.getTitle() + "</h2>",
                Label.CONTENT_XHTML);
        Label date = new Label("<h4>" + b1.getTime() + "</h4>",
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

        Label desc = new Label("<p>" + b1.getDescription() + "</p>",
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

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }
    
    /*
     * Method that shows an image of a mark or bust in a new window on top of the current interface.
     */
    public void showImage(Bust bust) {
    	final VahdinUI ui = (VahdinUI) UI.getCurrent(); // Get main window
    	final Window imagewin = new Window(); // Create the window
    	imagewin.setStyleName("single-image-window"); // Set style name
    	imagewin.setModal(true); // Make it modal
    	VerticalLayout layout = new VerticalLayout(); // Create layout for the image
    	Button close = new Button("Click this bar to close the image", new Button.ClickListener() { // Add a close button for the image
            public void buttonClick(ClickEvent event) { // inline click-listener
                ((UI) imagewin.getParent()).removeWindow(imagewin); // close the window by removing it from the parent window
            }
        });
    	layout.addComponent(close);
    	
    	String basepath = System.getProperty("user.home");
    	File directory = new File(basepath + "/contentimgs");
    	String filename = "b" + bust.getId();
    	
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
    	
    	Embedded img = new Embedded(bust.getTitle(), new ThemeResource(filename));
        layout.addComponent(img);
        imagewin.setContent(layout);
        ui.addWindow(imagewin); // add modal window to main window
    }
}
