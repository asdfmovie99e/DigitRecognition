package main;

 /*
 @author Jens Krueger
 @version 1.0
 */


import helper.MathHelper;

import java.util.HashMap;

public class HiddenNeuron {

    private Integer identNummer = null;
    private HashMap<Integer, Double> weightMap = new HashMap<Integer, Double>();
    private double inputSum = 0;
    private double outputSum = 0;
    private OutputNeuron[] outputNeurons;

    public void setIdentNummer(int identNummer){
        // setzt die Identifikationsnummer des Neurons
        this.identNummer = identNummer;
    }

    public void setOutputNeurons(OutputNeuron[] outputNeurons){
        //füllt das Array outPutNeurons mit den Neuronen der nächsten schicht
        this.outputNeurons = outputNeurons;
    }

    public void generateNewWeightMap(){
        //generiert eine neue HashMap in der die Gewichte die eingehenden Verbindungen gespeichert sind.
        for(int i = 0; i < 784; i++){
            weightMap.put(i,(Math.random() - 0.5d)); // so liegt das ergebnis ungefähr um 0 //GAAAANZ UNSICHER MIT GETEILT DURCH 100
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

    public void sendOutputToNextLayer(){
        //sendet den Outputwert an die nächste schicht. diese empfängt ihn mit der receive methode
        calcOutput();
        for(OutputNeuron outputNeuron: outputNeurons){
            outputNeuron.receive(identNummer, outputSum);
        }
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
