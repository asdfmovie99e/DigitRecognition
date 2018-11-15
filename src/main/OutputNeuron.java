package main;

 /*
 @author Jens Krueger
 @version 1.0
 */


import helper.Debug;
import helper.MathHelper;
import helper.WeightSaver;

import java.util.HashMap;

public class OutputNeuron {

    private Integer identNummer = null;
    private double inputSum = 0;
    private double outputSum = 0;
    private double smallDelta = 0;
    private HashMap<Integer, Double> weightMap = new HashMap<Integer, Double>();
    private HashMap<Integer, Double> inputMap = new HashMap<Integer, Double>();

    public void setIdentNummer(int identNummer){
        // setzt die Identifikationsnummer des Neurons
        this.identNummer = identNummer;
    }


    public void generateNewWeightMap(){
        //generiert eine neue HashMap in der die Gewichte die eingehenden Verbindungen gespeichert sind.
        for(int i = 0; i < NetworkController.hiddenNeuronTwoNumber; i++){
            weightMap.put(i,(Math.random() - 0.5d)); // so liegt das ergebnis ungefähr um 0 KEINE AHNUNG VON DEM GEWICHT. MAL SEHEN
        }
    }

    public void receive(int ident, double input){
        //empfaengt die Daten der vorherigen Schicht
        inputMap.put(ident, input);
    }

    private void calcOutput(){
        //berechnet den output mithilfe der sigmoid funktion
        inputSum = 0;
        for(int i = 0; i < NetworkController.hiddenNeuronTwoNumber;i++){
           inputSum += inputMap.get(i) * weightMap.get(i);
        }
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

    public void modWeight(double targetWeight){
        //die Variablen wie z.B. smallDelta beziehen sich auf die Delta Lernregel. Die Formel ist im Internet leicht zu finden.
        double oldValue = getOutputValue(); //debug purpose
        smallDelta = targetWeight - getOutputValue();
        double epsilon = 0.1f; // vollkommen experimentell. keine ahnung wie der wert gewählt werden soll
        for(int i = 0; i < NetworkController.hiddenNeuronTwoNumber; i++){
            double input = inputMap.get(i);
            double ableitung = MathHelper.sigmoidApprox(inputMap.get(i)) * (1 - MathHelper.sigmoidApprox(inputMap.get(i)));
            double bigDelta = epsilon * smallDelta * input * ableitung;
            double oldWeight = weightMap.get(i);
            //if(oldWeight + bigDelta > 2 || oldWeight + bigDelta < -2) return; // NUR TEST
            weightMap.remove(i);
            weightMap.put(i, oldWeight + bigDelta);
        }
        //ab hier debug purposes
        //Debug.log("Der Unterschied von OutputNeuron " + getIdentNummer() + " ist " + (getOutputValue() - oldValue));
    }

    public double getSmallDelta() {
        return smallDelta;
    }

    public double getWeight(int ident){
        return weightMap.get(ident);
    }

    public void saveWeightsToFile(){
        for(int i = 0; i < NetworkController.hiddenNeuronTwoNumber; i++){
            WeightSaver.receiveWeight(weightMap.get(i), 784 * NetworkController.hiddenNeuronOneNumber * 8+ i * 8 + 8 * NetworkController.hiddenNeuronOneNumber * NetworkController.hiddenNeuronTwoNumber);
        }
    }

    public void setWeight(int ident, double weight){
        if (weightMap.get(ident) != null) weightMap.remove(ident);
        weightMap.put(ident,weight);
    }
}
