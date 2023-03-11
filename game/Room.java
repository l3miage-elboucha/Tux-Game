/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Mesouak
 */
public class Room {
    
    private int depth;
    private int height;
    private int width;
    private String textureBottom;
    private String textureNorth;
    private String textureEast;
    private String textureWest;
    private String textureTop;
    private String textureSouth;
    
    
    public Room(){
        try{
            //Create new document instance
            DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder Builder;
            Builder = Factory.newDocumentBuilder();
            Document plateauXML = Builder.parse("src/Data/xml/plateau.xml");

            //Retrieve the coordinates for depth, width and height and affect them to the room attributes
            depth = Integer.parseInt(plateauXML.getElementsByTagName("depth").item(0).getTextContent());
            width = Integer.parseInt(plateauXML.getElementsByTagName("width").item(0).getTextContent());
            height = Integer.parseInt(plateauXML.getElementsByTagName("height").item(0).getTextContent());
            
            //Retrieve the textures from the xml document and affect them to the room attributes
            textureEast = plateauXML.getElementsByTagName("textureEast").item(0).getTextContent();
            textureWest = plateauXML.getElementsByTagName("textureWest").item(0).getTextContent();
            textureBottom = plateauXML.getElementsByTagName("textureBottom").item(0).getTextContent();
            textureNorth = plateauXML.getElementsByTagName("textureNorth").item(0).getTextContent();
            
        }catch(IOException | ParserConfigurationException | SAXException e){
        }

    }
    
    
    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }
    
    
    
}
