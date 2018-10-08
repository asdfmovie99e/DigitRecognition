package main;

public class InputNeuron {

    private int outputValue;
    private HiddenNeuron[] hiddenNeurons;
    private Integer identNummer = null;

    public void setHiddenNeurons(HiddenNeuron[] hiddenNeurons){
        this.hiddenNeurons = hiddenNeurons;
    }

    public void setIdentNummer(int identNummer){
        this.identNummer = identNummer;
    }

    public void setOutputValue(boolean outputBool){
        if (outputBool){
            outputValue = 1;
        } else
        {
            outputValue = 0;
        }
    }

    public void sendOutputToNextLayer(){
        for(HiddenNeuron hiddenNeuron: hiddenNeurons){
            hiddenNeuron.receive(identNummer, outputValue);
        }
    }
}
