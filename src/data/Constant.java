/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author THAITHANG
 */
public interface Constant {
    interface AccessModifier{
        String PRIVATE = "private";
        String DEFAULT ="";
        String PROTECTED ="protected";
        String PUBLIC = "public";
    }
    
    interface Modifier{
        int NON_STATIC = 0;
        int STATIC = 1;
    }
    
    interface InvokeType{
        String VIRTUAL = "virtual";
        String STATIC = "static";
        String DIRECT = "direct";
    }
    interface COMPONIENT_TYPE{
        int  ANOTATION = 1;
        int FIELD = 2;
        int METHOD = 3;
    }
    
}
