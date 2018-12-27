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
public class NewInstance extends Line{
    
    private String variable;
    private String packageName;
    
    public NewInstance(String textContent) {
        super(textContent);
        
        typeLine = NewInstance.class.getName();
        
        String []arr = textContent.split(" ");
        variable = arr[1].substring(0, arr[1].length() - 1);
        packageName = arr[2];
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "new-instance "+variable+", "+packageName;
    }
}
