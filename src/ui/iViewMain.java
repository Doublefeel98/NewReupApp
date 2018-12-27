/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

/**
 *
 * @author THAITHANG
 */
public interface iViewMain {
    void showPakageNameInManifest(String packageName);
    void showMessage(String message, boolean loadData);
    void reloadData();
}
