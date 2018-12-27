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
public class Const extends Line {

    private String dataType;
    private String variable;
    private String data;

    public Const(String textContent) {
        super(textContent);
        
        typeLine = Const.class.getName();
        
        String []arr = textContent.split(" ");
        dataType = arr[0];
        variable = arr[1].substring(0, arr[1].length() - 1);
        data = arr[2];
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return dataType + " " + variable + ", " + data;
    }

}
