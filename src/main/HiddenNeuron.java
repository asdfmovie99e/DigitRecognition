package main;


class HiddenNeuron extends Neuron {
    float currentDelta;
    float smallDelta;


    public void adjustWeights() {
        calcSmallDelta();
        for(int i = 0; i < previousNeurons.length; i++)
        {
            currentDelta = learningFactor * previousNeurons[i].getOutputValue() * smallDelta;
            float oldWeight = weightMap.get(i);
            weightMap.remove(i);
            weightMap.put(i, oldWeight + currentDelta);
        }


    }

    private void calcSmallDelta(){
        smallDelta = 0;
        for(Neuron neuron: nextNeurons){
            OutputNeuron outputNeuron = (OutputNeuron) neuron;
            smallDelta += weightMap.get(outputNeuron.identNumber) * outputNeuron.getSmallDelta();
        }
    }
}
