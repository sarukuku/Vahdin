package vahdin.component;

import java.sql.SQLException;

import vahdin.VahdinUI;
import vahdin.VahdinUI.LoginEvent;
import vahdin.VahdinUI.LoginListener;
import vahdin.data.User;
import vahdin.data.Votable;
import vahdin.data.Vote;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

/** A button for voting on things. */
public class VoteButton extends Button {

    private final VahdinUI ui = (VahdinUI) UI.getCurrent();
    private final LoginListener loginListener;

    public enum Type {
        UP(1, "VAADIN/themes/vahdintheme/img/up-arrow.png",
                "VAADIN/themes/vahdintheme/img/up-arrow-active.png"), DOWN(-1,
                "VAADIN/themes/vahdintheme/img/down-arrow.png",
                "VAADIN/themes/vahdintheme/img/down-arrow-active.png");

        private final Resource icon;
        private final Resource activeIcon;
        private final float m;

        private Type(float multiplier, String iconPath, String activeIconPath) {
            m = multiplier;
            icon = new ExternalResource(iconPath);
            activeIcon = new ExternalResource(activeIconPath);
        }

        public Resource getInactiveIcon() {
            return icon;
        }

        public Resource getActiveIcon() {
            return activeIcon;
        }

        public double getMultiplier() {
            return m;
        }
    }

    public VoteButton(final Votable target, final Type type) {

        setStyleName("upvote");

        addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                User user = ui.getCurrentUser();
                if (user.isLoggedIn()) {
                    if (!Vote.hasVoted(target.getId(), target.getVotableName(),
                            user.getUserId())) {
                        user.reload();
                        Vote vote = new Vote(user.getUserId(), target.getId(),
                                target.getVotableName(),
                                user.getPrestigePower() * type.getMultiplier());
                        try {
                            vote.save();
                            Vote.commit();
                            User.commit();
                            user.reload();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        setIcon(type.getActiveIcon());
                    }
                } else {
                    ui.openLoginWindow();
                }
            }
        });

        loginListener = new LoginListener() {
            @Override
            public void login(LoginEvent event) {
                User user = ui.getCurrentUser();
                if (user.isLoggedIn()
                        && Vote.hasVoted(target.getId(),
                                target.getVotableName(), user.getUserId())
                        && user.getVote(target).getPower()
                                * type.getMultiplier() > 0) {
                    setIcon(type.getActiveIcon());
                } else {
                    setIcon(type.getInactiveIcon());
                }
            }
        };

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
}
