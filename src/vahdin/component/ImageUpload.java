package vahdin.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class ImageUpload {

    public ImageUpload() {

    }

    /*
     * Constructor for the class
     * 
     * @param UpFilename filename for the image
     * 
     * @return upload returns the upload object
     */
    public Upload createImageUpload(final String UpFilename) {
        Upload upload = new Upload("", null);
        upload.setButtonCaption("Start Upload");

        class ImageUploader implements Receiver, SucceededListener,
                FailedListener {
            public File file;

            public OutputStream receiveUpload(String filename, String mimeType) {
                FileOutputStream fos = null;
                // String dir = "/Users/roopemerikukka/Dropbox/testi/";
                File dir = new File(System.getProperty("user.home")
                        + File.separator + "uusikansio");

                // File folder = new File(System.getProperty("user.home") +
                // File.separator + "myfolder");
                String filetype = mimeType.split("/")[1];
                filename = UpFilename;
                if (checkMimeType(mimeType)) {
                    try {

                        file = new File(dir, filename + "." + filetype);
                        System.out.println(file.getAbsolutePath());
                        fos = new FileOutputStream(file);
                    } catch (final java.io.FileNotFoundException e) {
                        Notification
                                .show("Could not open file<br/>",
                                        e.getMessage(),
                                        Notification.TYPE_ERROR_MESSAGE);
                        return null;
                    }
                } else {
                    // Notification.show("Wrong filetype");
                }

                return fos;
            }

            public void uploadSucceeded(SucceededEvent event) {
                Notification.show("Upload succeeded!");
            }

            public boolean checkMimeType(String mimeType) {
                String[] mimetypes = { "image/png", "image/jpg", "image/jpeg",
                        "image/gif" };
                for (int i = 0; i < mimetypes.length; i++) {
                    if (mimeType.equals(mimetypes[i])) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void uploadFailed(FailedEvent event) {
                Notification.show("Upload failed");

            }
        }
        ;

        final ImageUploader uploader = new ImageUploader();
        upload.setReceiver(uploader);
        upload.addFailedListener(uploader);
        upload.addSucceededListener(uploader);
        return upload;
    }
}
