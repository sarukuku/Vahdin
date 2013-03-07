package vahdin.view;

import java.sql.SQLException;
import java.util.List;

import vahdin.VahdinUI;
import vahdin.VahdinUI.LoginEvent;
import vahdin.VahdinUI.LoginListener;
import vahdin.data.Mark;
import vahdin.data.User;
import vahdin.data.Vote;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MarksView extends CustomLayout implements View {

    private final VahdinUI ui = (VahdinUI) UI.getCurrent();
    private final LoginListener loginListener;
    private final Button newMarkButton = new Button();
    private final VerticalLayout marksList;

    public MarksView() {
        super("marks-sidebar");

        newMarkButton.setStyleName("new-mark-button");
        newMarkButton.setIcon(new ExternalResource(
                "VAADIN/themes/vahdintheme/img/add-button.png"));
        newMarkButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("newmark/");
            }
        });

        marksList = new VerticalLayout();

        addComponent(newMarkButton, "new-mark-button");
        addComponent(marksList, "marks-list");

        loginListener = new LoginListener() {
            @Override
            public void login(LoginEvent event) {
                User user = ui.getCurrentUser();
                newMarkButton.setVisible(user.isLoggedIn());
                if (event != null) {
                    ui.getNavigator().navigateTo("");
                }
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
        ui.clearMap();
    }

    @Override
    public void enter(ViewChangeEvent event) {

        List<Mark> marks = Mark.loadAll();

        final User user = ui.getCurrentUser();

        // The button to add a new Mark is only shown if user is logged in

        for (int i = 0; i < marks.size(); i++) {
            final Mark m = marks.get(i);

            if (!m.isApproved()) {
                if (user.isAdmin()) {
                    CustomLayout layout = new CustomLayout("mark-row");
                    Button title = new Button(marks.get(i).getTitle());
                    title.setStyleName("mark-title");
                    final int id = marks.get(i).getId();
                    title.addClickListener(new Button.ClickListener() {

                        public void buttonClick(ClickEvent event) {
                            UI.getCurrent().getNavigator()
                                    .navigateTo("suggested-mark/" + id);
                            // Notification.show("Title clicked");
                        }

                    });
                    marksList.addComponent(layout);
                }
                continue;
            }

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

            final Label voteCount = new Label((int) m.getVoteCount() + "");
            voteCount.setStyleName("vote-count");

            // Button to give upvote to Mark
            final Button voteUp = new Button();

            if (Vote.hasVoted(id, "Mark", user.getUserId())
                    && user.getVote(m).getPower() > 0) {
                voteUp.setIcon(new ExternalResource(
                        "VAADIN/themes/vahdintheme/img/up-arrow-active.png"));
            } else {
                voteUp.setIcon(new ExternalResource(
                        "VAADIN/themes/vahdintheme/img/up-arrow.png"));
            }

            voteUp.setStyleName("upvote");
            voteUp.addClickListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    if (!user.isGuest()) {
                        if (!Vote.hasVoted(id, "Mark", user.getUserId())) {
                            Vote vote = new Vote(user.getUserId(), id, "Mark",
                                    user.getPrestigePower());
                            try {
                                vote.save();
                                Vote.commit();
                                User.commit();
                                user.reload();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            voteCount.setValue((int) m.getVoteCount() + "");
                            voteUp.setIcon(new ExternalResource(
                                    "VAADIN/themes/vahdintheme/img/up-arrow-active.png"));
                        }
                    } else {
                        ui.openLoginWindow();
                    }
                }
            });

            // Button to give downvote to Mark
            final Button voteDown = new Button();

            if (Vote.hasVoted(id, "Mark", user.getUserId())
                    && user.getVote(m).getPower() < 0) {
                voteDown.setIcon(new ExternalResource(
                        "VAADIN/themes/vahdintheme/img/down-arrow-active.png"));
            } else {
                voteDown.setIcon(new ExternalResource(
                        "VAADIN/themes/vahdintheme/img/down-arrow.png"));
            }

            voteDown.setStyleName("downvote");

            voteDown.addClickListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    if (!user.isGuest()) {
                        if (!Vote.hasVoted(id, "Mark", user.getUserId())) {
                            Vote vote = new Vote(user.getUserId(), id, "Mark",
                                    -user.getPrestigePower());
                            try {
                                vote.save();
                                Vote.commit();
                                User.commit();
                                user.reload();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            voteCount.setValue((int) m.getVoteCount() + "");
                            voteDown.setIcon(new ExternalResource(
                                    "VAADIN/themes/vahdintheme/img/down-arrow-active.png"));
                        }
                    } else {
                        ui.openLoginWindow();
                    }
                }
            });

            layout.addComponent(voteUp, "mark-row-upvote-arrow");
            layout.addComponent(voteCount, "mark-row-vote-count");
            layout.addComponent(voteDown, "mark-row-downvote-arrow");
            layout.addComponent(title, "mark-row-title");

            // FOR TESTING ONLY
            System.out.println(i + " " + marks.get(i).getTitle() + "id:"
                    + marks.get(i).getId());

            marksList.addComponent(layout);

            if (i == 0) {
                ui.showBusts(marks.get(i));
            }
        }

        loginListener.login(null); // force login actions
    }
}