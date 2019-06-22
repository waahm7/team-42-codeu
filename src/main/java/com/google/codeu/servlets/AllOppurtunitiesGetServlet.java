package com.google.codeu.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.codeu.data.Datastore;
import java.util.List;


/**
 * Handles fetching site statistics.
 */
@WebServlet("/getAllOppurtunities")
public class AllOppurtunitiesGetServlet extends HttpServlet {

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
    String res = "["; 
    List <com.google.codeu.data.Opportunity> allOpp = datastore.getAllOpportunities();
    for (com.google.codeu.data.Opportunity tempOpportunity : allOpp) {
      String jsonInString = gson.toJson(tempOpportunity);
      res = res + jsonInString + ",";
    }
    if (res.equals("[]")) {
      // Request is invalid, return empty array
      response.getWriter().println("{}");
      return;
    }
    res = res.substring(0, res.length()-1);
    res = res + "]";
    response.getWriter().write(res);
  }
}