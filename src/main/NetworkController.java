package main;

 /*
 @author Jens Krueger
 @version 1.0
 */

import helper.Debug;
import helper.UbyteCoder;

class NetworkController {

    private InputNeuron[] inputNeurons = new InputNeuron[784];
    private HiddenNeuron[] hiddenNeurons = new HiddenNeuron[40]; // noch nicht sicher ob hier auch 784 gewählt werden sollte bzw was besser ist
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
        for (int i = 0; i < 40; i++){
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
        //die naechsten beiden variablen sind nur fuer debugzwecke
        int timesTried = 0;
        int timesSuccesful = 0;
    for(int i1 = 0; i1 < 50000; i1++) { // zum testzweck erstmal nur 100 bilder
        if(timesTried % 1000 == 0){//debug purpose
            timesSuccesful = 0;
            timesTried = 0;
        }
        timesTried++;

        imageWithLabel = UbyteCoder.getImageWithLabel(i1);
        label = (Integer) imageWithLabel[0];
        Debug.log("Jetzt kommt ein Bild mit der Zahl " + label);
        pixelArray = (Boolean[]) imageWithLabel[1];
        for (int i = 0; i < pixelArray.length; i++) {
            //aus dem grade geholten pixelarray werden die daten an die Inputneuronen verteilt
            inputNeurons[i].setOutputValue(pixelArray[i]);

        }

        for (InputNeuron inputNeuron : inputNeurons) {
            //System.out.println("Ich bin Inputneuron " + inputNeuron.getIdentNummer() + " und habe den Wert " + inputNeuron.getOutputValue());
            inputNeuron.sendOutputToNextLayer();
        }
        for (HiddenNeuron hiddenNeuron : hiddenNeurons) {
            //Debug.log("Ich bin HiddenNeuron " + hiddenNeuron.getIdentNummer() + " und mein Wert ist " + hiddenNeuron.getOutputValue());
            hiddenNeuron.sendOutputToNextLayer();
        }
        OutputNeuron biggestNeuron = null;
        for (OutputNeuron outputNeuron : outputNeurons) {
            if(biggestNeuron == null || biggestNeuron.getOutputValue() < outputNeuron.getOutputValue()) biggestNeuron = outputNeuron;
           System.out.println("Ich bin OutputNeuron " + outputNeuron.getIdentNummer() + " und mein Wert ist " + outputNeuron.getOutputValue() + ". Richtig war die Nummer " + label);
        }
        if(biggestNeuron.getIdentNummer() == label) timesSuccesful++;//debug purpose
        Debug.log("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + (biggestNeuron.getIdentNummer() == label) + " " + 100 * (double)timesSuccesful / (double)timesTried);
        Debug.log("Bild Nummer: " + i1);
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
