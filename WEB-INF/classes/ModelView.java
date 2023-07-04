package etu2064.framework.view;
import java.util.Map;
import java.util.HashMap;

public class ModelView {
    String view;
    Map <String , Object> attribut = new HashMap<String , Object>();

   
    public String getView() {return view;}                         public void setView(String view) {this.view = view;}
    public Map<String, Object> getAttribut() {return attribut;}    public void setAttribut(Map<String, Object> attribut) {this.attribut = attribut;}
    
    public ModelView(){}
    public Map addItem(String cle, Object  valeur){
        this.getAttribut().put(cle, valeur);
        return this.getAttribut();
    }  
}