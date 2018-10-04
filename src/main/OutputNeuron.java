package main;

class OutputNeuron extends Neuron{


    @Override
    public void setNextNeurons(Neuron[] nextNeurons){}// not used because output neurons dont have a next layer

    public void adjustWeights(float targetWeight){
        for(int i = 0; i < previousNeurons.length; i++)
        {
            float delta = learningFactor * previousNeurons[i].getOutputValue() * (targetWeight - getOutputValue());
            float oldWeight = weightMap.get(i);
            weightMap.remove(i);
            weightMap.put(i, oldWeight + delta);
        }

    }
}
