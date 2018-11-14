package main;

import helper.MathHelper;

import java.util.HashMap;

public class HiddenLearnThread extends Thread{

    HiddenNeuron hn = null;



    public void setHn(HiddenNeuron hn){
        this.hn = hn;
    }

    public void run(){
        try {
       while(true){
               Thread.sleep(50);
       }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void calc() {
        //die Variablen wie z.B. smallDelta beziehen sich auf die Delta Lernregel. Die Formel ist im Internet leicht zu finden.
        double oldValue = hn.getOutputValue(); //debug purpose
        double smallDelta = 0;
        HashMap<Integer, Double> weightMap = hn.getWeightMap();
        HashMap<Integer, Double> inputMap = hn.getInputMap();
        for(int i = 0; i < 10; i++){
            smallDelta += hn.getOutputNeurons()[i].getSmallDelta() * hn.getOutputNeurons()[i].getWeight(hn.getIdentNummer());
        }
        double epsilon = 0.01f; // vollkommen experimentell. keine ahnung wie der wert gewählt werden soll
        for(int i = 0; i < 748; i++){
            double input = inputMap.get(i);
            double ableitung = MathHelper.sigmoidApprox(inputMap.get(i)) * (1 - MathHelper.sigmoidApprox(inputMap.get(i)));
            double bigDelta = epsilon * smallDelta * input * ableitung;
            double oldWeight = weightMap.get(i);
            //if(oldWeight + bigDelta > 1 || oldWeight + bigDelta < -1) return; // NUR TEST
            hn.setWeight(i, oldWeight + bigDelta);
        }
        //ab hier debug purposes
        //Debug.log("Der Unterschied von HiddenNeuron " + getIdentNummer() + " ist " + (getOutputValue() - oldValue));
    }
}
