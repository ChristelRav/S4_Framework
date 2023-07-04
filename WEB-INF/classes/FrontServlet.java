package etu2064.framework.servlet;

import etu2064.framework.Mapping;
import etu2064.framework.view.ModelView;
import etu2064.framework.myAnnotations.Url;
import etu2064.framework.myAnnotations.Param;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.text.SimpleDateFormat;
import java.lang.reflect.*;
import java.sql.*; 

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class FrontServlet  extends HttpServlet{
     HashMap<String,Mapping> mappingUrls = new HashMap<String,Mapping>();
     String modele ;
     //INIT
     public void init(ServletConfig config) throws ServletException {
         super.init(config);
         modele = getInitParameter("package");
         String path = getServletContext().getRealPath("/WEB-INF/classes/"+modele);
         fillMappingUrls(path);
     }
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
            String projet = req.getRequestURL().toString();
            String host = req.getHeader("Host");

            String[] partieUrl = projet.split(host);
            String chemin = partieUrl[1].substring(1);

            String action = chemin.substring(chemin.lastIndexOf("/") + 1);
            String packageName = modele.replace("/", ".");
          
            ///--Display vue
            if (mappingUrls.containsKey(action)) {
                Mapping valeur = mappingUrls.get(action);

                Class<?> maClasse = Class.forName(packageName+valeur.getClassName());
                Object obj = maClasse.newInstance();
                Method[] methods = maClasse.getDeclaredMethods();
                for (int i = 0; i < methods.length; i++) {
                    if(methods[i].getName().equals(valeur.getMethod())){
                        Class<?> returnType = methods[i].getReturnType();
                        if (returnType.equals(ModelView.class)) { 
                            Enumeration<String> paramNames = req.getParameterNames();
                            ModelView mv = null;
                            Parameter[] parameters = methods[i].getParameters();
                            Object[] arg = arg = new Object[parameters.length];
                            out.print(parameters.length);
                            //Choix  avec ou sans parmetre ilay methode
                            if(parameters.length == 0){
                                processNoParams(req, obj);
                                mv = (ModelView) methods[i].invoke(obj);                            
                            }else if(parameters.length > 0){
                                processParams(req, parameters, arg);
                                mv = (ModelView) methods[i].invoke(obj, arg);   
                            }
                            ///--envoie de qlq choz
                            for (Map.Entry<String, Object> entry : mv.getAttribut().entrySet()) {
                                String cle = entry.getKey();
                                Object vl = entry.getValue();
                                req.setAttribute(cle,vl);
                            }
                            RequestDispatcher dispat = req.getRequestDispatcher(mv.getView());
                            dispat.forward(req,res);
                        } else{
                            out.println("<p>Aucune vue disponible</p>");
                        }
                    }
                }
            }
        }catch (Exception e) {
            out.println(e.getMessage());
        }
    }
    //SEARCH CLASS
    public ArrayList<String> getClassNames(String path) {
        ArrayList<String> classNames = new ArrayList<>();
        try {
            File directory = new File(path);
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    String className = file.getName().replace(".class", "");
                    classNames.add(className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classNames;
    }
    
    //GET ANNOTATION + METHOD
    public void fillMappingUrls( String path) {
        ArrayList<String> classNames = getClassNames(path);
        String classesPrefix = "classes\\";
        String subPath = path.substring(path.indexOf(classesPrefix) + classesPrefix.length());
        String packageName =  subPath.replace("\\", ".");
        try {
            for (String className : classNames) {
                String val = packageName+className;
                Class<?> clazz = Class.forName(val);
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Url.class)) {
                        Url annotation = method.getAnnotation(Url.class);
                        String url = annotation.url();
                        Mapping mapping = new Mapping(className, method.getName());
                        this.mappingUrls.put(url, mapping);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
    public Object castValue(Class<?> type, String value) throws Exception{
        if (type == String.class) {
            return value;
        } else if (type == Integer.class || type == int.class) {
            return Integer.parseInt(value);
        } else if (type == Double.class || type == double.class) {
            return Double.parseDouble(value);
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == Long.class || type == long.class) {
            return Long.parseLong(value);
        } else if (type.toString() == "java.sql.Date") {
            return java.sql.Date.valueOf(value);
        }else if (type == Timestamp.class) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return new java.sql.Timestamp(formatter.parse(value).getTime());
        }else if(type == Time.class) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            return new java.sql.Time(formatter.parse(value).getTime());
        }else {
            return null;
        }
    }
    private void processNoParams(HttpServletRequest req, Object obj) throws Exception {
        Enumeration<String> paramNames = req.getParameterNames();
        Field[] fields = obj.getClass().getDeclaredFields();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            for (Field field : fields) {
                if (field.getName().equals(paramName)) {
                    String[] paramValues = req.getParameterValues(paramName);
                    Method method = obj.getClass().getMethod("set" + field.getName(), field.getType());
                    Object paramValue = castValue(field.getType(), paramValues[0]);
                    method.invoke(obj, paramValue);
                }
            }
        }
    }
    private void processParams(HttpServletRequest req, Parameter[] parameters, Object[] arg) throws Exception {
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            for (int j = 0; j < parameters.length; j++) {
                if (paramName.equals(parameters[j].getAnnotation(Param.class).p())) {
                    String[] paramValues = req.getParameterValues(paramName);
                    if (paramValues != null && paramValues.length == 1) {
                        arg[j] = castValue(parameters[j].getType(), paramValues[0]);
                    }
                }
            }
        }
    }
        


}   