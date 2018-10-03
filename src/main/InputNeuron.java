package main;

class InputNeuron extends Neuron{

    @Override
    public void setPreviousNeurons(Neuron[] previousNeurons){} // not used, because inputneurons dont have a previous layer of neurons


    public void receiveInput(boolean input){
        if (input == true){
            inputSum = 1;
        } else {
            inputSum = 0;
        }
    }
}
