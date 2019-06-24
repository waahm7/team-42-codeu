package com.google.codeu.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.MailService.Message;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.codeu.data.Datastore;
import com.google.gson.JsonObject;
import java.util.List;

/**
 * Handles fetching site statistics.
 */
@WebServlet("/map")
public class MapServlet extends HttpServlet{

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
    
    Gson gson = new Gson();
    List <String> locations = datastore.getAllOpportunityLocations();
    List <String> titles = datastore.getAllOpportunityTitle();

    String jsonLocationString = gson.toJson(locations);
    String jsonTitleString = gson.toJson(titles);


    String json = "[" + jsonLocationString + "," + jsonTitleString + "]";
    // response.getOutputStream().println(jsonObject.toString());
    response.getWriter().write(json);
  }
}