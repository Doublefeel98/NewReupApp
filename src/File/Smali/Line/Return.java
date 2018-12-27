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
public class Return extends Line{
    
    private String variable;
    
    public Return(String textContent) {
        super(textContent);
        
        typeLine = Return.class.getName();
        
        String []arr = textContent.split(" ");
        if(arr.length==2){
            variable = arr[1];
        }
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        if(variable == null)
            return "return-void";
        else
            return "return "+variable;
    }
}
