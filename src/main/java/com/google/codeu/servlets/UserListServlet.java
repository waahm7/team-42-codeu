package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet("/user-list")
public class UserListServlet extends HttpServlet {
    private Datastore datastore;

    @Override
    public void init() {
        datastore = new Datastore();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        Set<String> users=datastore.getUsers();
        for(String user:users)
            response.getOutputStream().println(user);

    }
}