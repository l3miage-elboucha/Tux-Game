/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Mesouak - El Bouchrifi
 */
public class Partie {
    
    public String date;
    public String mot;
    public int niveau;
    private int trouvé;
    private int temps;
    
    boolean remainsTime = true;
    
    public Partie(String date, String mot, int niveau) {
    
        this.date = date;
        this.mot = mot;
        this.niveau = niveau;
        
    }
    
    public Partie(Element partieElt) {
        
        // Retrieve attribute date
        this.date = partieElt.getAttribute("date");
        // Retrieve element niveau from partie
        Element Niveau = (Element) partieElt.getElementsByTagName("tux:niveau").item(0);
        // Retrieve element mot from element niveau
        Element Mot = (Element) Niveau.getElementsByTagName("tux:mot").item(0);
        // Retrieve text content from element mot and affect it to this.mot
        this.mot = Mot.getTextContent();
        // Retrieve string content from attribute difficulte and affect it as an int to this.niveau
        this.niveau = Integer.parseInt(Niveau.getAttribute("difficulte"));
        

    }

    public Element getPartie(Document doc) {
        
        //Create Element partie
        Element partieElt = doc.createElement("tux:partie");
        
        
        //Create Attribute date
        partieElt.setAttribute("date", this.date);

        //Set attribute trouvé et l'élément temps
        if (this.trouvé < 100) {
            partieElt.setAttribute("trouvé", Integer.toString(this.trouvé) + "%");
        }else{
            Element tempsElt = doc.createElement("tux:temps");
            tempsElt.setTextContent(String.valueOf(this.temps));
            partieElt.appendChild(tempsElt);
        }
        
        //Create element niveau
        Element niveauElt = doc.createElement("tux:niveau");
        partieElt.appendChild(niveauElt);
        
        //Create attribute difficulte
        niveauElt.setAttribute("difficulte", Integer.toString(getNiveau()));
        
        //Create element nom
        Element motElt = doc.createElement("tux:mot");
        motElt.setTextContent(this.mot);
        niveauElt.appendChild(motElt);

        return partieElt;
        
    }

    public int getTrouve() {
        return trouvé;
    }

    public String getDate() {
        return date;
    }

    public String getMot() {
        return mot;
    }

    public int getTemps() {
        return temps;
    }
    
    
    public void setTrouve(int nbLettresRestantes){
        
        float trouve2 = (((float)mot.toCharArray().length - (float)nbLettresRestantes) / (float)mot.toCharArray().length) * 100;
        this.trouvé = (int)trouve2;
      
    }
    
    public void setTemps(int temps){
        
        this.temps = temps;
        
    }
    
    public int getNiveau(){
        
        return niveau;
        
    }
    
    @Override
    public String toString(){
        
        return "Le mot : '" + mot + "' du niveau " + niveau + " est complété de : " + trouvé + " dans un temps de " + temps + " secondes";
        
    }
    
}
