package main;

import helper.MathHelper;

public abstract class Neuron {
    private float ouputValue;
    private float inputSum = 0;
    private Neuron[] previousNeurons = null;
    private Neuron[] nextNeurons = null;









    public void setPreviousNeurons(Neuron[] previousNeurons){
        this.previousNeurons = previousNeurons;
    }

    public void setNextNeurons(Neuron[] nextNeurons){
        this.nextNeurons = nextNeurons;
    }


    public void setOuputValue(float ouputValue) {
        this.ouputValue = ouputValue;
    }
    public float getOutputValue() {
        return MathHelper.sigmoidApprox(inputSum);
    }

    public void resetInputSum(){
        inputSum = 0;
    }

    public void addInputValue(float inputValue){
        inputSum +=  inputValue;
    }




}
