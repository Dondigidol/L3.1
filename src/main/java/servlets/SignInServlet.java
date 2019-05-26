package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SignInServlet extends HttpServlet {
    private DBService dbService;

    public SignInServlet(DBService dbService){
        this.dbService = dbService;
    }


    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");

        if(login!=""){
            try {
                Long user = dbService.getUserId(login);
                if (user>0 && user!=null){
                    System.out.println("User exists!");
                } else {
                    System.out.println("User not exist!");
                }
            } catch (DBException e){
                e.printStackTrace();
            }

        }
    }

}
