/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

/**
 *
 * @author Dodong
 */
public class Property
{
    String name;
    int propid;

    public  Property( int id,String name){
        propid=id;
        this.name=name;
    }
    @Override
    public String toString(){
        return this.name;
    }
    public int getInt(){
        return propid;
    }
}