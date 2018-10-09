package main;

 /*
 @author Jens Krueger
 @version 1.0
 */


import helper.MathHelper;

import java.util.HashMap;

public class OutputNeuron {

    private Integer identNummer = null;
    private double inputSum = 0;
    private double outputSum = 0;
    private HashMap<Integer, Double> weightMap = new HashMap<Integer, Double>();

    public void setIdentNummer(int identNummer){
        // setzt die Identifikationsnummer des Neurons
        this.identNummer = identNummer;
    }


    public void generateNewWeightMap(){
        //generiert eine neue HashMap in der die Gewichte die eingehenden Verbindungen gespeichert sind.
        for(int i = 0; i < 35; i++){
            weightMap.put(i,(Math.random() - 0.5d)); // so liegt das ergebnis ungefähr um 0 KEINE AHNUNG VON DEM GEWICHT. MAL SEHEN
        }
    }

    public void receive(int ident, double input){
        //empfaengt die Daten der vorherigen Schicht
        inputSum += weightMap.get(ident) * input;
    }

    private void calcOutput(){
        //berechnet den output mithilfe der sigmoid funktion
        outputSum = MathHelper.sigmoidApprox(inputSum);
    }

    public double getOutputValue(){
        //wird eigentlich nie benutzt. nur fuer debug zwecke
        calcOutput();
        return outputSum;
    }

    public int getIdentNummer(){
        //nur fuer debug eigentlich
        return identNummer;
    }


}
