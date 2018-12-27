/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.smali;

/**
 *
 * @author THAITHANG
 */
public class Line {
    protected String textContent;
    protected String typeLine;

    public Line(String textContent) {
        this.textContent = textContent;
        this.typeLine = Line.class.getName();
    }

    public String getTextContent() {
        return textContent;
    }

    public String getTypeLine() {
        return typeLine;
    }
    
    public void replace(String oldString, String newString){
        if(textContent.contains(oldString))
            textContent = textContent.replace(oldString, newString);
    }
    
    public String toSmali(){
        return textContent;
    }
}
