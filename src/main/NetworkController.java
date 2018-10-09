package main;

import helper.Debugger;
import helper.UbyteCoder;

class NetworkController {

    private InputNeuron[] inputNeurons = new InputNeuron[784];
    private HiddenNeuron[] hiddenNeurons = new HiddenNeuron[35]; // noch nicht sicher ob hier auch 784 gewählt werden sollte bzw was besser ist
    private OutputNeuron[] outputNeurons = new OutputNeuron[10];

    void initializeNetwork()
    {
        for(int i = 0; i < 784; i++){
            inputNeurons[i] = new InputNeuron();
            inputNeurons[i].setIdentNummer(i);
        }
        for (int i = 0; i < 35; i++){
            hiddenNeurons[i] = new HiddenNeuron();
            hiddenNeurons[i].setIdentNummer(i);
            hiddenNeurons[i].generateNewWeightMap();
        }
        for(int i = 0; i < 10; i++){
            outputNeurons[i] = new OutputNeuron();
            outputNeurons[i].setIdentNummer(i);
            outputNeurons[i].generateNewWeightMap();
        }
        for(InputNeuron inputNeuron: inputNeurons){
            inputNeuron.setHiddenNeurons(hiddenNeurons);
        }
        for(HiddenNeuron hiddenNeuron: hiddenNeurons){
            hiddenNeuron.setOutputNeurons(outputNeurons);
        }
        Debugger.log("Netzwerk initialisiert");
    }

    public void startLearning() {



    }
}
