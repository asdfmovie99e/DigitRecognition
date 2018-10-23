package main;

 /*
 @author Jens Krueger
 @version 1.0
 */

import helper.Debug;
import helper.UbyteCoder;
import helper.WeightSaver;

public class NetworkController {

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

        if(WeightSaver.getFileChoosen()){
            WeightSaver.generateDoubleArray();
            double[] weightDoubleArray = WeightSaver.getWeightDoubleArray();
            for(int a1 = 0; a1 < 784; a1++){
                for(int a2 = 0; a2 < 40; a2++){
                    hiddenNeurons[a2].setWeight(a1,weightDoubleArray[a2*784 + a1]);
                }
            }
            for(int a1 = 0; a1 < 40; a1++){
                for(int a2 = 0; a2 < 10; a2++){
                    outputNeurons[a2].setWeight(a1,weightDoubleArray[a2 * 40 + a1]);
                }
            }
        }
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

            inputNeuron.sendOutputToNextLayer();
        }
        for (HiddenNeuron hiddenNeuron : hiddenNeurons) {

            hiddenNeuron.sendOutputToNextLayer();
        }
        OutputNeuron biggestNeuron = null;
        for (OutputNeuron outputNeuron : outputNeurons) {
            if(biggestNeuron == null || biggestNeuron.getOutputValue() < outputNeuron.getOutputValue()) biggestNeuron = outputNeuron;

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
            WeightSaver.initialize();
            saveWeightsToFile();
            WeightSaver.writeArrayToFile();
        }
        if (i1 % 50 == 0) Debug.log("Bild " + i1 + " abgechlossen.");
        //ab hier faengt das eigentliche lernen an.
        // Zuerst werden die gewichte zu den outputneuronen geaendert
        if( worstNeuron[0] == (double)label) setCustomFactor(1.5); else setCustomFactor(1);
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


    private static void saveWeightsToFile(){
        for(HiddenNeuron hiddenNeuron: hiddenNeurons){
            hiddenNeuron.saveWeightsToFile();
        }
        for(OutputNeuron outputNeuron: outputNeurons){
            outputNeuron.saveWeightsToFile();
        }
    }

    private void distributeWeightsFromFile(){

    }
}


