package main;

 /*
 @author Jens Krueger
 @version 1.0
 */

import helper.Debug;
import helper.UbyteCoder;

class NetworkController {

    private InputNeuron[] inputNeurons = new InputNeuron[784];
    private HiddenNeuron[] hiddenNeurons = new HiddenNeuron[35]; // noch nicht sicher ob hier auch 784 gewählt werden sollte bzw was besser ist
    private OutputNeuron[] outputNeurons = new OutputNeuron[10];
    private Object[] imageWithLabel;
    private Integer label;
    private Boolean[] pixelArray;

    void initializeNetwork()
            //erstellt alle Neuronen und Verbindungen und verbindet sie
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
        Debug.log("Netzwerk initialisiert");
    }

    public void startLearning() {
        //startet die Lernroutine
    imageWithLabel = UbyteCoder.getImageWithLabel(0);
    label = (Integer) imageWithLabel[0];
    pixelArray = (Boolean[]) imageWithLabel[1];
    for(int i = 0; i < pixelArray.length; i++){
        //aus dem grade geholten pixelarray werden die daten an die Inputneuronen verteilt
        inputNeurons[i].setOutputValue(pixelArray[i]);
        Debug.log("Ich bin Inputneuron " + i + " und habe den Wert " + inputNeurons[i].getOutputValue());
    }
    for(InputNeuron inputNeuron: inputNeurons){
        //die senden funktion der inputneuronen wird aufgerufen
        inputNeuron.sendOutputToNextLayer();
    }
    for(HiddenNeuron hiddenNeuron: hiddenNeurons){
        Debug.log("Ich bin HiddenNeuron " + hiddenNeuron.getIdentNummer() + " und mein Wert ist " + hiddenNeuron.getOutputValue());
    }



    }
}
