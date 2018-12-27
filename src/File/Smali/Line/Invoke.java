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
public class Invoke extends Line {

    private String type;
    private String[] variables;
    private String packageName;
    private String nameMethod;

    public Invoke(String textContent) {
        super(textContent);
        
        typeLine = Invoke.class.getName();
        
        int start = textContent.indexOf('{');
        int end = textContent.indexOf('}');
        
        if(end > start + 1){
            String var = textContent.substring(start +1,end);
            variables = var.split(", ");
        }
        
        String head = textContent.substring(0, start - 1);
        String []arr1 = head.split("-");
        type = arr1[1];

        String tail = textContent.substring(end + 3, textContent.length());
        String []arr3 = tail.split("->");
        packageName = arr3[0];
        nameMethod = arr3[1];
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getVariables() {
        return variables;
    }

    public void setVariables(String[] variables) {
        this.variables = variables;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getNameMethod() {
        return nameMethod;
    }

    public void setNameMethod(String nameMethod) {
        this.nameMethod = nameMethod;
    }

    @Override
    public String toString() {
        String string;
        string = "invoke-" + type + " {";
        if(variables != null){
            for (int i = 0; i < variables.length; i++) {
                if (i != variables.length - 1) {
                    string += variables[i] + ", ";
                } else {
                    string += variables[i];
                }
            }
        }
        string += "}, " + packageName + "->" + nameMethod;
        return string;
    }
}
