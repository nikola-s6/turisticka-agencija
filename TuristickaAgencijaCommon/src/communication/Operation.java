/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package communication;

import java.io.Serializable;

/**
 *
 * @author nikol
 */
public enum Operation implements Serializable{
    LOGOVANJE,
    ZAPAMTI_PUTNIKA,
    PRONADJI_PUTNIKE,
    UCITAJ_PUTNIKA,
    OBRISI_PUTNIKA,
    PRONADJI_PUTNOVANJA,
    UCITAJ_PUTOVANJE,
    ZAPAMTI_PUTOVANJE,
    ZAPAMTI_REZERVACIJU,
    PRONADJI_REZERVACIJE,
    UCITAJ_REZERVACIJE,
    OBRADI_REZERVACIJU,
    STORNIRAJ_REZERVACIJU,
    UCITAJ_PUTOVANJA_PUTNIKA,
    UCITAJ_LISTU_GRADOVA,
    UCITAJ_LISTU_PUTNIKA,
    UCITAJ_LISTU_PUTOVANJA,
    UCITAJ_LISTU_TERMINA;
}
