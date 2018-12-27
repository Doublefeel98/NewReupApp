/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;

/**
 *
 * @author THAITHANG
 */
public interface iProgressCallback {
    void changePackageName(String oldStr,String newStr);
    void changeMethodName(String packageName, String param, String oldName, String newName) ;
}
