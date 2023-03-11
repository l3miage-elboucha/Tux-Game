
import game.Dico;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author elbou
 */
public class TestDico {
    
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException{
        
        Dico dico =  new Dico("src/Data/xml/dico.xml");
        
        
        //dico.lireDictionnaireDOM("C:/Users/elbou/Downloads/NetBeansProjects/NetBeansProjects/Tux/src/xml/","dico.xml");
        dico.lireDictionnaire();
        //System.out.println(dico);
        
        
        System.out.println(dico.listeNiveau1);
        System.out.println(dico.listeNiveau2);
        System.out.println(dico.listeNiveau3);
        System.out.println(dico.listeNiveau4);
        System.out.println(dico.listeNiveau5);
        System.out.println();
        
        
        dico.ajouteMotADico(1, "oui");
        dico.ajouteMotADico(1, "non");
        dico.ajouteMotADico(2, "hello");
        dico.ajouteMotADico(2, "priviet");
        dico.ajouteMotADico(2, "bonjour");
        dico.ajouteMotADico(2, "hola");
        dico.ajouteMotADico(2, "salam");
        dico.ajouteMotADico(2, "marhaba");
        dico.ajouteMotADico(3, "spasibo");
        
        
        
        System.out.println(dico.listeNiveau1);
        System.out.println(dico.listeNiveau2);
        System.out.println(dico.listeNiveau3);
        System.out.println(dico.listeNiveau4);
        System.out.println(dico.listeNiveau5);
        
        
        
        System.out.println(dico.getMotDepuisListeNiveau(1));
        System.out.println(dico.getMotDepuisListeNiveau(1));
        System.out.println(dico.getMotDepuisListeNiveau(2));
        System.out.println(dico.getMotDepuisListeNiveau(2));
        System.out.println(dico.getMotDepuisListeNiveau(2));
        System.out.println(dico.getMotDepuisListeNiveau(2));
        
        
    }
    
}
