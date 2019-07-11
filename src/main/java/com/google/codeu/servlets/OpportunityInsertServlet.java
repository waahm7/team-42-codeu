package com.google.codeu.servlets;

import com.google.api.client.util.DateTime;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Opportunity;
import javafx.event.Event;

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

    public void addCalenderEvent(){
        Event event = new Event().setSummary("Google I/O 2015")
                .setLocation("800 Howard St., San Francisco, CA 94103")
                .setDescription("A chance to hear more about Google's developer products.");

        DateTime startDateTime = new DateTime("2015-05-28T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2015-05-28T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);

        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("lpage@example.com"),
                new EventAttendee().setEmail("sbrin@example.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());

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
        String city = request.getParameter("City");
        String country = request.getParameter("Country");




        String dueDate = request.getParameter("DueDate"), startDate = request.getParameter("StartDate");
        String recurring = request.getParameter("Recurring");
        boolean recurringBool = false;
        if (recurring.equals("true"))
            recurringBool = true;

        ArrayList<String> otherRequirementsList = new ArrayList<>(Arrays.asList(otherRequirements.split(";")));
        ArrayList<String> additionalLinksList = new ArrayList<>(Arrays.asList(additionalLinks.split(";")));
        ArrayList<String> opportunityDetailsList = new ArrayList<>(Arrays.asList(opportunityDetails.split(";")));

        datastore.storeOpportunity(new Opportunity(id, minAge, maxAge, title,
                description, applyLink, advertisementImageUrl,
                gender, educationLevel, otherRequirementsList,
                additionalLinksList, opportunityDetailsList, dueDate, startDate, recurringBool, city, country));

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
        response.getOutputStream().println(city);
        response.getOutputStream().println(country);



        response.getOutputStream().println("Data inserted successfully");


    }
}
