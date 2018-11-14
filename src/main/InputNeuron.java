package main;

 /*
 @author Jens Krueger
 @version 1.0
 */


public class InputNeuron {

    private double outputValue;
    private HiddenNeuron[] hiddenNeurons;
    private Integer identNummer = null;

    public void setHiddenNeurons(HiddenNeuron[] hiddenNeurons){
        //füllt das Array hiddenNeurons mit den Neuronen der nächsten schicht
        this.hiddenNeurons = hiddenNeurons;
    }

    public void setIdentNummer(int identNummer){
        // setzt die Identifikationsnummer des Neurons
        this.identNummer = identNummer;
    }

    public void setOutputValue(Double outputValue){
    this.outputValue = outputValue;
    int wfasfasdfsadfsdf;
    }

    public void sendOutputToNextLayer(){
        //sendet den Outputwert an die nächste schicht. diese empfängt ihn mit der receive methode
        for(HiddenNeuron hiddenNeuron: hiddenNeurons){
            hiddenNeuron.receive(identNummer, outputValue);
        }
    }

    public double getOutputValue(){
        //wird eigentlich nie benutzt. nur fuer debug zwecke
        return outputValue;
    }

    public int getIdentNummer(){
        //nur fuer debug eigentlich
        return identNummer;
    }
}
