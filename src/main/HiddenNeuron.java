package main;


class HiddenNeuron extends Neuron {


    public void adjustWeights(float targetWeight){
        for(int i = 0; i < previousNeurons.length; i++)
        {
            float delta = learningFactor * previousNeurons[i].getOutputValue() * (targetWeight - getOutputValue());
            float oldWeight = weightMap.get(i);
            weightMap.remove(i);
            weightMap.put(i, oldWeight + delta);
        }

}
