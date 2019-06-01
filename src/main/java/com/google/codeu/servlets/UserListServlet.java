package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user-list")
public class UserListServlet extends HttpServlet {
    private Datastore datastore;

    @Override
    public void init() {
        datastore = new Datastore();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        for(String user:datastore.getUsers())
            response.getOutputStream().println(user);

    }
}