package vahdin.view;

import java.util.List;

import vahdin.VahdinUI;
import vahdin.VahdinUI.LoginEvent;
import vahdin.VahdinUI.LoginListener;
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

public class MarksView extends CustomLayout implements View {

    final private VahdinUI ui = (VahdinUI) UI.getCurrent();
    private final LoginListener loginListener;

    public MarksView() {
        super("marks-sidebar");
        VerticalLayout marksList = new VerticalLayout();

        List<Mark> marks = Mark.loadAll();

        // The button to add a new Mark is only shown if user is logged in

        final Button newMarkButton = new Button();
        newMarkButton.setStyleName("new-mark-button");
        newMarkButton.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/add-button.png"));
        newMarkButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("newmark/");
            }
        });

        for (int i = 0; i < marks.size(); i++) {
            CustomLayout layout = new CustomLayout("mark-row");

            // Button to show BustsSubview with Busts under the clicked Mark
            Button title = new Button(marks.get(i).getTitle());
            title.setStyleName("mark-title");
            final int id = marks.get(i).getId();
            title.addClickListener(new Button.ClickListener() {

                public void buttonClick(ClickEvent event) {
                    UI.getCurrent().getNavigator()
                            .navigateTo("busts/" + id + "/");
                    // Notification.show("Title clicked");
                }

            });

            // Button to give upvote to Mark
            Button voteUp = new Button();
            voteUp.setIcon(new ExternalResource(
                    "VAADIN/themes/vahdintheme/img/up-arrow.png"));
            voteUp.setStyleName("upvote");
            voteUp.addClickListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    Notification.show("Upvote clicked");
                }
            });

            // Button to give downvote to Mark
            Button voteDown = new Button();
            voteDown.setIcon(new ExternalResource(
                    "VAADIN/themes/vahdintheme/img/down-arrow.png"));
            voteDown.setStyleName("downvote");
            voteDown.addClickListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    Notification.show("Downvote clicked");
                }
            });

            Label voteCount = new Label(marks.get(i).getVoteCount() + "");
            voteCount.setStyleName("vote-count");

            layout.addComponent(voteUp, "mark-row-upvote-arrow");
            layout.addComponent(voteCount, "mark-row-vote-count");
            layout.addComponent(voteDown, "mark-row-downvote-arrow");
            layout.addComponent(title, "mark-row-title");

            // FOR TESTING ONLY
            System.out.println(i + " " + marks.get(i).getTitle() + "id:"
                    + marks.get(i).getId());

            marksList.addComponent(layout);
        }

        addComponent(newMarkButton, "new-mark-button");
        addComponent(marksList, "marks-list");

        loginListener = new LoginListener() {
            @Override
            public void login(LoginEvent event) {
                User user = ui.getCurrentUser();
                newMarkButton.setVisible(user.isLoggedIn());
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
}