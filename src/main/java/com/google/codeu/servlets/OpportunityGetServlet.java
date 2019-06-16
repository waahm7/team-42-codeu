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
