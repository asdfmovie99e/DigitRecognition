package main;

class OutputNeuron extends Neuron{
    float currentDelta;
    float smallDelta;

    @Override
    public void setNextNeurons(Neuron[] nextNeurons){}// not used because output neurons dont have a next layer

    public void adjustWeights(float targetWeight){
        smallDelta = (targetWeight - getOutputValue());
        for(int i = 0; i < previousNeurons.length; i++)
        {
            currentDelta = learningFactor * previousNeurons[i].getOutputValue() * smallDelta;
            float oldWeight = weightMap.get(i);
            weightMap.remove(i);
            weightMap.put(i, oldWeight + currentDelta);
        }

    }

    public float getSmallDelta(){
        return smallDelta;
    }
}
