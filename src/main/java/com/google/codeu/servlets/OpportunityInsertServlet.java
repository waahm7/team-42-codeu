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

        long id = datastore.getOpportunitiesCount() + 1;
        long minAge = Integer.parseInt(request.getParameter("minAge"));
        long maxAge = Integer.parseInt(request.getParameter("maxAge"));
        String title = request.getParameter("title");
        String description = request.getParameter("Description");
        String applyLink = request.getParameter("ApplyLink");
        String advertisementImageUrl = request.getParameter("Image");
        String gender = request.getParameter("Gender");
        String educationLevel = request.getParameter("Education");
        String otherRequirements = request.getParameter("AdditionalRequirements");
        String additionalLinks = request.getParameter("AdditionalLinks");
        String opportunityDetails = request.getParameter("opportunityDetails");


        String dueDate = request.getParameter("DueDate"), startDate = request.getParameter("StartDate");
        String recurring = request.getParameter("Recurring");
        boolean recurringBool = false;
        if (recurring.equals("true"))
            recurringBool = true;

        ArrayList<String> otherRequirementsList = new ArrayList<>(Arrays.asList(otherRequirements.split(";")));
        ArrayList<String> additionalLinksList = new ArrayList<>(Arrays.asList(additionalLinks.split(";")));
        ArrayList<String> opportunityDetailsList = new ArrayList<>(Arrays.asList(opportunityDetails.split(";")));

        Opportunity op=new Opportunity(id, minAge, maxAge, title,
                description, applyLink, advertisementImageUrl,
                gender, educationLevel, otherRequirementsList,
                additionalLinksList, opportunityDetailsList, dueDate, startDate, recurringBool);
        datastore.storeOpportunity(new Opportunity(id, minAge, maxAge, title,
                description, applyLink, advertisementImageUrl,
                gender, educationLevel, otherRequirementsList,
                additionalLinksList, opportunityDetailsList, dueDate, startDate, recurringBool));

        response.getOutputStream().println(id);
        response.getOutputStream().println(minAge);
        response.getOutputStream().println(maxAge);
        response.getOutputStream().println(title);
        response.getOutputStream().println(description);
        response.getOutputStream().println(applyLink);
        response.getOutputStream().println(advertisementImageUrl);
        response.getOutputStream().println(educationLevel);
        response.getOutputStream().println(otherRequirements);
        response.getOutputStream().println(gender);
        response.getOutputStream().println(additionalLinks);
        response.getOutputStream().println(opportunityDetails);
        response.getOutputStream().println(dueDate);
        response.getOutputStream().println(startDate);
        response.getOutputStream().println(recurring);

        response.getOutputStream().println("Data inserted successfully");


    }
}
