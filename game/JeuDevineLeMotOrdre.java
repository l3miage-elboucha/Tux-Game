/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Mesouak
 */
public class JeuDevineLeMotOrdre extends Jeu {
    
    private int nbLettresRestantes;
    private Chronometre chrono;
    
    public JeuDevineLeMotOrdre() throws SAXException, IOException, ParserConfigurationException{
        
        super();
        
    }

    private Boolean tuxTrouveLettre(){
        
        Letter lettre = super.lettres.get(super.lettres.size() - this.nbLettresRestantes);
        if(collision(lettre)){
            env.removeObject(lettre);
            return true;
        }
        return false;
        
    }
    
    
    @Override
    protected void démarrePartie(Partie partie){
    
        this.chrono = new Chronometre(20000);
        chrono.start();
        nbLettresRestantes = partie.mot.length();
        
    }

    @Override
    protected void appliqueRegles(Partie partie) {
    
        if(chrono.remainsTime()){
            if((nbLettresRestantes!=0) && tuxTrouveLettre()){
                nbLettresRestantes--;
            }
            else if(nbLettresRestantes == 0){
                this.terminePartie(partie);
            }
        }
        else{
            chrono.stop();
            partie.remainsTime = false;
        }
        
    }

    @Override
    protected void terminePartie(Partie partie) {
        AfterGameResult = new EnvText(env, partie.getTrouve() + "% du mot \"" + partie.getMot() +"\" trouvé", 110, 450);
        AfterGameResult.display();
        partie.setTrouve(nbLettresRestantes);
        partie.setTemps((int) chrono.tempsPasse());
        this.finished = true;
        
    }
    
}
