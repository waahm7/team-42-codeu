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

    public Opportunity getOpportunity(int id) {
        Query query = new Query("Opportunity")
                .setFilter(new Query.FilterPredicate("id", FilterOperator.EQUAL, id));
        PreparedQuery results = datastore.prepare(query);
        Entity userEntity = results.asSingleEntity();
        if (userEntity == null) {
            return null;
        }
        Opportunity opportunity = new Opportunity(
                (int) userEntity.getProperty("id"),
                (int) userEntity.getProperty("minAge"),
                (int) userEntity.getProperty("maxAge"),
                (String) userEntity.getProperty("title"),
                (String) userEntity.getProperty("description"),
                (String) userEntity.getProperty("applyLink"),
                (String) userEntity.getProperty("advertisementImageUrl"),
                (String) userEntity.getProperty("gender"),
                (String) userEntity.getProperty("educationLevel"),
                (ArrayList<String>) userEntity.getProperty("otherRequirements"),
                (ArrayList<String>) userEntity.getProperty("additionalLinks"),
                (ArrayList<String>) userEntity.getProperty("opportunityDetails"),
                (Date) userEntity.getProperty("dueDate"),
                (Date) userEntity.getProperty("startDate"),
                (boolean) userEntity.getProperty("recurring"),
                (int) userEntity.getProperty("popularity"));

        return (Opportunity) userEntity.getProperty("opportunity");
    }

    public int getOpportuntiesCount(){
            Query query = new Query("Opportunity");
            PreparedQuery results = datastore.prepare(query);
            return results.countEntities(FetchOptions.Builder.withLimit(1000));
    }

    public void storeOpportunity(Opportunity opportunity) {
        Entity userEntity = new Entity("Opportunity", opportunity.getId());

        userEntity.setProperty("id", opportunity.getId());
        userEntity.setProperty("minAge", opportunity.getMinAge());
        userEntity.setProperty("maxAge", opportunity.getMaxAge());
        userEntity.setProperty("title", opportunity.getTitle());
        userEntity.setProperty("description", opportunity.getDescription());
        userEntity.setProperty("applyLink", opportunity.getApplyLink());
        userEntity.setProperty("advertisementImageUrl", opportunity.getAdvertisementImageUrl());
        userEntity.setProperty("gender", opportunity.getGender());
        userEntity.setProperty("educationLevel", opportunity.getEductationLevel());
        userEntity.setProperty("otherRequirements", opportunity.getOtherRequirments());
        userEntity.setProperty("additionalLinks", opportunity.getAdditionalLinks());
        userEntity.setProperty("dueDate", opportunity.getDueDate());
        userEntity.setProperty("startDate", opportunity.getStartDate());
        userEntity.setProperty("recurring", opportunity.isRecurring());
        userEntity.setProperty("popularity", opportunity.getPopularity());
        userEntity.setProperty("opportunityDetails", opportunity.getOpportunityDetails());


        datastore.put(userEntity);

    }


}
