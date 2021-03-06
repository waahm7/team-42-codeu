/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Handles fetching and saving {@link Message} instances. */
@WebServlet("/messages")
public class MessageServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with a JSON representation of {@link Message} data for a specific user. Responds with
   * an empty array if the user is not provided.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("application/json");

    String user = request.getParameter("user");

    if (user == null || user.equals("")) {
      // Request is invalid, return empty array
      response.getWriter().println("[]");
      return;
    }
	
	String content = request.getParameter("content");
	List<Message> messages = datastore.getAllMessages(); //gets all the messages in datastore
	
	
	if (content != null && !content.equals("")){
		messages = datastore.getSearchedMessages(content); //gets all the messages which contains the searched term in datastore
	}


    String page=request.getParameter("buttonName");
    String numberOfPostPerPage=request.getParameter("numberOfPostsPerPage");
    int start, end;
    int pageNumber;
    pageNumber= (page==null) ? 1 : Integer.parseInt(page);
    int total = messages.size();
    int numberOfMessages=(numberOfPostPerPage==null) ? 20 : Integer.parseInt(numberOfPostPerPage);
    List<Message> newMessages =new ArrayList<>(numberOfMessages);
       pageNumber--;
       start= pageNumber * numberOfMessages;
       end= start + numberOfMessages;
       if(start<total){
         for(int i=start; i<end && i<total ;i++){
           newMessages.add(messages.get(i));
         }
       }
    Gson gson = new Gson();
    String json = gson.toJson(newMessages);
    response.getWriter().println(json);
  }

  /** Stores a new {@link Message}. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

    String user = userService.getCurrentUser().getEmail();

    String userText = Jsoup.clean(request.getParameter("text"), Whitelist.none());
    String text = Jsoup.clean(request.getParameter("text"), Whitelist.none());
    String regex = "(https?://\\S+\\.(jpeg|gif|png|jpg))";
    String replacement = "<img src=\"$1\" />";
    String textWithImagesReplaced;
    Message message;

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
}
