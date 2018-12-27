/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.Smali;

/**
 *
 * @author THAITHANG
 */
public class SuperClass {
    
    private String textContent;
    private String packageName;

    public SuperClass(String textContent) {
        this.textContent = textContent;
        
        String []arr = textContent.split(" ");
        packageName = arr[1];
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public void changePackageName(String oldPack, String newPack){
        if(packageName.equals("oldPack"))
            packageName = newPack;
    }

    @Override
    public String toString() {
        return ".super "+ packageName;
    }
}
