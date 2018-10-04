package main;

import helper.UbyteCoder;

class NetworkController {

    private InputNeuron[] inputNeurons = new InputNeuron[784];
    private HiddenNeuron[] hiddenNeurons = new HiddenNeuron[35]; // noch nicht sicher ob hier auch 784 gewählt werden sollte bzw was besser ist
    private OutputNeuron[] outputNeurons = new OutputNeuron[10];

    void initializeNetwork()
    {
        for(int i = 0; i < inputNeurons.length; i++)
        {
            inputNeurons[i] = new InputNeuron();
            inputNeurons[i].setIdentNumber(i);
        }
        for(int i = 0; i < hiddenNeurons.length; i++)
        {
            hiddenNeurons[i] = new HiddenNeuron();
            hiddenNeurons[i].setIdentNumber(i);
        }
        for(int i = 0; i < outputNeurons.length; i++)
        {
            outputNeurons[i] = new OutputNeuron();
            outputNeurons[i].setIdentNumber(i);
        }
        for(Neuron neuron: inputNeurons){
            neuron.setNextNeurons(hiddenNeurons);
        }
        for(Neuron neuron: hiddenNeurons){
            neuron.setNextNeurons(outputNeurons);
            neuron.setPreviousNeurons(inputNeurons);
        }
        for(Neuron neuron: outputNeurons){
            neuron.setPreviousNeurons(hiddenNeurons);
        }
    }

    public void startLearning(){
        for(Neuron neuron: hiddenNeurons){
            neuron.generateMaps(); // darf nur einmal ingsgesamt ausgeführt werden
            neuron.resetInputMap();
        }
        for(Neuron neuron: outputNeurons){
            neuron.generateMaps(); // darf nur einmal ingsgesamt ausgeführt werden
            neuron.resetInputMap();
        }
        for(Neuron neuron: inputNeurons){
            neuron.generateMaps(); // darf nur einmal ingsgesamt ausgeführt werden
            neuron.resetInputMap();
        }
        for(int i1 = 0 ; i1 < 1000; i1++) {
            Object[] imageWithLabel = UbyteCoder.getImageWithLabel(i1); // [0] lable; [1] pixel
            Boolean[] pixelArray = (Boolean[]) imageWithLabel[1];
            int label =(int) imageWithLabel[0];
            for (int i = 0; i < 748; i++) {
                inputNeurons[i].resetInputMap();
                inputNeurons[i].receiveInput(pixelArray[i]);
            }
            for (InputNeuron neuron : inputNeurons) {
                neuron.sendOutput();
            }
            for (HiddenNeuron neuron : hiddenNeurons) {
                neuron.sendOutput();
                neuron.resetInputMap();
            }
            for (OutputNeuron neuron : outputNeurons) {
                System.out.println(neuron.identNumber + "  " + neuron.getOutputValue());
                neuron.resetInputMap();
            }
            for (OutputNeuron neuron : outputNeurons) {
                if( neuron.identNumber == label){
                    neuron.adjustWeights(100);
                } else {
                    neuron.adjustWeights(0);
                }
            }
            for (HiddenNeuron neuron : hiddenNeurons) {
                neuron.adjustWeights();
            }
        }
    }
}
