
package etu2064.framework;
public class FileUpload {
    String nameFile;
    byte [] byteFile;

    public String getnameFile() {return nameFile;}        public void setnameFile(String nameFile) {this.nameFile = nameFile;}
    public byte[] getbyteFile() {return byteFile;}        public void setbyteFile(byte[] byteFile) {this.byteFile = byteFile;}

    public FileUpload(){}
    public FileUpload(String nameFile,byte [] byteFile){
        this.setbyteFile(byteFile);
        this.setnameFile(nameFile);
    }
}