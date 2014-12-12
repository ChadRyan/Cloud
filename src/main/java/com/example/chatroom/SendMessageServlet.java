package com.example.chatroom;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendMessageServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    String chatroom = req.getParameter("chatroom");
    Key chatroomKey = KeyFactory.createKey("chatroom", chatroom);
    String content = req.getParameter("content");
    Date date = new Date();
    Entity greeting = new Entity("Greeting", chatroomKey);
    greeting.setProperty("user", user);
    greeting.setProperty("date", date);
    greeting.setProperty("content", content);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(greeting);

    resp.sendRedirect("/chatroom.jsp?chatroom=" + chatroom);
  }
}