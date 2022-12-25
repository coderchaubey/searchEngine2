package com.Accio;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/MyServlet")
//This is a servlet (mini server)//
public class MyServlet extends HttpServlet { //you can add a try catch clause instead of "throws IOException"//
    //this will handle request
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //suppose i have triggered this "Myservlet"
        //so response and request both are created
        //setting content type of response
        response.setContentType("text/html"); //it will show an html page as response type is Html//
        //getting writer to write the content in response
        PrintWriter out=response.getWriter(); //under PrintWriter class we are getting the writer of the response//
        //this is the response that iam going to get//
        //writing content//
        out.println("<h3>This is My servlet</h3>"); //for printing the response using html code as the response type is HTML//

    }
}
