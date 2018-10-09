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
    for(int i1 = 0; i1 < 1000; i1++) { // zum testzweck erstmal nur 100 bilder


        imageWithLabel = UbyteCoder.getImageWithLabel(i1);
        label = (Integer) imageWithLabel[0];
        Debug.log("Jetzt kommt ein Bild mit der Zahl " + label);
        pixelArray = (Boolean[]) imageWithLabel[1];
        for (int i = 0; i < pixelArray.length; i++) {
            //aus dem grade geholten pixelarray werden die daten an die Inputneuronen verteilt
            inputNeurons[i].setOutputValue(pixelArray[i]);

        }

        for (InputNeuron inputNeuron : inputNeurons) {
            Debug.log("Ich bin Inputneuron " + inputNeuron.getIdentNummer() + " und habe den Wert " + inputNeuron.getOutputValue(), false);
            inputNeuron.sendOutputToNextLayer();
        }
        for (HiddenNeuron hiddenNeuron : hiddenNeurons) {
            //Debug.log("Ich bin HiddenNeuron " + hiddenNeuron.getIdentNummer() + " und mein Wert ist " + hiddenNeuron.getOutputValue());
            hiddenNeuron.sendOutputToNextLayer();
        }
        Debug.log("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        for (OutputNeuron outputNeuron : outputNeurons) {
            Debug.log("Ich bin OutputNeuron " + outputNeuron.getIdentNummer() + " und mein Wert ist " + outputNeuron.getOutputValue() + ". Richtig war die Nummer " + label);
        }
        //ab hier faengt das eigentliche lernen an.
        // Zuerst werden die gewichte zu den outputneuronen geaendert
        for (OutputNeuron outputNeuron : outputNeurons) {
            if (outputNeuron.getIdentNummer() == label) {
                outputNeuron.modWeight(1);
            } else {
                outputNeuron.modWeight(0);
            }
        }
        for (HiddenNeuron hiddenNeuron : hiddenNeurons) {
            hiddenNeuron.modWeight();
        }
    }
    }
}
