package main;

 /*
 @author Jens Krueger
 @version 1.0
 */

import helper.Debug;
import helper.UbyteCoder;

class NetworkController {

    private static InputNeuron[] inputNeurons = new InputNeuron[784];
    private static HiddenNeuron[] hiddenNeurons = new HiddenNeuron[40]; // noch nicht sicher ob hier auch 784 gewählt werden sollte bzw was besser ist
    private static OutputNeuron[] outputNeurons = new OutputNeuron[10];
    private static  Object[] imageWithLabel;
    private static Integer label;
    private static Boolean[] pixelArray;
    private static double customFactor = 1d;

    static void  initializeNetwork()
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

    public static void startLearning() {
        //startet die Lernroutine
        int[] timesTried = new int[10];
        int[] timesSuccesful = new int[10];
    for(int i1 = 0; i1 < 50000; i1++) { // zum testzweck erstmal nur 100 bilder

        imageWithLabel = UbyteCoder.getImageWithLabel(i1);
        label = (Integer) imageWithLabel[0];
        //Debug.log("Jetzt kommt ein Bild mit der Zahl " + label);
        pixelArray = (Boolean[]) imageWithLabel[1];
        for (int i = 0; i < pixelArray.length; i++) {
            //aus dem grade geholten pixelarray werden die daten an die Inputneuronen verteilt
            inputNeurons[i].setOutputValue(pixelArray[i]);

        }

        for(int i4 = 0; i4 < 10; i4++){
            if(i1 % 5000 == 0){
                timesTried[i4] = 0;
                timesSuccesful[i4] = 0;
            }
        }
        timesTried[label]++;

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
           //System.out.println("Ich bin OutputNeuron " + outputNeuron.getIdentNummer() + " und mein Wert ist " + outputNeuron.getOutputValue() + ". Richtig war die Nummer " + label);
        }
        Double[] worstNeuron = new Double[2]; //0. platz ist neuron ident und der 1. platz ist der wert
        for(int i6 = 0; i6 < 10; i6++){
            //gets the digit which have been recognized worst
            if(worstNeuron[0] == null || (double)timesSuccesful[i6] / (double)timesTried[i6] < worstNeuron[1]){
                worstNeuron[0] = (double) i6;
                worstNeuron[1] = (double)timesSuccesful[i6] / (double)timesTried[i6];
            }
        }

        if(biggestNeuron.getIdentNummer() == label) {
            timesSuccesful[label]++;//debug purpose
        }
        for(int iDebug = 0; iDebug < 10; iDebug++){
            if (timesTried[iDebug] == 0 ||i1 % 50 != 0) continue;
            Debug.log("Die Zahl " +iDebug + " ist zu folgendem Prozentsatz richtig: " + 100 * (double)timesSuccesful[iDebug] / (double)timesTried[iDebug]);
        }
        if (i1 % 50 == 0) Debug.log("Bild " + i1 + " abgechlossen.");
        //ab hier faengt das eigentliche lernen an.
        // Zuerst werden die gewichte zu den outputneuronen geaendert
        //if( worstNeuron[0] == (double)label) setCustomFactor(1.5); else setCustomFactor(1); // klappt glaube ich nicht so dolle
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

    public static double getCustomFactor(){
        return  customFactor;
    }

    private static void setCustomFactor(double ff){
        customFactor = ff;
    }
}
