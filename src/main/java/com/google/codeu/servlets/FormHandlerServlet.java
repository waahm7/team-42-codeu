package com.google.codeu.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;


import java.io.IOException;
import java.util.List;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/my-form-handler")
public class FormHandlerServlet extends HttpServlet {

    private Datastore datastore;

    @Override
    public void init() {
        datastore = new Datastore();
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Get the message entered by the user.
        String userText= request.getParameter("message");

        // Get the URL of the image that the user uploaded to Blobstore.
        String imageUrl = getUploadedFileUrl(request, "image");

        // Output some HTML that shows the data the user entered.
        // A real codebase would probably store these in Datastore.
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            response.sendRedirect("/index.html");
            return;
        }

        String user = userService.getCurrentUser().getEmail();

        Message message;

        if(imageUrl!= null) {
            userText += "<img src=\"" + imageUrl + "\" />";
        }

            String text=userText;
            String regex = "(https?://\\S+\\.(jpeg|gif|png|jpg))";
            String replacement = "<img src=\"$1\" />";
            String textWithImagesReplaced;

            Pattern r = Pattern.compile(regex);

            // Now create matcher object.
            Matcher m = r.matcher(userText);
            if (m.find( )) {
                textWithImagesReplaced = userText.replaceAll(regex, replacement);
                message = new Message(user, textWithImagesReplaced);
            }else {
                message = new Message(user, text);
            }


        datastore.storeMessage(message);

        response.sendRedirect("/user-page.html?user=" + user);

}

    /**
     * Returns a URL that points to the uploaded file, or null if the user didn't upload a file.
     */
    private String getUploadedFileUrl(HttpServletRequest request, String formInputElementName){
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
        List<BlobKey> blobKeys = blobs.get("image");

        // User submitted form without selecting a file, so we can't get a URL. (devserver)
        if(blobKeys == null || blobKeys.isEmpty()) {
            return null;
        }

        // Our form only contains a single file input, so get the first index.
        BlobKey blobKey = blobKeys.get(0);

        // User submitted form without selecting a file, so we can't get a URL. (live server)
        BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
        if (blobInfo.getSize() == 0) {
            blobstoreService.delete(blobKey);
            return null;
        }

        // We could check the validity of the file here, e.g. to make sure it's an image file
        String filetype = new BlobInfoFactory().loadBlobInfo(blobKey).getContentType().toLowerCase();
        if (!(filetype.equals("image/jpg")|| filetype.equals("image/jpeg") || filetype.equals("image/png") || filetype.equals("image/gif"))){
            blobstoreService.delete(blobKey);
            return null;
        }

        // Use ImagesService to get a URL that points to the uploaded file.
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
        return imagesService.getServingUrl(options);
    }
}