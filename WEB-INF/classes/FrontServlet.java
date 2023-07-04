//MaServlet
package etu2064.framework.servlet;


import etu2064.framework.myAnnotations.Url;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import etu2064.framework.Mapping;


public class FrontServlet  extends HttpServlet{
   
    //GET
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException  {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        try{
            processRequest(req,res);

        }catch(Exception exp){
            out.print(exp);
        }
    }
    //POST
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException  {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        try{             
            processRequest(req,res);     
        }catch(Exception exp){
            out.print(exp);
        }
    }
 
    //PROCESS REQUEST
    public void processRequest(HttpServletRequest req,HttpServletResponse res)throws IOException , Exception , ServletException{
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        ServletContext result = this.getServletContext();
        try {        
            /// Display URL 
            out.print("URL Tomcat = ");
            String projet = req.getRequestURL().toString();
            String host = req.getHeader("Host");

            String[] partieUrl = projet.split(host);
            String chemin = partieUrl[1].substring(1);
            out.print("/"+chemin);
        }catch (Exception e) {
            e.printStackTrace(out);
        }
        

    }
 
   
}