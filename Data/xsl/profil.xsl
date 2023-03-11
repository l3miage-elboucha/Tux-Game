<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : profil.xsl
    Created on : 25 octobre 2022, 16:42
    Author     : Mesouak - El Bouchrifi
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tux="http://myGame/tux" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Page Profil</title>
                <style>
                    table {
                    border: 1px solid;
                    table-layout: fixed;
                    width: 70%;
                    }
                    h1{
                    text-decoration: underline;
                    font-family: serif;
                    }
                    h2{
                    font-family: serif;
                    text-decoration: underline;
                    }
                    h3{
                    text-decoration: underline;
                    font-family: serif;
                    }
                </style>
            </head>
            <body>
                <h1> Profil : </h1> 
                <br/>
                <br/> 
                <h3>Nom :</h3> 
                <xsl:value-of select="//tux:nom"/> 
                <br/>
                <h3>Avatar :</h3> 
                <xsl:value-of select="//tux:avatar"/> 
                <br/>
                <h3>Anniversaire :</h3> 
                <xsl:value-of select="//tux:anniversaire"/> 
                <br/>
                <br/>
                <br/>
                <h2>Les parties :</h2>
                <xsl:apply-templates select="//tux:parties"/>
                <br/>
            </body>
        </html>
    </xsl:template>
    
    <!-- 
        Cette template affiche les parties
    --> 
    <xsl:template match="tux:parties">
        <xsl:apply-templates select="//tux:partie">
            <xsl:sort order="ascending"/>
        </xsl:apply-templates>
    </xsl:template>
    
    <xsl:template match="tux:partie">
        <table border="1">
            <tr>
                <td>Date de partie :<xsl:value-of select="@date"/></td>
                <td>Trouvé <xsl:value-of select="@trouvé"/></td>
                <td>Temps : <xsl:value-of select="tux:temps"/></td>
                <td>Difficulté : <xsl:value-of select="tux:niveau/@difficulte"/></td>
                <td>Mot : <xsl:value-of select="tux:niveau/tux:mot"/></td>
            </tr>
        </table>
    </xsl:template>
    
</xsl:stylesheet>