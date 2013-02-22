package vahdin.view;

import java.util.ArrayList;
import java.util.Date;

import vahdin.data.Mark;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MarksSubview extends Subview {

    public MarksSubview() {

        CustomLayout marksList = new CustomLayout("marks-sidebar");
        VerticalLayout tmp = new VerticalLayout();

        ArrayList<Mark> marks = new ArrayList<Mark>();
        Mark m = new Mark("Tissiposki", new Date(),
                "Tissiposki nähty mordorissa", 1, 1);
        Mark m2 = new Mark("HeMan", new Date(), "Hiimän Turussa", 2, 2);
        marks.add(m);
        marks.add(m2);

        for (int i = 0; i < marks.size(); i++) {
            CustomLayout layout = new CustomLayout("mark-row");

            // Button to show BustsSubview with Busts under the clicked Mark
            Button title = new Button(marks.get(i).getTitle());
            final int id = marks.get(i).getId();
            title.addClickListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    UI.getCurrent().getNavigator()
                            .navigateTo("/busts/" + id + "/");
                    Notification.show("Title clicked");
                }

            });

            // Button to give upvode to Mark
            Button voteUp = new Button("voteUp");
            voteUp.addClickListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    Notification.show("Upvote clicked");
                }
            });

            // Button to give downvote to Mark
            Button voteDown = new Button("voteDown");
            voteDown.addClickListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    Notification.show("Downvote clicked");
                }
            });

            Label voteCount = new Label(marks.get(i).getVoteCount() + "");

            layout.addComponent(voteUp, "mark-row-upvote-arrow");
            layout.addComponent(voteCount, "mark-row-vote-count");
            layout.addComponent(voteDown, "mark-row-downvote-arrow");
            layout.addComponent(title, "mark-row-title");

            // FOR TESTING ONLY
            System.out.println(i + " " + marks.get(i).getTitle());

            tmp.addComponent(layout);
        }
        marksList.addComponent(tmp, "marks-list");
        setCompositionRoot(marksList);

    }

    @Override
    public void show(String[] params) {
        if (params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                System.out.println(params[i]);
            }
        }
        addStyleName("open");
        super.show(params);
    }
}