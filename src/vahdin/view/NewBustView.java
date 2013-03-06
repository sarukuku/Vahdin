package vahdin.view;

import vahdin.VahdinUI;
import vahdin.VahdinUI.LoginEvent;
import vahdin.VahdinUI.LoginListener;
import vahdin.component.GoogleMap;
import vahdin.component.ImageUpload;
import vahdin.data.User;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;

/**
 * "New Bust" view.
 * 
 * URL format: #!newbust/{mark_id}/
 */
public class NewBustView extends CustomLayout implements View {

    private int markId;
    private GoogleMap.Marker marker = null;
    private double latitude;
    private double longitude;

    private final VahdinUI ui = (VahdinUI) UI.getCurrent();

    private final TextField title = new TextField();
    private final TextArea description = new TextArea();
    private final Upload up = new ImageUpload().createImageUpload("tissit");
    private final DateField date = new DateField("Pick date");
    private final Label lat = new Label();
    private final Label lon = new Label();

    private final Button cancel = new Button("Cancel");
    private final Button submit = new Button("Submit");

    private final GoogleMap.ClickListener mapListener;
    private final LoginListener loginListener;

    public NewBustView() {
        super("new-bust-sidebar");

        lat.setReadOnly(true);
        lon.setReadOnly(true);

        mapListener = new GoogleMap.ClickListener() {
            @Override
            public void click(GoogleMap.ClickEvent event) {
                System.out.println("moving marker");
                if (marker != null) {
                    ui.removeMarker(marker);
                }
                marker = ui.addMarker(event.latitude, event.longitude);
                ui.centerMapOn(event.latitude, event.longitude);
                lat.setValue("Lat: " + event.latitude);
                lon.setValue("Lon: " + event.longitude);
                latitude = event.latitude;
                longitude = event.longitude;
            }
        };

        ui.addMapClickListener(mapListener);

        loginListener = new LoginListener() {
            @Override
            public void login(LoginEvent event) {
                User user = ui.getCurrentUser();
                if (!user.isLoggedIn()) {
                    ui.getNavigator().navigateTo("");
                }
            }
        };

        ui.addLoginListener(loginListener);

        cancel.setStyleName("cancel-button");
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator()
                        .navigateTo("busts/" + markId + "/");
            }
        });

        submit.setStyleName("submit-button");
        submit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                // TODO: submit event handler
            }
        });

        addComponent(title, "new-bust-title-input");
        addComponent(description, "new-bust-desc-texarea");
        addComponent(up, "new-bust-image-input");
        addComponent(date, "new-bust-datetime-input");
        addComponent(lat, "latitude");
        addComponent(lon, "longtitude");
        addComponent(cancel, "new-bust-cancel-button");
        addComponent(submit, "new-bust-submit-button");
    }

    @Override
    public void enter(ViewChangeEvent event) {
        loginListener.login(null); // force login actions
    }

    @Override
    public void detach() {
        super.detach();
        ui.removeMapClickListener(mapListener);
        if (marker != null) {
            ui.removeMarker(marker);
        }
        ui.removeLoginListener(loginListener);
    }
}
