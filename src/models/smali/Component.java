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
public abstract class Component {
    private int componientType = -1;

    public int getComponientType() {
        return componientType;
    }

    public void setComponientType(int componientType) {
        this.componientType = componientType;
    }
    
    public abstract void replace(String oldString, String newString);
    public abstract String toSmali();
}
