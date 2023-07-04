package etu2064.framework.modele;

import etu2064.framework.myAnnotations.Url;

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
    public String findAll(){
      String ok = "Coucou";
      return ok;
    }


}
