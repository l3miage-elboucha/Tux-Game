/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import xml.XMLUtil;
import java.io.IOException;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 *
 * @author Mesouak
 */
public class Profil {
    
    Document document;
    
    private String nom;
    private String dateNaissance;
    protected String avatar;
    protected ArrayList<Partie> parties;
    
    public Profil(String nom, String dateNaissance){
    
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        parties = new ArrayList<Partie>();
        
    }
    
    public Profil(String filename) {  
        
        //Call function fromXML to retrieve the file filename.xml
        document = fromXML(filename);
        //Get the root of the document (tux:profil)
        Element racine = document.getDocumentElement();
        //Get the 3 elements tux:nom, tux:avatar and tux:anniversaire from the root
        Element Nom = (Element) racine.getElementsByTagName("tux:nom").item(0);
        Element Avatar = (Element) racine.getElementsByTagName("tux:avatar").item(0);
        Element Anniversaire = (Element) racine.getElementsByTagName("tux:anniversaire").item(0);
        Element Parties = (Element) racine.getElementsByTagName("tux:parties").item(0);
        //Get all "partie" as a nodelist containing all parties
        NodeList partie = Parties.getElementsByTagName("tux:partie");
        parties = new ArrayList<Partie>();
        //Affectation des infos du joueur
        this.nom = Nom.getTextContent();
        this.avatar = Avatar.getTextContent();
        this.dateNaissance = Anniversaire.getTextContent();
        //Adding all parties
        for (int i = 0; i < partie.getLength(); i++) {
            Element eltPartie = (Element) partie.item(i);
            Partie p;
            p = new Partie(eltPartie);
            ajouterPartie(p);
        }

    }
    
    public final void ajouterPartie(Partie p) {
        //Add "partie" to 'parties'
        parties.add(p);
        
        //Open a new instance file
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //Parse the xml document already existing
            document = documentBuilder.parse("src/Data/xml/profil_" + nom + ".xml");
        } catch (SAXException | IOException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Retrieve the root from the xml file
        Element root = document.getDocumentElement();
        //Create the element 'parties'
        Element partieS = document.createElement("tux:parties");
        
        //For each 'partie' in 'parties' we add it to the element "tux:parties" as an element
        for(Partie prt : parties){
            Element partie = prt.getPartie(document);
            partieS.appendChild(partie);
        }
        
        //We add the element "tux:parties" as a child of the root
        root.appendChild(partieS);
        
        //Transform as an xml file named "profil_name.xml"
        toXML("profil_" + this.nom +".xml");
        

    }

    public String getNom() {
        return nom;
    }
    
    
    
    public void sauvegarder(String filename) {
        try {
            //Open a new instance file
            DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = Factory.newDocumentBuilder();
            document = dBuilder.newDocument();

            //Create Element "tux:profil" (the root) wich contains all the vocabularies and the schema location
            Element profil = document.createElement("tux:profil");
            profil.setAttribute("xmlns:tux", "http://myGame/tux");
            profil.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            profil.setAttribute("xsi:schemaLocation", "http://myGame/tux ../xsd/profil.xsd");

            //Create Element "tux:nom" and fill it with this.nom content
            Element Nom = document.createElement("tux:nom");
            Nom.setTextContent(nom);
            //add the element "nom" as a child to the element "profil"
            profil.appendChild(Nom);
            
            //Create Element "tux:avatar" and fill it with this.avatar content
            Element Avatar = document.createElement("tux:avatar");
            Avatar.setTextContent(avatar);
            //add the element "tux:avatar" as a child to the element "tux:profil"
            profil.appendChild(Avatar);
            
            //Create Element "tux:anniversaire" and fill it with this.dateNaissance content
            Element eltAnniv = document.createElement("tux:anniversaire");
            eltAnniv.setTextContent(dateNaissance);
            //add the element "tux:anniversaire" as a child to the element "tux:profil"
            profil.appendChild(eltAnniv);

            //Add the element "tux:profil" that contains everything to the 'document'
            document.appendChild(profil);

        } catch (ParserConfigurationException | DOMException e) {
        }
        
        //Transform it to an xml file named "profil_name.xml"
        toXML("profil_" + filename);
    }

    
    // Cree un DOM à partir d'un fichier XML
    public final Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile("src/Data/xml/" + nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    // Sauvegarde un DOM en XML
    public void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(document, "src/Data/xml/" + nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }
    
    
    /// Takes a date in profile format: dd/mm/yyyy and returns a date
    /// in XML format (i.e. ????-??-??)
    public String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }


    
}
