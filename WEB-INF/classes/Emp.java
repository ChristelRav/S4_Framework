package etu2064.framework.modele;

import etu2064.framework.myAnnotations.*;
import etu2064.framework.view.ModelView;

import java.util.Map;

public class Emp {
    String nom;
    int age;
/// GETTER & SETTER
    public String getnom() {return nom;}    public void setnom(String nom) {this.nom = nom;}
    public int getage() {return age;}       public void setage(int age) {this.age = age;}

    public Emp(){}
    public Emp(String nom, int age) {
        this.setnom(nom);
        this.setage(age);
    }
    @Url(url="findAll_Emp")
    public ModelView findAllEmp(){
      String jsp = "load.jsp";
      ModelView mv = new ModelView();
      mv.setView(jsp);
      return mv;
    }
    @Url(url="simple_Emp")
    public String simple(){
      String sp = "Coucou";
      return sp;
    }
    @Url(url="send_Emp")
    public ModelView sendEmp(){
      String jsp = "send.jsp";
      Emp e = new Emp("Ialy", 25);
      Map <String , Object> att = new ModelView().addItem("emp",e);
      ModelView mv = new ModelView();
      mv.setView(jsp);  mv.setAttribut(att);
      return mv;
    }
    @Url(url="form_Emp")
    public ModelView form(){
      String jsp = "form.jsp";
      ModelView mv = new ModelView();
      mv.setView(jsp);
      return mv;
    }
    @Url(url="save_Emp")
    public ModelView save(){
      String jsp = "save.jsp";
      ModelView mv = new ModelView();
      Emp e = new Emp(this.getnom(), this.getage());
      Map<String, Object> att = new ModelView().addItem("emp",e);
      mv.setView(jsp);
      mv.setAttribut(att);
      return mv;
    }
    @Url(url="loaded_Emp")
    public ModelView load(@Param(p="nom")String nom , @Param(p="age")int age){
      String jsp = "get.jsp";
      ModelView mv = new ModelView();
      Emp e = new Emp(nom, age);
      Map<String, Object> att = new ModelView().addItem("emp",e);
      mv.setView(jsp);
      mv.setAttribut(att);
      return mv;
    }
}
