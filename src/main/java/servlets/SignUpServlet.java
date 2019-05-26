package servlets;

import dbService.DAO.UsersDAO;
import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;
import org.hibernate.HibernateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private DBService dbService;

    public SignUpServlet(DBService dbService){
        this.dbService =dbService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response ) throws ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login != "" & password != "") {
            try {
                dbService.addUser(login, password);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }
}
