package vahdin.view;

import java.util.ArrayList;
import java.util.Date;

import vahdin.data.Mark;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class MarksSubview extends Subview {

    public MarksSubview() {

        CustomLayout marksList = new CustomLayout("marks-list");
        VerticalLayout tmp = new VerticalLayout();

        ArrayList<Mark> marks = new ArrayList<Mark>();
        Mark m = new Mark("Tissiposki", new Date(),
                "Tissiposki nähty mordorissa", 1, 1);
        Mark m2 = new Mark("HeMan", new Date(), "Hiimän Turussa", 2, 2);
        marks.add(m);
        marks.add(m2);

        for (int i = 0; i < marks.size(); i++) {
            CustomLayout layout = new CustomLayout("mark");

            // Button to show BustsSubview with Busts under the clicked Mark
            Button title = new Button(marks.get(i).getTitle());
            title.addClickListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
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

            layout.addComponent(voteUp, "mark-upvote-image");
            layout.addComponent(voteCount, "mark-vote-count");
            layout.addComponent(voteDown, "mark-downvote-image");
            layout.addComponent(title, "mark-title-container");

            // FOR TESTING ONLY
            System.out.println(i + " " + marks.get(i).getTitle());

            tmp.addComponent(layout);
        }
        marksList.addComponent(tmp, "marks-list-scrollable-area");
        setCompositionRoot(marksList);

    }
}