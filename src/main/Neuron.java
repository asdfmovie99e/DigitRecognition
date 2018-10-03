package main;

import helper.MathHelper;

import java.util.HashMap;

public abstract class Neuron {
    float inputSum = 0;
    Neuron[] previousNeurons = null;
    Neuron[] nextNeurons = null;
    int identNumber;
    HashMap<Integer, Float> weightMap;






    public void sendOutput(){
        for(Neuron neuron: nextNeurons){
            neuron.receiveInput(getOutputValue(), identNumber);
        }
    }

    public void generateWeightMap(){
        weightMap = new HashMap<Integer, Float>();

        for(int i = 0; i < previousNeurons.length; i++){
            weightMap.put(i, 0.5f); // am anfang haben alle weights den wert 0.5f
        }
    }

    public void setIdentNumber(int identNumber){
        this.identNumber = identNumber;
    }

    public void setPreviousNeurons(Neuron[] previousNeurons){
        this.previousNeurons = previousNeurons;
    }

    public void setNextNeurons(Neuron[] nextNeurons){
        this.nextNeurons = nextNeurons;
    }


    public float getOutputValue() {
        return MathHelper.sigmoidApprox(inputSum);
    }

    public void resetInputSum(){
        inputSum = 0;
    }

    public void receiveInput(float inputValue, int identNumber){
        inputSum +=  inputValue * weightMap.get(identNumber);
    }




}
