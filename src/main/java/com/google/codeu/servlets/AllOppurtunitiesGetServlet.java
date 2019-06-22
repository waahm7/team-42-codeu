package com.google.codeu.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.MailService.Message;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Opportunity;
import com.google.gson.JsonObject;
import java.util.List;


/**
 * Handles fetching site statistics.
 */
@WebServlet("/getAllOppurtunities")
public class GetAllOppurtunitiesServlet extends HttpServlet {

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
    JsonObject jsonObject = new JsonObject();
    String res = "["; 



    List <com.google.codeu.data.Opportunity> allOpp = datastore.getAllOpportunities();
    for (com.google.codeu.data.Opportunity tempOpportunity : allOpp) {
      String jsonInString = gson.toJson(tempOpportunity);
      // String id = Long.toString(tempOpportunity.getId());
      // jsonObject.add("Oppurtunities", jsonInString);  
      res = res + jsonInString + ",";
      
    }
    res = res.substring(0, res.length()-1);
    res = res + "]";
    // response.getOutputStream().println(jsonObject.toString());
    response.getWriter().write(res);
  }
}