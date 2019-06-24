/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.repackaged.com.google.common.base.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


/**
 * Provides access to the data stored in Datastore.
 */
public class Datastore {

    private DatastoreService datastore;

    public Datastore() {
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    /**
     * Stores the Message in Datastore.
     */
    public void storeMessage(Message message) {
        Entity messageEntity = new Entity("Message", message.getId().toString());
        messageEntity.setProperty("user", message.getUser());
        messageEntity.setProperty("text", message.getText());
        messageEntity.setProperty("timestamp", message.getTimestamp());

        datastore.put(messageEntity);
    }

    /**
     * Gets messages posted by a specific user.
     *
     * @return a list of messages posted by the user, or empty list if user has never posted a
     * message. List is sorted by time descending.
     */
    public List<Message> getMessages(String user) {
        List<Message> messages = new ArrayList<>();

        Query query =
                new Query("Message")
                        .setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user))
                        .addSort("timestamp", SortDirection.DESCENDING);
        PreparedQuery results = datastore.prepare(query);

        for (Entity entity : results.asIterable()) {
            try {
                String idString = entity.getKey().getName();
                UUID id = UUID.fromString(idString);
                String text = (String) entity.getProperty("text");
                long timestamp = (long) entity.getProperty("timestamp");

                Message message = new Message(id, user, text, timestamp);
                messages.add(message);
            } catch (Exception e) {
                System.err.println("Error reading message.");
                System.err.println(entity.toString());
                e.printStackTrace();
            }
        }

