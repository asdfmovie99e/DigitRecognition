package main;

 /*
 @author Jens Krueger
 @version 1.0
 */

import helper.Debug;
import helper.PictureCoder;
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
                    outputNeurons[a2].setWeight(a1,weightDoubleArray[a2 * 40 + a1 + 31360]);
                }
            }
        }
    }

    public static void startLearning() {
        //startet die Lernroutine
        int[] timesTried = new int[10];
        int[] timesSuccesful = new int[10];
        double highestWorstRate = 0d; // gehört zu for(int iDebug = 0; iDebug < 10; iDebug++){
    for(int i1 = 0; i1 < 50000; i1++) { // zum testzweck erstmal nur 100 bilder

        imageWithLabel = PictureCoder.getImageWithLabel(i1);
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
        Double worstRate = null; // darf nicht verändert werden
        for(int iDebug = 0; iDebug < 10; iDebug++){ // wird jedes 50. bild ausgeführt
            if (timesTried[iDebug] == 0 ||i1 % 50 != 0 || i1 < 10) continue;
            double succRate = 100 * (double)timesSuccesful[iDebug] / (double)timesTried[iDebug];
            Debug.log("Die Zahl " +iDebug + " ist zu folgendem Prozentsatz richtig: " + succRate );
            if(i1 % 5000 > 1000) { // if bedingung, damit nicht die ersten werte nach reset genommen werden aber garnicht wirklich sogut sondern nur noch nicht eingependelt sind
                if (worstRate == null || succRate < worstRate) worstRate = succRate;
            }

        }
        if ( worstRate != null && (int)highestWorstRate <= worstRate ){ //int cast, damit auch bei lgeicher rate eine neue datei angelegt wird
                double succGes = 0d;
                double triedGes = 0d;
                for(Integer succ: timesSuccesful){
                    succGes += succ;
                }
                for(Integer tried: timesTried){
                    triedGes += tried;
                }
                highestWorstRate = worstRate;
//                WeightSaver.initialize((int) highestWorstRate, (int) (100 * succGes / triedGes));
//                saveWeightsToFile();
        }
                        WeightSaver.initialize(555,555);
                       saveWeightsToFile();
        if (i1 % 50 == 0) Debug.log("Bild " + i1 + " abgechlossen.");
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


    private static void saveWeightsToFile(){
        for(HiddenNeuron hiddenNeuron: hiddenNeurons){
            hiddenNeuron.saveWeightsToFile();
        }
        for(OutputNeuron outputNeuron: outputNeurons){
            outputNeuron.saveWeightsToFile();
        }
        WeightSaver.writeArrayToFile();
    }

    public static double[] analyzeShrunkImage() {
        boolean[] shrunkPixelArray = PictureCoder.getShrunkImage();
        for (int i = 0; i < shrunkPixelArray.length; i++) {
            //aus dem grade geholten pixelarray werden die daten an die Inputneuronen verteilt
            inputNeurons[i].setOutputValue(shrunkPixelArray[i]);


        }
        for (InputNeuron inputNeuron : inputNeurons) {

            inputNeuron.sendOutputToNextLayer();
        }
        for (HiddenNeuron hiddenNeuron : hiddenNeurons) {

            hiddenNeuron.sendOutputToNextLayer();
        }
        OutputNeuron biggestNeuron = null;
        double[] resultArray = new double[11];
        for (OutputNeuron outputNeuron : outputNeurons) {
            resultArray[outputNeuron.getIdentNummer()] = outputNeuron.getOutputValue();
            System.out.println(outputNeuron.getIdentNummer() + "   " + outputNeuron.getOutputValue());
            if(biggestNeuron == null || biggestNeuron.getOutputValue() < outputNeuron.getOutputValue()) biggestNeuron = outputNeuron;

        }
        resultArray[10] = biggestNeuron.getIdentNummer();
        System.out.println(biggestNeuron.getIdentNummer());
        return resultArray;
    }

    private void distributeWeightsFromFile(){

    }
}


