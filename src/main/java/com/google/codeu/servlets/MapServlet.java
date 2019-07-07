package com.google.codeu.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.codeu.data.Datastore;
import java.util.List;
import java.util.Date;


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
    List <String> dueDate = datastore.getAllOpportunityDueDate();
    List <Boolean> recc = datastore.getAllOpportunityReccuuring();

    String jsonLocationString = gson.toJson(locations);
    String jsonTitleString = gson.toJson(titles);
    String jsonDueDate = gson.toJson(dueDate);
    String jsonRecc = gson.toJson(recc);


    String json = "[" + jsonLocationString + "," + jsonTitleString + "," + jsonDueDate + "," + jsonRecc + "]";
    // response.getOutputStream().println(jsonObject.toString());
    response.getWriter().write(json);
  }
}