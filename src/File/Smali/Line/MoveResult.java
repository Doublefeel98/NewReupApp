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
public class MoveResult extends Line {

    private String variable;
    private boolean object;

    public MoveResult(String textContent) {
        super(textContent);
        
        typeLine = MoveResult.class.getName();
        
        String []arr = textContent.split("-");
        
        if(arr.length == 3){
            object = true;
            String []arr2 = arr[2].split(" ");
            variable = arr2[1];
        }
        else{
            object = false;
            String []arr1 = arr[1].split(" ");
            variable = arr1[1];
        }
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public boolean isObject() {
        return object;
    }

    public void setObject(boolean object) {
        this.object = object;
    }
    
    @Override
    public String toString() {
        if(object)
            return "move-result-object " + variable;
        else
            return "move-result " + variable;
    }
}
