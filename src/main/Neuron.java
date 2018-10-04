package main;

import helper.MathHelper;

import java.util.HashMap;

public abstract class Neuron {
    Neuron[] previousNeurons = null;
    Neuron[] nextNeurons = null;
    int identNumber;
    HashMap<Integer, Float> weightMap;
    HashMap<Integer, Float> inputMap;
    float learningFactor = 0.01f;


    public void sendOutput(){
        for(Neuron neuron: nextNeurons){
            neuron.receiveInput(getOutputValue(), identNumber);
        }
    }

    public void generateMaps(){
        if(this instanceof  InputNeuron) return;
        weightMap = new HashMap<Integer, Float>();
        inputMap = new HashMap<Integer, Float>();
        for(int i = 0; i < previousNeurons.length; i++){
            weightMap.put(i,(float) (Math.random() / 20) ); // am anfang haben alle weights den wert 0.5f
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
        float inputSum = 0;
        for(int i = 0; i < inputMap.size(); i++){
            inputSum += inputMap.get(i) * weightMap.get(i);
        }
        //if(this instanceof  HiddenNeuron){ inputSum += 1; } // BIAS. Keine ahnung wie der gewählt werden muss und überhaupt. wird später bestimmt angepasst
        return MathHelper.identity(inputSum);
    }

    public void resetInputMap(){
        inputMap = new HashMap<Integer, Float>();
    }



    public void receiveInput(float inputValue, int identNumber){
       inputMap.put(identNumber, inputValue);
    }




}
