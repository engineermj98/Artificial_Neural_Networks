
package ann;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class ANN extends Agent{
Dataset ds = new Dataset();
Model model = new Model();
private Gui myGui;
private ANN myAgent;
public double x1,x2,x3;
@Override
    public void setup(){
        System.out.println("Agent "+getLocalName()+" started.");
        addBehaviour(new ArtificialNeuralNetwork());
        myGui = new Gui(ANN.this);
        myGui.showGui();

    }
        private class ArtificialNeuralNetwork extends Behaviour{
        int cont = 0;

        @Override
        public void action() {
            ArrayList<Double> x = new ArrayList<>();
            ArrayList<Double> w = new ArrayList<>();
            ArrayList<Double> b = new ArrayList<>();
            x.add((double)x1);
            x.add((double)x2);
            x.add((double)x3); 
            ds.setX(x);   
                ACLMessage msg3 = myAgent.receive();
                if (msg3 != null) {
                        //JOptionPane.showMessageDialog(null, " x2: "+msg3.getContent());                 
                        msg3.getContent();
                model.recalculus(model.output(model.zOutput(model.weights(), model.hidden(model.zHidden(model.weights(), x, model.bias())),
                    model.bias())), 
                    model.weights(), 
                    model.derivativesEWeights(model.output(model.zOutput(model.weights(), model.hidden(model.zHidden(model.weights(), x, model.bias())),
                    model.bias())), 
                            model.derivativesZoWeights(model.hidden(model.zHidden(model.weights(), x, model.bias()))), 
                            model.derivativesEHidden(model.derivativesEOutput(model.output(model.zOutput(model.weights(), model.hidden(model.zHidden(model.weights(), x, model.bias())),
                    model.bias()))), model.derivativesOutputZo(model.output(model.zOutput(model.weights(), model.hidden(model.zHidden(model.weights(), x, model.bias())),
                    model.bias()))), model.weights()), 
                            model.derivativesHiddenZh(model.hidden(model.zHidden(model.weights(), x, model.bias()))), 
                            model.derivativesZhWeights(x)), 
                    model.derivativesEBias(model.derivativesEOutput(model.output(model.zOutput(model.weights(), model.hidden(model.zHidden(model.weights(), x, model.bias())),
                    model.bias()))), 
                    model.derivativesOutputZo(model.output(model.zOutput(model.weights(), model.hidden(model.zHidden(model.weights(), x, model.bias())),
                    model.bias()))), 
                    model.derivativesZoBias(model.hidden(model.zHidden(model.weights(), x, model.bias()))), 
                    model.derivativesZoHidden(model.output(model.zOutput(model.weights(), model.hidden(model.zHidden(model.weights(), x, model.bias())),
                    model.bias())), model.weights()), 
                    model.derivativesHiddenZh(model.hidden(model.zHidden(model.weights(), x, model.bias()))), 
                    model.derivativesZhBias(model.output(model.zOutput(model.weights(), model.hidden(model.zHidden(model.weights(), x, model.bias())),
                    model.bias())), model.weights())), 
                    model.bias(), 
                    x);
			}
                        else block(); 
                myGui.txtx3.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            x1 = Double.parseDouble(msg3.getContent());
                            x2 = Double.parseDouble(msg3.getContent());
                            x3 = Double.parseDouble(msg3.getContent());
                            cont+=1; 
                            }
                        });       
        }

        @Override
        public boolean done() {
            if(cont == 1){
                return true;
            }else{
                return false;
            }
        }
        @Override
        public int onEnd() {
            //myAgent.doDelete();
            return super.onEnd();
        }

        }

    
}
