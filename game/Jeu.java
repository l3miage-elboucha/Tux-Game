/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.xml.parsers.ParserConfigurationException;
import org.lwjgl.input.Keyboard;
import org.xml.sax.SAXException;

/**
 *
 * @author gladen
 */
public abstract class Jeu {

    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    protected Env env;
    private Tux tux;
    private final Room mainRoom;
    private final Room menuRoom;
    private Letter letter;
    private Profil profil;
    private final Dico dico;
    public ArrayList<Letter> lettres;
    protected EnvTextMap menuText;                         //text (affichage des texte du jeu)
    public Boolean finished;
    
    
    //Variable du niveau
    private int niveau;
    
    //Text du menu choix niveaux
    EnvText MenuChoixNV;
    EnvText MenuNV1;
    EnvText MenuNV2;
    EnvText MenuNV3;
    EnvText MenuNV4;
    EnvText MenuNV5;
    
    //Text choix partie charge
    EnvText MenuPartieChargee;
    
    //Text Entrée Anniversaire
    EnvText MenuAnniversaire;
    
    //Text choix Avatar
    EnvText MenuAvatar;
    EnvText MenuAV1;
    EnvText MenuAV2;
    EnvText MenuAV3;
    
    //Text pour afficher le temps restant et le mot à trouver
    EnvText InGameTimeLeft;
    
    EnvText AfterGameResult;
    
    EnvText InGameWord;
    
    
    public Jeu() throws SAXException, IOException, ParserConfigurationException {

        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room
        mainRoom = new Room();
        
        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        //profil = new Profil();
        
        // Instancie conteneur de lettre
        lettres = new ArrayList<Letter>();
        
        // Dictionnaire
        dico = new Dico("src/Data/xml/dico.xml");
        try {
            //dico.lireDictionnaireDOM("C:/Users/elbou/Documents/TUX/TuxLetterGame_template/src/xml/", "dico.xml");
            //SAX Parsing of the dictionnary "dico"
            dico.lireDictionnaire();
        } catch (IOException | SAXException ex) 
        {
            System.out.println("Erreur chargement dico");
        }
        
        // instancie le menuText
        menuText = new EnvTextMap(env);
        
        // Textes affichés à l'écran
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("Choisissez un avatar : ", "NomAvatar", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);
        
        MenuChoixNV = new EnvText(env, "Veuillez choisir un niveau de difficulté", 200, 300);
        MenuNV1 = new EnvText(env, "1. Difficulté 1", 250, 260);
        MenuNV2 = new EnvText(env, "2. Difficulté 2", 250, 240);
        MenuNV3 = new EnvText(env, "3. Difficulté 3", 250, 220);
        MenuNV4 = new EnvText(env, "4. Difficulté 4", 250, 200);
        MenuNV5 = new EnvText(env, "5. Difficulté 5", 250, 180);
        
        MenuPartieChargee = new EnvText(env, "Veuillez choisir une partie", 200, 300);

        MenuAnniversaire = new EnvText(env, "Veuillez entrer votre anniversaire (aaaa-mm-jj): ", 150, 300);

        MenuAV1 = new EnvText(env, "conan", 220, 220);
        MenuAV2 = new EnvText(env, "pikachu", 220, 200);
        MenuAV3 = new EnvText(env, "goku", 220, 180);
        
        InGameTimeLeft = new EnvText(env, "", 200, 220);
        InGameWord = new EnvText(env, "", 200, 240);
              
    }

    /**
     * Gère le menu principal
     *
     */
    public void execute() {

        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
        env.exit();
    }


    // fourni
    private String getNomJoueur() {
        String nomJoueur = "";
        menuText.getText("NomJoueur").display();
        nomJoueur = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nomJoueur;
    }
    
    private String getAvatar() {
        String nomAvatar = "";
        menuText.getText("NomAvatar").display();
        MenuAV1.display();
        MenuAV2.display();
        MenuAV3.display();
        nomAvatar = menuText.getText("NomAvatar").lire(true);
        menuText.getText("NomAvatar").clean();
        MenuAV1.clean();
        MenuAV2.clean();
        MenuAV3.clean();
        return nomAvatar;
    }

    
    // fourni, à compléter
    private MENU_VAL menuJeu() {
        //Indices lettre
        int cx;
        int cy;
        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie = null;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);
            // affiche menu
            menuText.getText("Question").display();
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu2").display();
            menuText.getText("Jeu3").display();
            menuText.getText("Jeu4").display();
            
            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu2").clean();
            menuText.getText("Jeu3").clean();
            menuText.getText("Jeu4").clean();

            

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                    // .......... dico.******
                    MenuChoixNV.display();
                    MenuNV1.display();
                    MenuNV2.display();
                    MenuNV3.display();
                    MenuNV4.display();
                    MenuNV5.display();
                    String motDico = "";
                    int toucheChoixNV = 0;
                    while(!(toucheChoixNV == Keyboard.KEY_1 || toucheChoixNV == Keyboard.KEY_2 || toucheChoixNV == Keyboard.KEY_3 || toucheChoixNV == Keyboard.KEY_4 || toucheChoixNV == Keyboard.KEY_5)){
                        
                        toucheChoixNV = env.getKey();
                        env.advanceOneFrame();
                        
                    }
                    
