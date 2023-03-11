/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Mesouak
 */
public class Dico extends DefaultHandler {

    public ArrayList<String> listeNiveau1;
    public ArrayList<String> listeNiveau2;
    public ArrayList<String> listeNiveau3;
    public ArrayList<String> listeNiveau4;
    public ArrayList<String> listeNiveau5;
    public String cheminFichierDico;
    
    private int niveauS;
    private String mot;
    private boolean motSax;
    private boolean niveauSax;

    public Dico(String cheminFichierDico) {
        
        super();
        listeNiveau1 = new ArrayList<String>();
        listeNiveau2 = new ArrayList<String>();
        listeNiveau3 = new ArrayList<String>();
        listeNiveau4 = new ArrayList<String>();
        listeNiveau5 = new ArrayList<String>();
        this.cheminFichierDico = cheminFichierDico;

    }

    public String getMotDepuisListeNiveau(int niveau) {

        String mot = "";
        switch (verifierNiveau(niveau)) {
            case 1:
                mot = getMotDepuisListe(this.listeNiveau1);
                break;
            case 2:
                mot = getMotDepuisListe(this.listeNiveau2);
                break;
            case 3:
                mot = getMotDepuisListe(this.listeNiveau3);
                break;
            case 4:
                mot = getMotDepuisListe(this.listeNiveau4);
                break;
            case 5:
                mot = getMotDepuisListe(this.listeNiveau5);
                break;
            default:
                System.out.println("Niveau " + niveau + " non existant !\n");
                break;

        }

        return mot;
    }

    private String getMotDepuisListe(ArrayList<String> list) {

        int x;
        x = (int) (Math.random() * list.size());
        return list.get(x);

    }

    private int verifierNiveau(int niveau) {
        switch (niveau) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            default:
                return -1;
        }
    }

    public void ajouteMotADico(int niveau, String mot) {

        switch (verifierNiveau(niveau)) {
            case 1:
                if(!listeNiveau1.contains(mot)){
                    listeNiveau1.add(mot);
                    break;
                }
                System.out.println("Mot: '" + mot + "' deja existant dans le niveau : " + niveau + " !\n");
            case 2:
                if(!listeNiveau2.contains(mot)){
                    listeNiveau2.add(mot);
                    break;
                }
                System.out.println("Mot: '" + mot + "' deja existant dans le niveau : " + niveau + " !\n");
            case 3:
                if(!listeNiveau3.contains(mot)){
                    listeNiveau3.add(mot);
                    break;
                }
                System.out.println("Mot: '" + mot + "' deja existant dans le niveau : " + niveau + " !\n");
            case 4:
                if(!listeNiveau4.contains(mot)){
                    listeNiveau4.add(mot);
                    break;
                }
                System.out.println("Mot: '" + mot + "' deja existant dans le niveau : " + niveau + " !\n");
            case 5:
                if(!listeNiveau5.contains(mot)){
                    listeNiveau5.add(mot);
                    break;
                }
                System.out.println("Mot: '" + mot + "' deja existant dans le niveau : " + niveau + " !\n");
            default:
                System.out.println("Niveau non existant !\n");
        }

    }

    //Parseur DOM du dictionnaire
    public void lireDictionnaireDOM(String path, String filename) throws SAXException, IOException {

        DOMParser parser = new DOMParser();
        parser.parse(path + filename);
        Document doc = parser.getDocument();

        // NodeList qui retourne la liste des niveaux
        NodeList niveaux = doc.getElementsByTagName("tux:niveau");

        // Parcours des éléments niveau
        for (int i = 0; i < niveaux.getLength(); i++) {

            Element niveau = (Element) niveaux.item(i);
            String difficulte = niveau.getAttribute("difficulte");
            NodeList mots = niveau.getChildNodes();
            
            // Parcours des éléments mot
            for (int j = 0; j < mots.getLength(); j++) {

                Node mot = mots.item(j);

                if (mot.getNodeType() == Node.ELEMENT_NODE) {

                    switch (difficulte) {
                        case "1":
                            this.ajouteMotADico(1, mot.getTextContent().toLowerCase());
                            break;
                        case "2":
                            this.ajouteMotADico(2, mot.getTextContent().toLowerCase());
                            break;
                        case "3":
                            this.ajouteMotADico(3, mot.getTextContent().toLowerCase());
                            break;
                        case "4":
                            this.ajouteMotADico(4, mot.getTextContent().toLowerCase());
                            break;
                        case "5":
                            this.ajouteMotADico(5, mot.getTextContent().toLowerCase());
                            break;
                    }
                }

            }

        }

    }
    
    //Parseur SAX du dictionnaire
    public void lireDictionnaire() throws ParserConfigurationException, SAXException, SAXException, IOException {
        // créer la fabrique de parseurs et du parseur SAX 
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parseur = factory.newSAXParser();

        // lecture d'un fichier XML avec un DefaultHandler 
        File fichier = new File(cheminFichierDico);
        DefaultHandler handler = this;
        parseur.parse(fichier, handler);

    }
    
    @Override
    public void startDocument() throws SAXException {
	motSax = false;
        niveauSax = false;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	if (qName.equals("tux:niveau")){
            niveauSax = true;
            niveauS = Integer.parseInt(attributes.getValue("difficulte"));
	}
        if (qName.equals("tux:mot")){
            motSax = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (motSax) {
            this.mot = new String(ch, start, length).toLowerCase();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
	if (motSax) {
            ajouteMotADico(this.niveauS, this.mot);
            motSax = false;
	}
        if (niveauSax) {
            niveauSax = false;
        }
    }

}
