package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        String keyword=request.getParameter("keyword");
        System.out.println(keyword);
        try{
            Connection connection=DatabaseConnection.getConnection();
            //add keyword into history table
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into history values(?, ?)");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "http://localhost:8080/searchEngineAccio/search?keyword="+keyword);
            preparedStatement.executeUpdate();

            ResultSet resultSet=connection.createStatement().executeQuery("select pagetitle, pageLink, (length(lower(pageData))-length(replace(lower(pageData),'"+keyword+"',\"\")))/length('"+keyword+"') as countoccurences from pages order by countoccurences desc limit 30;"); //we are creating statement not preparing statement//
            ArrayList<Searchresult> results=new ArrayList<Searchresult>();
            while(resultSet.next()){
//                System.out.println(resultSet.getString("pagetitle")); //created for printing what we will be showing to our user//
//                System.out.println(" "+resultSet.getString("pageLink")+"\n");
                Searchresult searchresult=new Searchresult();
                searchresult.setPagetitle(resultSet.getString("pagetitle")); //using method of getter and setter
                searchresult.setPageLink(resultSet.getString("pageLink")); //similar to above
                results.add(searchresult);
            }
            //this part is sending request to the frontend//
            request.setAttribute("results",results);
            request.getRequestDispatcher("/Search.jsp").forward(request,response);
            response.setContentType("text/html");
            PrintWriter out=response.getWriter();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        catch(ServletException servletException){
            servletException.printStackTrace();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
