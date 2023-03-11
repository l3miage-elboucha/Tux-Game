<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : dico.xsl
    Created on : 25 octobre 2022, 16:42
    Author     : Mesouak - El Bouchrifi
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tux="http://myGame/dico" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Page Dictionnaire</title>
            </head>
            <body>
                <h1> Dictionnaire </h1> <br/><br/>
                <xsl:apply-templates select="//tux:niveau"/><br/>
            </body>
        </html>
    </xsl:template>

    <!-- 
        Ici on crée la template niveau 
    -->
    <!-- 
        Cette template affiche le niveau et sa difficulté
    --> 
    <xsl:template match="tux:niveau">
        <h2> 
            Niveau : <xsl:value-of select="@difficulte"/>
        </h2>
        <i>
            <xsl:apply-templates select="tux:mot">
                <xsl:sort order="ascending"/>
            </xsl:apply-templates>
        </i>
    </xsl:template>
    
    <!--
           Creation de la template mot 
    -->
    <!--
        Elle affiche chaque mot du niveau avec un retour à la ligne après
    -->
    
    <xsl:template match="tux:mot">
        <xsl:value-of select="text()"/>
        <br/>
    </xsl:template>
 
</xsl:stylesheet>