        return messages;
    }

    /**
     * Returns the total number of messages for all users.
     */
    public int getTotalMessageCount() {
        Query query = new Query("Message");
        PreparedQuery results = datastore.prepare(query);
        return results.countEntities(FetchOptions.Builder.withLimit(1000));
    }

    /**
     * Returns the total number of users for in site.
     */
    public int getTotalUserCount() {
        Query query = new Query("User");
        PreparedQuery results = datastore.prepare(query);
        return results.countEntities(FetchOptions.Builder.withLimit(1000));
    }

    /**
     * Stores the User in Datastore.
     */
    public void storeUser(User user) {
        Entity userEntity = new Entity("User", user.getEmail());
        userEntity.setProperty("email", user.getEmail());
        userEntity.setProperty("aboutMe", user.getAboutMe());
        datastore.put(userEntity);
    }

    /**
     * Returns the User owned by the email address, or
     * null if no matching User was found.
     */
    public User getUser(String email) {

        Query query = new Query("User")
                .setFilter(new Query.FilterPredicate("email", FilterOperator.EQUAL, email));
        PreparedQuery results = datastore.prepare(query);
        Entity userEntity = results.asSingleEntity();
        if (userEntity == null) {
            return null;
        }

        String aboutMe = (String) userEntity.getProperty("aboutMe");
        User user = new User(email, aboutMe);

        return user;
    }

    /*
     * Returns list of users that at least sent a message
     * */
    public Set<String> getUsers() {
        Set<String> users = new HashSet<>();
        Query query = new Query("Message");
        PreparedQuery results = datastore.prepare(query);
        for (Entity entity : results.asIterable()) {
            users.add((String) entity.getProperty("user"));
        }
        return users;
    }
	
	public List<String> getAllOpportunityTitle()
	{
		List<String> opportunities = new ArrayList<>();
		Query query = new Query("Opportunity");
                
        PreparedQuery results = datastore.prepare(query);
		
		for (Entity entity : results.asIterable()) {
			try{
				opportunities.add((String) entity.getProperty("title"));
			}
			
			catch (Exception e) {
                System.err.println("Error reading opportunity title.");
                System.err.println(entity.toString());
                e.printStackTrace();
            }
			
		}	
			
		return opportunities;
			
	}
    
    public List<String> getAllOpportunityLocations()
	{
		List<String> opportunities = new ArrayList<>();
		Query query = new Query("Opportunity");
                
        PreparedQuery results = datastore.prepare(query);
		
		for (Entity entity : results.asIterable()) {
			try{
                String city = (String) entity.getProperty("city");
                String country = (String) entity.getProperty("country");
                // long id = (long) entity.getProperty("id");
				opportunities.add(city+",+"+country);
			}
			
			catch (Exception e) {
                System.err.println("Error reading opportunity title.");
                System.err.println(entity.toString());
                e.printStackTrace();
            }
			
		}	
			
		return opportunities;
			
	}



    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();

        Query query = new Query("Message")
                .addSort("timestamp", SortDirection.DESCENDING);
        PreparedQuery results = datastore.prepare(query);
		

        for (Entity entity : results.asIterable()) {
            try {
                String idString = entity.getKey().getName();
                UUID id = UUID.fromString(idString);
                String user = (String) entity.getProperty("user");
                String text = (String) entity.getProperty("text");
                long timestamp = (long) entity.getProperty("timestamp");

                Message message = new Message(id, user, text, timestamp);
                messages.add(message);
            } catch (Exception e) {
                System.err.println("Error reading message.");
                System.err.println(entity.toString());
                e.printStackTrace();
            }
        }

        return messages;
    }


    public Message LongestMessage() {
        List<Message> messages = new ArrayList<>();

        Query query = new Query("Message")
                .addSort("timestamp", SortDirection.DESCENDING);
        PreparedQuery results = datastore.prepare(query);

        for (Entity entity : results.asIterable()) {
            try {
                String idString = entity.getKey().getName();
                UUID id = UUID.fromString(idString);
                String user = (String) entity.getProperty("user");
                String text = (String) entity.getProperty("text");
                long timestamp = (long) entity.getProperty("timestamp");

                Message message = new Message(id, user, text, timestamp);
                messages.add(message);
            } catch (Exception e) {
                System.err.println("Error reading message.");
                System.err.println(entity.toString());
                e.printStackTrace();
            }
        }

        int largestString = messages.get(0).getText().length();
        int index = 0;

        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getText().length() > largestString) {
                largestString = messages.get(i).getText().length();
                index = i;
            }
        }
        return messages.get(index);
    }

    public Opportunity getOpportunity(long id) {
        Query query = new Query("Opportunity")
                .setFilter(new Query.FilterPredicate("id", FilterOperator.EQUAL, id));
        PreparedQuery results = datastore.prepare(query);
        Entity opportunityEntity = results.asSingleEntity();
        if (opportunityEntity == null) {
            return null;
        }
        Opportunity opportunity = new Opportunity(id,
                (long) opportunityEntity.getProperty("minAge"),
                (long) opportunityEntity.getProperty("maxAge"),
                (String) opportunityEntity.getProperty("title"),
                (String) opportunityEntity.getProperty("description"),
                (String) opportunityEntity.getProperty("applyLink"),
                (String) opportunityEntity.getProperty("advertisementImageUrl"),
                (String) opportunityEntity.getProperty("gender"),
                (String) opportunityEntity.getProperty("educationLevel"),
                (ArrayList<String>) opportunityEntity.getProperty("otherRequirements"),
                (ArrayList<String>) opportunityEntity.getProperty("additionalLinks"),
                (ArrayList<String>) opportunityEntity.getProperty("opportunityDetails"),
                (Date) opportunityEntity.getProperty("dueDate"),
                (Date) opportunityEntity.getProperty("startDate"),
                (boolean) opportunityEntity.getProperty("recurring"),
                (long) opportunityEntity.getProperty("popularity"));

        return opportunity;
    }

    public int getOpportunitiesCount() {
        Query query = new Query("Opportunity");
        PreparedQuery results = datastore.prepare(query);
        return results.countEntities(FetchOptions.Builder.withLimit(1000));
    }

    public void storeOpportunity(Opportunity opportunity) {
        Entity opportunityEntity = new Entity("Opportunity", opportunity.getId());

        opportunityEntity.setProperty("id", opportunity.getId());
        opportunityEntity.setProperty("minAge", opportunity.getMinAge());
        opportunityEntity.setProperty("maxAge", opportunity.getMaxAge());
        opportunityEntity.setProperty("title", opportunity.getTitle());
        opportunityEntity.setProperty("description", opportunity.getDescription());
        opportunityEntity.setProperty("applyLink", opportunity.getApplyLink());
        opportunityEntity.setProperty("advertisementImageUrl", opportunity.getAdvertisementImageUrl());
        opportunityEntity.setProperty("gender", opportunity.getGender());
        opportunityEntity.setProperty("educationLevel", opportunity.getEductationLevel());
        opportunityEntity.setProperty("otherRequirements", opportunity.getOtherRequirments());
        opportunityEntity.setProperty("additionalLinks", opportunity.getAdditionalLinks());
        opportunityEntity.setProperty("dueDate", opportunity.getDueDate());
        opportunityEntity.setProperty("startDate", opportunity.getStartDate());
        opportunityEntity.setProperty("recurring", opportunity.isRecurring());
        opportunityEntity.setProperty("popularity", opportunity.getPopularity());
        opportunityEntity.setProperty("opportunityDetails", opportunity.getOpportunityDetails());
        opportunityEntity.setProperty("city", opportunity.getCity());
        opportunityEntity.setProperty("country", opportunity.getCountry());



        datastore.put(opportunityEntity);

    }


}
