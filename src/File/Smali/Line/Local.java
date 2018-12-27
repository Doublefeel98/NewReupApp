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
public class Local extends Line{
    
    private String variable;
    private String name;
    private String typeData;
    
    public Local(String textContent) {
        super(textContent);
        
        typeLine = Local.class.getName();
        
        String []arr = textContent.split(" ");
        
        variable = arr[1].substring(0, arr[1].length() - 1);
        String []arr2 = arr[2].split(":");
        name = arr2[0].substring(1, arr2[0].length() -1);
        typeData = arr2[1];
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeData() {
        return typeData;
    }

    public void setTypeData(String typeData) {
        this.typeData = typeData;
    }

    @Override
    public String toString() {
        return ".local " + variable + ", \""+name+"\":" + typeData;
    }
}