                    switch( toucheChoixNV ){
                        
                        case Keyboard.KEY_1:
                            niveau = 1;
                            motDico = dico.getMotDepuisListeNiveau(niveau);
                            for (int i = 0; i < motDico.length(); i++) {
                                cx = (int) (Math.random() * 100) + 1;
                                cy = (int) (Math.random() * 100) + 1;
                                this.lettres.add(new Letter(motDico.charAt(i), cx, cy));
                            }
                            break;
                        case Keyboard.KEY_2:
                            niveau = 2;
                            motDico = dico.getMotDepuisListeNiveau(niveau);
                            for (int i = 0; i < motDico.length(); i++) {
                                cx = (int) (Math.random() * 100) + 1;
                                cy = (int) (Math.random() * 100) + 1;
                                this.lettres.add(new Letter(motDico.charAt(i), cx, cy));
                            }
                            break;
                        case Keyboard.KEY_3:
                            niveau = 3;
                            motDico = dico.getMotDepuisListeNiveau(niveau);
                            for (int i = 0; i < motDico.length(); i++) {
                                cx = (int) (Math.random() * 100) + 1;
                                cy = (int) (Math.random() * 100) + 1;
                                this.lettres.add(new Letter(motDico.charAt(i), cx, cy));
                            }
                            break;
                        case Keyboard.KEY_4:
                            niveau = 4; 
                            motDico = dico.getMotDepuisListeNiveau(niveau);
                            for (int i = 0; i < motDico.length(); i++) {
                                cx = (int) (Math.random() * 100) + 1;
                                cy = (int) (Math.random() * 100) + 1;
                                this.lettres.add(new Letter(motDico.charAt(i), cx, cy));
                            }
                            break;
                        case Keyboard.KEY_5:
                            niveau = 5;
                            motDico = dico.getMotDepuisListeNiveau(niveau);
                            for (int i = 0; i < motDico.length(); i++) {
                                cx = (int) (Math.random() * 100) + 1;
                                cy = (int) (Math.random() * 100) + 1;
                                this.lettres.add(new Letter(motDico.charAt(i), cx, cy));
                            }
                            break;
                        default:
                            break;
                    }
                    
                    MenuChoixNV.clean();
                    MenuNV1.clean();
                    MenuNV2.clean();
                    MenuNV3.clean();
                    MenuNV4.clean();
                    MenuNV5.clean();
                    
                    //Create a timer to show the word for 8 seconds before starting the game
                    Chronometre WordChrono = new Chronometre(8000);
                    this.InGameWord.modifyTextAndDisplay("Mot: " + motDico);
                    this.InGameTimeLeft.modifyTextAndDisplay((8 - WordChrono.tempsPasse()) + " secondes restantes pour regarder le mot");
                    while (WordChrono.remainsTime()) {
                        env.advanceOneFrame();
                        this.InGameTimeLeft.modifyTextAndDisplay((8 - WordChrono.tempsPasse()) + " secondes restantes pour regarder le mot");
                    }

                    InGameTimeLeft.clean();
                    InGameWord.clean();
                    
                    // restaure la room du jeu
                    env.setRoom(mainRoom);
                    
                    //ajouter les lettres
                    for(Letter l : lettres){
                                env.addObject(l);
                    }
              
                    
                    // crée un nouvelle partie
                    String datePartie = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                    datePartie = profil.profileDateToXmlDate(datePartie);
                    partie = new Partie(datePartie, motDico, niveau);
                    // joue
                    joue(partie);
                    lettres.clear();
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    profil.sauvegarder(profil.getNom() + ".xml");
                    profil.ajouterPartie(partie);
                    
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;


                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante


                    
                    /*
                    // Recupère le mot de la partie existante
                    // ..........
                    // joue
                    // restaure la room du jeu
                    env.setRoom(mainRoom);
                    joue(partie);
                    
                    // enregistre la partie dans le profil --> enregistre le profil
                    profil.sauvegarder(profil.getNom() + ".xml");
                    profil.ajouterPartie(partie);
                    */
                    // .......... profil.******
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }
    
    private String AnniversaireJoueur() {
        String anniv = "";
        while (!anniv.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
            MenuAnniversaire.clean();
            MenuAnniversaire.display();
            anniv = MenuAnniversaire.lire(true);
        }
        MenuAnniversaire.clean();
        return anniv;
    }


    private MENU_VAL menuPrincipal() {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;
        String nomAvatar;

        // restaure la room du menu
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("Principal1").display();
        menuText.getText("Principal2").display();
        menuText.getText("Principal3").display();
               
        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // Ask for player's name
                nomJoueur = getNomJoueur();
                // Retrieve player profile if exists
                File fichierJoueur = new File("src/Data/xml/profil_" + nomJoueur + ".xml");
                if (fichierJoueur.exists()) {
                    profil = new Profil("profil_" + nomJoueur + ".xml");
                    choix = menuJeu();
                    
                } else {
                    choix = MENU_VAL.MENU_JOUE;//CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // Ask for player's informations
                nomJoueur = getNomJoueur();
                String anniv = AnniversaireJoueur();
                nomAvatar = getAvatar();
                // Create a player's profile with the informations given
                profil = new Profil(nomJoueur, anniv);
                profil.avatar = nomAvatar;
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }

    public void joue(Partie partie) {
        
        // Instancie un Tux
        tux = new Tux(env, mainRoom);
        env.addObject(tux);

        //letter = new Letter('a', 10, 10);
        //env.addObject(letter);

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        démarrePartie(partie);
        // Boucle de jeu
        
        finished = false;
        while (!finished && partie.remainsTime) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true;
            }
            
            
            // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.déplace();
            
            // Ici, on applique les regles
            appliqueRegles(partie);

            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }

        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);

    }

    protected abstract void démarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);
    
    protected double distance(Letter lettre){
        
        double LettreX = lettre.getX();
        double LettreZ = lettre.getZ();
        double TuxX = tux.getX();
        double TuxZ = tux.getZ();
        return Math.sqrt(Math.pow(LettreX - TuxX, 2) + Math.pow(LettreZ - TuxZ, 2));
        
    }
    
    protected Boolean collision(Letter lettre){
        double col = Math.abs(distance(lettre));
        return  col < 6.0;
        
    }
    
}