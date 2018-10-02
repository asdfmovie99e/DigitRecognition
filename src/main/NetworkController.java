package main;

class NetworkController {

    private InputNeuron[] inputNeurons = new InputNeuron[784];
    private HiddenNeuron[] hiddenNeurons = new HiddenNeuron[35]; // noch nicht sicher ob hier auch 784 gewählt werden sollte bzw was besser ist
    private OutputNeuron[] outputNeurons = new OutputNeuron[10];

    void initializeNetwork()
    {
        for(int i = 0; i < inputNeurons.length; i++)
        {
            inputNeurons[i] = new InputNeuron();
        }
        for(int i = 0; i < hiddenNeurons.length; i++)
        {
            hiddenNeurons[i] = new HiddenNeuron();
        }
        for(int i = 0; i < outputNeurons.length; i++)
        {
            outputNeurons[i] = new OutputNeuron();
        }
    }
}
