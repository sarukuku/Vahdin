package vahdin.view;

import java.util.ArrayList;
import java.util.Date;

import vahdin.data.Mark;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
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
            layout.addComponent(new Button("voteUp"), "mark-upvote-image");
            layout.addComponent(new Label(marks.get(i).getVoteCount() + ""),
                    "mark-vote-count");
            layout.addComponent(new Button("voteDown"), "mark-downvote-image");
            layout.addComponent(new Label(marks.get(i).getTitle()),
                    "mark-title-container");
            System.out.println("jee");
            tmp.addComponent(layout);
        }
        marksList.addComponent(tmp, "marks-list-scrollable-area");
        setCompositionRoot(marksList);

    }
}