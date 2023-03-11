/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.advanced.EnvNode;

/**
 *
 * @author Mesouak
 */
public class Letter extends EnvNode{
    
    private char letter;
    
    public Letter(char l, double x, double y){
        
        letter = l;
        
        setScale(4.0);
        setX(x);
        setY(getScale() * 1.1);
        setZ(y);
        
        if (letter == ' '){
            
            setTexture("models/letter/cube.png");
            
        }
        else{
            setTexture("models/letter/" + letter + ".png");
        }
        setModel("models/letter/cube.obj");
        
    }
        
        
}
