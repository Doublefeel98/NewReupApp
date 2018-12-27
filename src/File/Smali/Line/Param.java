/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.Smali.Line;

import models.smali.Line;

/**
 *
 * @author THAITHANG
 */
public class Param extends Line {

    private String variable;
    private String nameVariable;

    public Param(String textContent) {
        super(textContent);
        
        typeLine = Param.class.getName();
        
        String []arr = textContent.split(" ");
        
        variable = arr[1].substring(0, arr[1].length() - 1);
        nameVariable = arr[2].substring(1, arr[2].length() -1);
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getNameVariable() {
        return nameVariable;
    }

    public void setNameVariable(String nameVariable) {
        this.nameVariable = nameVariable;
    }

    @Override
    public String toString() {
        return ".param " + variable + ", \"" + nameVariable +"\"";
    }
}
