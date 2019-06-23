package com.google.codeu.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.MailService.Message;
import com.google.codeu.data.Datastore;
import com.google.gson.JsonObject;

/**
 * Handles fetching site statistics.
 */
@WebServlet("/stats")
public class StatsPageServlet extends HttpServlet{

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with site statistics in JSON.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/json");

    int messageCount = datastore.getTotalMessageCount();
    int userCount = datastore.getTotalUserCount();
    // com.google.codeu.data.Message LongestMessage = datastore.LongestMessage();

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userCount", userCount);
    jsonObject.addProperty("messageCount", messageCount);
    // jsonObject.addProperty("LongestMessage", LongestMessage.getText());
    // jsonObject.addProperty("LongestMessageLenght", LongestMessage.getText().length());
    jsonObject.addProperty("Empty", 0);

    response.getOutputStream().println(jsonObject.toString());
  }
}