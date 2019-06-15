package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.Opportunity;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Handles fetching and saving user data.
 */
@WebServlet("/insertOpportunity")

public class OpportunityInsertServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("text/html");

     int id = datastore.getOpportuntiesCount()+1;
     int minAge= Integer.parseInt(request.getParameter("minAge"));
     int maxAge= Integer.parseInt(request.getParameter("maxAge"));
     String title= request.getParameter("title"), description= request.getParameter("id");
     String applyLink= request.getParameter("ApplyLink");
     String advertisementImageUrl= request.getParameter("Image");
    //Requirements
     String gender= request.getParameter("Gender"), eductationLevel= request.getParameter("Education");
     String otherRequirments= request.getParameter("AdditionalRequirements"), additionalLinks= request.getParameter("AdditionalLinks");
     String dueDate= request.getParameter("DueDate"), startDate= request.getParameter("StartDate");
     String recurring= request.getParameter("Recurring");
     boolean recurringBool=false;
     if(recurring.equals("true"))
         recurringBool=true;

     ArrayList<String> otherRequirementsList = new ArrayList<>(Arrays.asList(otherRequirments.split(";")));
     ArrayList<String> addiotnalLinksList = new ArrayList<>(Arrays.asList(additionalLinks.split(";")));


      datastore.storeOpportunity(new Opportunity( id, minAge, maxAge,  title,
               description,  applyLink, advertisementImageUrl,
               gender,  eductationLevel,  otherRequirementsList,
               addiotnalLinksList,  dueDate,  startDate,  recurringBool));
    
    response.getOutputStream().println(id);
    response.getOutputStream().println(minAge);
    response.getOutputStream().println(maxAge);
    response.getOutputStream().println(title);
    response.getOutputStream().println(description);
    response.getOutputStream().println(applyLink);
    response.getOutputStream().println(advertisementImageUrl);
    response.getOutputStream().println(eductationLevel);
    response.getOutputStream().println(otherRequirments);
    response.getOutputStream().println(gender);
    response.getOutputStream().println(additionalLinks);
    response.getOutputStream().println(dueDate);
    response.getOutputStream().println(startDate);
    response.getOutputStream().println(recurring);
    response.getOutputStream().println("Data inserted successfully");


  }
}
