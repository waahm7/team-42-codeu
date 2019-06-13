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
    private String title, description;
    private String applyLink;
    private ArrayList<String> requirements, additionalLinks;
    private Date dueDate, startDate;
    private boolean recurring;


    public Opportunity(int id, String title, String description, String applyLink, ArrayList<String> requirements, ArrayList<String> additionalLinks, String dueDate, String startDate, boolean recurring) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.applyLink = applyLink;
        this.requirements = requirements;
        this.additionalLinks = additionalLinks;
        try {
            this.dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(dueDate);
            this.startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.recurring = recurring;
    }
    public Opportunity(int id, String title, String description, String applyLink, ArrayList<String> requirements, ArrayList<String> additionalLinks, String dueDate, String startDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.applyLink = applyLink;
        this.requirements = requirements;
        this.additionalLinks = additionalLinks;
        try {
            this.dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(dueDate);
            this.startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.recurring = false;
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

    public ArrayList<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<String> requirements) {
        this.requirements = requirements;
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
