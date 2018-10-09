package main;

import helper.MathHelper;

import java.util.HashMap;

public class OutputNeuron {

    private Integer identNummer = null;
    private double inputSum = 0;
    private double outputSum = 0;
    private HashMap<Integer, Double> weightMap = new HashMap<Integer, Double>();

    public void setIdentNummer(int identNummer){
        this.identNummer = identNummer;
    }


    public void generateNewWeightMap(){
        for(int i = 0; i < 35; i++){
            weightMap.put(i,(Math.random() - 0.5d) / 20d); // so liegt das ergebnis ungefähr um 0
        }
    }

    public void receive(int ident, double input){
        inputSum += weightMap.get(ident) * input;
    }

    private void calcOutput(){
        outputSum = MathHelper.sigmoidApprox(inputSum);
    }



}
