package vahdin;

import java.lang.reflect.Method;

import vahdin.component.GoogleMap;
import vahdin.data.User;
import vahdin.layout.MenuBar;
import vahdin.layout.SideBar;

import com.vaadin.annotations.Theme;
import com.vaadin.event.MethodEventSource;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Main UI class
 */
@Theme("vahdintheme")
@SuppressWarnings("serial")
public class VahdinUI extends UI implements MethodEventSource {

    private static final String GOOGLE_MAPS_API_KEY = "AIzaSyD723LQ68aCdI37_yhUNDQVHj3zzAfPDVo";

    private User currentUser = User.guest();

    /** Initializes the UI. */
    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Vahdin");

        GoogleMap map = new GoogleMap(GOOGLE_MAPS_API_KEY);
        map.setSizeFull();
        VerticalLayout sidebar = new VerticalLayout();
        MenuBar menu = new MenuBar();

        CustomLayout layout = new CustomLayout("main");
        layout.addComponent(menu, "menu");
        layout.addComponent(map, "map");
        layout.addComponent(sidebar, "sidebar");
        layout.setSizeFull();

        setContent(layout);
        ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(
                sidebar);
        Navigator navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addView("", SideBar.class);
    }

    /**
     * Gets the current user.
     * 
     * @return The currently logged in user, or a guest user if there is none.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user, marks them as logged in and fires the login event.
     * 
     * @param user
     *            The user to set as currently logged in.
     */
    public void setCurrentUser(User user) {
        currentUser.markLoggedOut();
        currentUser = user;
        currentUser.markLoggedIn();
        fireEvent(new LoginEvent(this));
    }

    /**
     * Adds a login listener.
     * 
     * @param listener
     *            The listener to add.
     */
    public void addLoginListener(LoginListener listener) {
        addListener(LoginEvent.class, listener, LoginListener.LOGIN_METHOD);
    }

    /** Login event listener. */
    public static abstract class LoginListener {

        public static final Method LOGIN_METHOD;

        static {
            try {
                LOGIN_METHOD = LoginListener.class.getMethod("login",
                        LoginEvent.class);
            } catch (NoSuchMethodException e) {
                throw new Error(e);
            }
        }

        /***/
        public abstract void login(LoginEvent event);

    }

    /** Login event. */
    public static class LoginEvent extends Component.Event {

        private LoginEvent(UI source) {
            super(source);
        }
    }
}