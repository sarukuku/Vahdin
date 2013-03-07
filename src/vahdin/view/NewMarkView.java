package vahdin.view;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import vahdin.VahdinUI;
import vahdin.VahdinUI.LoginEvent;
import vahdin.VahdinUI.LoginListener;
import vahdin.component.ImageUpload;
import vahdin.data.Mark;
import vahdin.data.User;

import com.vaadin.data.util.sqlcontainer.query.QueryDelegate;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;

public class NewMarkView extends CustomLayout implements View {

    private final VahdinUI ui = (VahdinUI) UI.getCurrent();
    private final LoginListener loginListener;

    public NewMarkView() {
        super("new-mark-sidebar");

        final User user = ui.getCurrentUser();
        final String userId = user.getUserId();

        final TextField title = new TextField();
        final TextArea description = new TextArea();
        final String tempImgId = "m" + UUID.randomUUID().toString();
        Upload up = new ImageUpload().createImageUpload(tempImgId);

        Button cancel = new Button("Cancel");
        cancel.setStyleName("cancel-button");
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("");
            }
        });

        Button submit = new Button("Submit");
        submit.setStyleName("submit-button");
        submit.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                String name = title.getValue();
                String desc = description.getValue();
                Date time = new Date();
                if (name.isEmpty() || desc.isEmpty() || time == null) {
                    Notification
                            .show("A sign of wisdom and maturity is when you come to terms with the realization that your decisions cause your rewards and consequences. You are responsible for your life, and your ultimate success depends on the choices you make.");
                    return;
                }
                Mark m = new Mark(name, time, desc, userId);
                user.addExperience(10);
                try {
                    m.save();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
                Mark.addIdChangeListener(new QueryDelegate.RowIdChangeListener() {
					@Override
					public void rowIdChange(RowIdChangeEvent event) {
						System.out.println("HERE");
						try {
		                	String basePath = System.getProperty("user.home") + "/contentimgs/";
		                	File imgDirectory = new File(basePath);
		                	String tempFilename = null;
		                	if (imgDirectory.isDirectory()) {
							  String filenames[] = imgDirectory.list();
							  for (int i = 0; i < filenames.length; i++) {
							      if (filenames[i].contains(tempImgId)) {
							          tempFilename = filenames[i];
							          break;
							      }
							  }
							}
		                	
		                	String tempImgPath = basePath + tempFilename;
		                	System.out.println("TempImgPath: " + tempImgPath);
		                	
		                	if (new File(tempImgPath).exists()) {
		                		String[] fileType = tempImgPath.split("\\.");
		                		String finalImgPath = basePath + "m" + event.getNewRowId() + "." + fileType[fileType.length-1];
		                		File image = new File(tempImgPath);
		                   	 
		                  	  	if (image.renameTo(new File(finalImgPath))) {
		                  	  		System.out.println("File renamed successfully!");
		                  	  	} else {
		                  	  		System.out.println("Failed to rename image!");
		                  	  	}
		                	}
		                	
		                } catch (Exception e) {
		                	e.printStackTrace();
		                }
						Mark.removeIdChangeListener(this);
					}
                });
                
                try {
                	Mark.commit();
                    User.commit();
                    user.reload();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                Notification.show("Created new Mark with title: "
                        + title.getValue());
                UI.getCurrent().getNavigator().navigateTo("");
            }
        });

        addComponent(submit, "new-mark-submit-button");
        addComponent(cancel, "new-mark-cancel-button");
        addComponent(up, "new-mark-image-input");
        addComponent(description, "new-mark-desc-textarea");
        addComponent(title, "new-mark-title-input");

        loginListener = new LoginListener() {
            @Override
            public void login(LoginEvent event) {
                User user = ui.getCurrentUser();
                if (!user.isLoggedIn()) {
                    ui.getNavigator().navigateTo("");
                }
            }
        };
    }

    @Override
    public void enter(ViewChangeEvent event) {
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
