package vahdin.view;

import java.util.List;

import vahdin.VahdinUI;
import vahdin.VahdinUI.LoginEvent;
import vahdin.VahdinUI.LoginListener;
import vahdin.component.VoteButton;
import vahdin.data.Mark;
import vahdin.data.User;

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

    private boolean hasVisibleSuggestedMarks = false;

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
                if (event != null
                        && (user.isAdmin() || hasVisibleSuggestedMarks)) {
                    hasVisibleSuggestedMarks = false;
                    ui.getNavigator().navigateTo("");
                } else {
                    newMarkButton.setVisible(user.isLoggedIn());
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

        for (int i = marks.size() - 1; i >= 0; i--) {
            final Mark m = marks.get(i);

            if (!m.isApproved()) {
                if (user.isAdmin()) {
                    CustomLayout layout = new CustomLayout("suggested-mark-row");
                    Button title = new Button(marks.get(i).getTitle());
                    title.setStyleName("mark-title");
                    final int id = marks.get(i).getId();
                    title.addClickListener(new Button.ClickListener() {

                        public void buttonClick(ClickEvent event) {
                            UI.getCurrent().getNavigator()
                                    .navigateTo("suggestedmark/" + id);
                            // Notification.show("Title clicked");
                        }

                    });
                    layout.addComponent(title, "suggested-mark-row-title");
                    marksList.addComponent(layout);
                    hasVisibleSuggestedMarks = true;
                }
                continue;
            }

            CustomLayout layout = new CustomLayout("mark-row");

            // Button to show BustsSubview with Busts under the clicked Mark
            int count = m.getBusts().size();
            Button title = new Button(marks.get(i).getTitle() + " (" + count
                    + ")");
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
            final Button upvoteButton = new VoteButton(m, VoteButton.Type.UP);
            upvoteButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    voteCount.setValue((int) m.getVoteCount() + "");
                }
            });

            // Button to give downvote to Mark
            final Button downvoteButton = new VoteButton(m,
                    VoteButton.Type.DOWN);
            downvoteButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    voteCount.setValue((int) m.getVoteCount() + "");
                }
            });

            layout.addComponent(upvoteButton, "mark-row-upvote-arrow");
            layout.addComponent(voteCount, "mark-row-vote-count");
            layout.addComponent(downvoteButton, "mark-row-downvote-arrow");
            layout.addComponent(title, "mark-row-title");

            marksList.addComponent(layout);

            if (i == marks.size() - 1) {
                ui.showBusts(marks.get(i));
            }
        }

        loginListener.login(null); // force login actions
    }
}