/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 * Enumarartion of stars
 * 
 * @author MIGUEL_ANGEL
 */
public enum StarEnum
{
    ALPHA_AQUILAE("Alpha Aquilae"),
    ALPHA_AURIGAE("Alpha Aurigae"),
    ALPHA_BOOTIS("Alpha Bootis"),
    ALPHA_CANIS_MAJORIS("Alpha Canis Majoris"),
    ALPHA_CANIS_MINORIS("Alpha Canis Minoris"),
    ALPHA_CENTAURI("Alpha Centauri"),
    ALPHA_CORONAE("Alpha Coronae"),
    BETA_CENTAURI("Beta Centauri"),
    BETA_ORIONIS("Beta Orionis"),
    ALPHA_CRUCIS("Alpha Crucis"),
    BETA_CRUCIS("Beta Crucis"),
    DELTA_CRUCIS("Delta Crucis"),
    GAMMA_CRUCIS("Gamma Crucis"),
    ALPHA_CYGNI("Alpha Cygni"),
    ALPHA_GEMINORUM("Alpha Geminorum"),
    BETA_GEMINORUM("Beta Geminorum"),
    ALPHA_LYRAE("Alpha Lyrae"),
    ALPHA_ORIONIS("Alpha Orionis"),
    EPSILON_ORIONIS("Epsilon Orionis"),
    DELTA_ORIONIS("Delta Orionis"),
    ZETA_ORIONIS("Zeta Orionis"),
    ALPHA_PISCIS_AUSTRINI("Alpha Piscis Austrini"),
    ALPHA_SCORPII("Alpha Scorpii"),
    ALPHA_TAURI("Alpha Tauri"),
    ETA_TAURI("Eta Tauri"),
    PLEYADES("Pleyades"),
    ETA_URSAE_MAJORIS("Eta Ursae Majoris"),
    ALPHA_VIRGINIS("Alpha Virginis"),
    ALPHA_CASSIOPEIA("Alpha Cassiopeia");

    /**
     * Star name
     */
    String starName;

    /**
     * 
     * @param name Star name
     */
    StarEnum(String name)
    {
        this.starName = name;
    }

    /**
     * 
     * @return star name
     */
    @Override
    public String toString()
    {
        return starName;
    }
}
