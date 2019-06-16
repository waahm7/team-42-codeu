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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A single message posted by a user.
 */
public class Opportunity {

    private int id;
    private int minAge,maxAge;
    private String title, description;
    private String applyLink;
    private String advertisementImageUrl;
    //Requirements
    private String gender, eductationLevel;
    private ArrayList<String> otherRequirments, additionalLinks,opportunityDetails;
    private Date dueDate, startDate;
    private boolean recurring;
    private int popularity;


    public Opportunity(int id,int minAge,int maxAge, String title,
                       String description, String applyLink,String advertisementImageUrl,
                       String gender, String eductationLevel, ArrayList<String> otherRequirments,
                       ArrayList<String> additionalLinks,ArrayList<String> opportunityDetails, String dueDate, String startDate, boolean recurring) {
        this.id = id;
        this.minAge=minAge;
        this.maxAge=maxAge;
        this.title = title;
        this.description = description;
        this.applyLink = applyLink;
        this.otherRequirments = otherRequirments;
        this.additionalLinks = additionalLinks;
        this.advertisementImageUrl=advertisementImageUrl;
        this.gender = gender;
        this.eductationLevel = eductationLevel;
        this.opportunityDetails=opportunityDetails;

        try {
            this.dueDate = new SimpleDateFormat("yyyy/dd/MM").parse(dueDate);
            this.startDate = new SimpleDateFormat("yyyy/dd/MM").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.recurring = recurring;
        popularity=0;
    }

    public Opportunity(int id,int minAge,int maxAge, String title,
                       String description, String applyLink,String advertisementImageUrl,
                       String gender, String eductationLevel, ArrayList<String> otherRequirments,
                       ArrayList<String> additionalLinks,ArrayList<String> opportunityDetails, String dueDate, String startDate, boolean recurring,int popularity) {
        this.id = id;
        this.minAge=minAge;
        this.maxAge=maxAge;
        this.title = title;
        this.description = description;
        this.applyLink = applyLink;
        this.otherRequirments = otherRequirments;
        this.additionalLinks = additionalLinks;
        this.advertisementImageUrl=advertisementImageUrl;
        this.gender = gender;
        this.eductationLevel = eductationLevel;
        this.opportunityDetails=opportunityDetails;

        try {
            this.dueDate = new SimpleDateFormat("yyyy/dd/MM").parse(dueDate);
            this.startDate = new SimpleDateFormat("yyyy/dd/MM").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.recurring = recurring;
        this.popularity=popularity;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public Opportunity(int id, int minAge, int maxAge, String title,
                       String description, String applyLink, String advertisementImageUrl,
                       String gender, String eductationLevel, String dueDate, String startDate, boolean recurring) {
        this.id = id;
        this.minAge=minAge;
        this.maxAge=maxAge;
        this.title = title;
        this.description = description;
        this.applyLink = applyLink;
        this.otherRequirments = new ArrayList<>();
        this.additionalLinks = new ArrayList<>();
        this.opportunityDetails = new ArrayList<>();
        this.advertisementImageUrl=advertisementImageUrl;
        this.gender = gender;
        this.eductationLevel = eductationLevel;
        try {
            this.dueDate = new SimpleDateFormat("yyyy/dd/MM").parse(dueDate);
            this.startDate = new SimpleDateFormat("yyyy/dd/MM").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.recurring = recurring;
    }

    public Opportunity(int id,int minAge,int maxAge, String title,
                       String description, String applyLink,String advertisementImageUrl,
                       String gender, String eductationLevel, ArrayList<String> otherRequirments,
                       ArrayList<String> additionalLinks,ArrayList<String> opportunityDetails, Date dueDate, Date startDate, boolean recurring) {
        this.id = id;
        this.minAge=minAge;
        this.maxAge=maxAge;
        this.title = title;
        this.description = description;
        this.applyLink = applyLink;
        this.otherRequirments = otherRequirments;
        this.additionalLinks = additionalLinks;
        this.advertisementImageUrl=advertisementImageUrl;
        this.gender = gender;
        this.eductationLevel = eductationLevel;
        this.startDate=startDate;
        this.dueDate=dueDate;
        this.recurring = recurring;
        this.opportunityDetails=opportunityDetails;

    }
    public Opportunity(int id,int minAge,int maxAge, String title,
                       String description, String applyLink,String advertisementImageUrl,
                       String gender, String eductationLevel, ArrayList<String> otherRequirments,
                       ArrayList<String> additionalLinks,ArrayList<String> opportunityDetails, Date dueDate, Date startDate, boolean recurring,int popularity) {
        this.id = id;
        this.minAge=minAge;
        this.maxAge=maxAge;
        this.title = title;
        this.description = description;
        this.applyLink = applyLink;
        this.otherRequirments = otherRequirments;
        this.additionalLinks = additionalLinks;
        this.advertisementImageUrl=advertisementImageUrl;
        this.gender = gender;
        this.eductationLevel = eductationLevel;
        this.startDate=startDate;
        this.dueDate=dueDate;
        this.recurring = recurring;
        this.popularity=popularity;
        this.opportunityDetails=opportunityDetails;

    }


    public Opportunity(int id,int minAge, int maxAge, String title,
                       String description, String applyLink,String advertisementImageUrl,
                       String gender, String eductationLevel, ArrayList<String> otherRequirments,
                       ArrayList<String> additionalLinks,ArrayList<String> opportunityDetails, String dueDate, String startDate) {
        this.id = id;
        this.minAge=minAge;
        this.maxAge=maxAge;
        this.title = title;
        this.description = description;
        this.applyLink = applyLink;
        this.otherRequirments = otherRequirments;
        this.additionalLinks = additionalLinks;
        this.gender = gender;
        this.advertisementImageUrl=advertisementImageUrl;
        this.eductationLevel = eductationLevel;
        this.opportunityDetails=opportunityDetails;


        try {

            this.dueDate = new SimpleDateFormat("yyyy/dd/MM").parse(dueDate);
            this.startDate = new SimpleDateFormat("yyyy/dd/MM").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.recurring = false;
    }


    public void insertOtherRequirement(String requirement){
        otherRequirments.add(requirement);
    }

    public void insertAdditionalLink(String link){
        additionalLinks.add(link);
    }
    public void insertOpporuntityDetail(String detail){
        opportunityDetails.add(detail);
    }

    public ArrayList<String> getOpportunityDetails() {
        return opportunityDetails;
    }

    public void setOpportunityDetails(ArrayList<String> opportunityDetails) {
        this.opportunityDetails = opportunityDetails;
    }


    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getAdvertisementImageUrl() {
        return advertisementImageUrl;
    }

    public void setAdvertisementImageUrl(String advertisementImageUrl) {
        this.advertisementImageUrl = advertisementImageUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEductationLevel() {
        return eductationLevel;
    }

    public void setEductationLevel(String eductationLevel) {
        this.eductationLevel = eductationLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }

    public ArrayList<String> getOtherRequirments() {
        return otherRequirments;
    }

    public void setOtherRequirments(ArrayList<String> otherRequirments) {
        this.otherRequirments = otherRequirments;
    }

    public ArrayList<String> getAdditionalLinks() {
        return additionalLinks;
    }

    public void setAdditionalLinks(ArrayList<String> additionalLinks) {
        this.additionalLinks = additionalLinks;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }
}
