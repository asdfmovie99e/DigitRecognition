package main;

import helper.MathHelper;

import java.util.HashMap;

public class HiddenNeuron {

    private Integer identNummer = null;
    private HashMap<Integer, Double> weightMap = new HashMap<Integer, Double>();
    private double inputSum = 0;
    private double outputSum = 0;
    private OutputNeuron[] outputNeurons;

    public void setIdentNummer(int identNummer){
        this.identNummer = identNummer;
    }

    public void setOutputNeurons(OutputNeuron[] outputNeurons){
        this.outputNeurons = outputNeurons;
    }

    public void generateNewWeightMap(){
        for(int i = 0; i < 784; i++){
            weightMap.put(i,(Math.random() - 0.5d) / 300d); // so liegt das ergebnis ungefähr um 0
        }
    }
    public void receive(int ident, double input){
        inputSum += weightMap.get(ident) * input;
    }

    public void calcOutput(){
        outputSum = MathHelper.sigmoidApprox(inputSum);
    }

    public void sendOutputToNextLayer(){
        for(OutputNeuron outputNeuron: outputNeurons){
            outputNeuron.receive(identNummer, outputSum);
        }
    }

}
