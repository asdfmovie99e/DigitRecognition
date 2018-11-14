package main;

 /*
 @author Jens Krueger
 @version 1.0
 */


import helper.MathHelper;
import helper.WeightSaver;

import java.util.HashMap;

public class HiddenNeuron {

    private Integer identNummer = null;
    private HashMap<Integer, Double> weightMap = new HashMap<Integer, Double>();
    private HashMap<Integer, Double> inputMap = new HashMap<Integer, Double>();
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
        inputMap.put(ident, input);
    }

    private void calcOutput(){
        //berechnet den output mithilfe der sigmoid funktion
        inputSum = 0;
        for(int i = 0; i < 748;i++){
            inputSum += inputMap.get(i) * weightMap.get(i);
        }
      //  inputSum += 0.2d; //BIAS TEST
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

    public void modWeight(){/*
        //die Variablen wie z.B. smallDelta beziehen sich auf die Delta Lernregel. Die Formel ist im Internet leicht zu finden.
        double oldValue = getOutputValue(); //debug purpose
        double smallDelta = 0;
        for(int i = 0; i < 10; i++){
            smallDelta += outputNeurons[i].getSmallDelta() * outputNeurons[i].getWeight(getIdentNummer());
        }
        double epsilon = 0.003f; // vollkommen experimentell. keine ahnung wie der wert gewählt werden soll
        for(int i = 0; i < 748; i++){
            double input = inputMap.get(i);
            double ableitung = MathHelper.sigmoidApprox(inputMap.get(i)) * (1 - MathHelper.sigmoidApprox(inputMap.get(i)));
            double bigDelta = epsilon * smallDelta * input * ableitung;
            double oldWeight = weightMap.get(i);
            //if(oldWeight + bigDelta > 1 || oldWeight + bigDelta < -1) return; // NUR TEST
            setWeight(i, oldWeight + bigDelta);
        }
        //ab hier debug purposes
        //Debug.log("Der Unterschied von HiddenNeuron " + getIdentNummer() + " ist " + (getOutputValue() - oldValue));
*/
       //new HiddenLearnThread().start();
    }


    public void saveWeightsToFile(){
        for(int i = 0; i < 784; i++){
            WeightSaver.receiveWeight(weightMap.get(i), i * 8+ 784 * 8 * this.getIdentNummer());
        }
    }

    public void setWeight(int ident, double weight){
        if (weightMap.get(ident) != null) weightMap.remove(ident);
        weightMap.put(ident,weight);
    }

    public void setWeightMap(HashMap<Integer, Double> weightMap){
        this.weightMap = weightMap;
    }

    public HashMap<Integer, Double> getWeightMap(){
        return weightMap;
    }

    public HashMap<Integer, Double> getInputMap(){
        return inputMap;
    }

    public OutputNeuron[] getOutputNeurons(){
        return outputNeurons;
    }
}
