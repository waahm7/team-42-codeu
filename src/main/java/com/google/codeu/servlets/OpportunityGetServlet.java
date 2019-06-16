package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.Opportunity;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles fetching and saving user data.
 */
@WebServlet("/getOpportunity")
public class OpportunityGetServlet extends HttpServlet {

    private Datastore datastore;

    @Override
    public void init() {
        datastore = new Datastore();
    }

    /**
     * Responds with the "about me" section for a particular user.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        int id = -1;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.getOutputStream().println("Invalid id");
            return;

        }
        if (id < 1) {
            response.getOutputStream().println("invalid id");
            return;
        }
        Opportunity opportunity = datastore.getOpportunity(id);
        if (opportunity == null) {
            response.getOutputStream().println("invalid id");
            return;
        }


        ServletOutputStream stream = response.getOutputStream();
        stream.println("<html><head><link rel='stylesheet' href='./css/opportunity.css'></script></head> <body>");
        //title
        stream.println("<div>");
        stream.println("<h2 class='opportunity-title'>");
        stream.println(opportunity.getTitle());
        stream.println("</h2>");
        stream.println("</div>");

        //image
        stream.println("<div>");
        stream.println("<image class='OpportunityImage' src='");
        stream.println(opportunity.getAdvertisementImageUrl());
        stream.println("'/>");
        stream.println("</div>");

        //description
        stream.println("<div>");
        stream.println("<p>");
        stream.println(opportunity.getDescription());
        stream.println("</p>");
        stream.println("</div>");

        //detail title
        stream.println("<div>");
        stream.println("<h2 class='opportunity-title'>");
        stream.println("ScholarShip Details");
        stream.println("</h2>");
        stream.println("</div>");

        //details list
        stream.println("<div>");
        stream.println("<ul>");
        for(int i=0;i<opportunity.getOpportunityDetails().size();i++){
            stream.println("<li>");
            stream.println(opportunity.getOpportunityDetails().get(i));
            stream.println("</li>");
        }
        stream.println("</ul>");
        stream.println("</div>");

        //Eligibility Criteria
        stream.println("<div>");
        stream.println("<h2 class='opportunity-title'>");
        stream.println("Eligibility Criteria");
        stream.println("</h2>");
        stream.println("</div>");

        stream.println("<div>");
        stream.println("<ul>");
        for(int i=0;i<opportunity.getOtherRequirments().size();i++){
            stream.println("<li>");
            stream.println(opportunity.getOtherRequirments().get(i));
            stream.println("</li>");
        }
        stream.println("</ul>");
        stream.println("</div>");

        //detail title
        stream.println("<div>");
        stream.println("<h3 class='opportunity-title'>");
        stream.println("Due Date:"+opportunity.getDueDate());
        stream.println("</h3>");
        stream.println("</div>");



        //Additional Links
        stream.println("<div>");
        stream.println("<h2 class='opportunity-title'>");
        stream.println("Additional Links");
        stream.println("</h2>");
        stream.println("</div>");

        stream.println("<div>");
        stream.println("<ul>");
        for(int i=0;i<opportunity.getAdditionalLinks().size();i++){
            stream.println("<li><a href='");
            stream.println(opportunity.getAdditionalLinks().get(i));
            stream.println("'>");
            stream.println(opportunity.getAdditionalLinks().get(i));
            stream.println("</a></li>");
        }
        stream.println("</ul>");
        stream.println("</div>");





        stream.println("</body></html>");

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        String id = request.getParameter("id");
        if (id == null)
            response.getOutputStream().println("No id given");
        else
            response.getOutputStream().println("Opportunity id=" + id);

    }
}
