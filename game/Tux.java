/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Mesouak
 */
public class Tux extends EnvNode{
    
    Env env;
    Room room;
    
    public Tux(Env env, Room room) {
        
        this.env = env;
        this.room = room;
        
        setScale(4.0);
        setX(room.getWidth()/2);
        setY(getScale() * 1.1);
        setZ(room.getDepth()/2);
        setTexture("models/tux/tux_happy.png");
        setModel("models/tux/tux.obj");
        
    }
    
    public boolean Collision(double CordX, double CordY)
    {
        boolean coliz = false;
        if(!(CordX > 0 && CordX < room.getWidth()))
        {
            coliz = true;
        }
        if(!(CordY > 0 && CordY < room.getDepth()))
        {
            coliz = true;
        }
        
        
        return  coliz;
    }
    
    public void déplace() {
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
            // Haut
            double nextZ = this.getZ() - 1.0;
            if(!Collision(this.getX(), nextZ)){
                this.setRotateY(180);
                this.setZ(nextZ);
            }
            
       }
       if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
            // Gauche
            double nextX = this.getX() - 1.0;
            if(!Collision(nextX, this.getZ())){
                this.setRotateY(-90);
                this.setX(nextX);
            }
            
       }
       if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'droite' ou D
            // Droite
            double nextX = this.getX() + 1.0;
            if(!Collision(nextX, this.getZ())){
                this.setRotateY(90);
                this.setX(nextX);
            }
       }
       if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'bas' ou S
            // Bas
            double nextZ = this.getZ() + 1.0;
            if(!Collision(this.getX(), nextZ)){
                this.setRotateY(0);
                this.setZ(nextZ);
            }
       }
   
   }
    
}
