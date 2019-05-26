package main;

import dbService.DBService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import servlets.SignInServlet;
import servlets.SignUpServlet;


public class Main {
    public static void main(String[] args) throws Exception{
        DBService dbService = new DBService();
        dbService.printConnectionInfo();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new SignInServlet(dbService)), "/signin");
        context.addServlet(new ServletHolder(new SignUpServlet(dbService)), "/signup");

        ResourceHandler resource = new ResourceHandler();
        resource.setResourceBase("public_html");

        HandlerList handler = new HandlerList();
        handler.setHandlers(new Handler[]{resource, context});

        Server server = new Server(8080);
        server.stop();
        server.setHandler(handler);

        server.start();
        server.join();
    }
}
