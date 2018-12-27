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
public class Source {
    
    private String textContent;
    private String name;

    public Source(String textContent) {
        this.textContent = textContent;
        
        String []arr = textContent.split(" ");
        name = arr[1].substring(1, arr[1].length() -2);
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ".source \"" +name+"\"";
    }  
}
