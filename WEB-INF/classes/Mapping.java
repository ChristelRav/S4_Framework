package etu2064.framework;

public class Mapping {
    String className,method;
    /// GETTER & SETTER
    public String getClassName() {return className;}     public void setClassName(String className) {this.className = className;}
    public String getMethod() {return method;}           public void setMethod(String method) {this.method = method;}

    public Mapping(String className, String method) {
        this.className = className;
        this.method = method;
    }
}

