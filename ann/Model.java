
package ann;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Model {
    DecimalFormat df = new DecimalFormat("#.####");
    public double t1 = 0.1, t2 = 0.05, alpha = 0.01, Error = 0.00;
    public double[] weights(){//n = ds.getX.length
        double[]w = new double[10];
        double num = 1;
        for(int i = 0; i < 10; i++){
            w[i]= num/10;
            if(i > 8) w[i]= num/100;
            //System.out.println(w[i]);
            num++;
        }
        return w;
    }
    public double[] zHidden(double[] w, ArrayList<Double> x, ArrayList<Double> b){
        double[]zh = new double[100];
        zh[0] = w[0]*x.get(0)+w[2]*x.get(1)+w[4]*x.get(2)+b.get(0);
        zh[1] = w[1]*x.get(0)+w[3]*x.get(1)+w[5]*x.get(2)+b.get(0);
        return zh;
    }
    public ArrayList<Double> hidden(double[] zh){
        ArrayList<Double> h = new ArrayList<>();
        h.add(1/(1+Math.pow(2.718,-(zh[0]))));
        h.add(1/(1+Math.pow(2.718,-(zh[1]))));
        return h;
    }
    public ArrayList<Double> zOutput(double[] w, ArrayList<Double> h, ArrayList<Double> b){
        ArrayList<Double> zo = new ArrayList<>();
        zo.add(w[6]*h.get(0)+w[8]*h.get(1)+b.get(1));
        zo.add(w[7]*h.get(0)+w[9]*h.get(1)+b.get(1));
        return zo;
    }
    public ArrayList<Double> output(ArrayList<Double> zo){
        ArrayList<Double> o = new ArrayList<>();
        o.add(1/(1+Math.pow(2.718,-(zo.get(0)))));
        o.add(1/(1+Math.pow(2.718,-(zo.get(1)))));
        return o;
    }
    public ArrayList<Double> derivativesZoWeights(ArrayList<Double> h){
        ArrayList<Double> dZodW = new ArrayList<>();
        dZodW.add(h.get(0)); //Derivative of Zo1 in respect of W7
        dZodW.add(h.get(0)); //Derivative of Zo2 in respect of W8
        dZodW.add(h.get(1)); //Derivative of Zo1 in respect of W9
        dZodW.add(h.get(1)); //Derivative of Zo2 in respect of W10
        return dZodW;
    }
    public ArrayList<Double> derivativesEWeights(ArrayList<Double> o, ArrayList<Double> dZodW, ArrayList<Double> dEdH,ArrayList<Double> dHdZh, ArrayList<Double> dZhdW){
        ArrayList<Double> dEdW = new ArrayList<>();
        dEdW.add((o.get(0)-t1)*(o.get(0)*(1-o.get(0)))*dZodW.get(0)); //dEdW7 = (o1-t1)*(o1*(1-o1))*dZo1dW7 =  dEdO1* dO1dZo1 *dZo1dW7
        dEdW.add((o.get(0)-t1)*(o.get(0)*(1-o.get(0)))*dZodW.get(2)); //dEdW9  Zo1 in respect of W9
        dEdW.add(dEdH.get(0)*dHdZh.get(0)*dZhdW.get(0)); //dEdW1 = dEdH1*dH1dZh1*dZh1dW1;    
        dEdW.add(dEdH.get(0)*dHdZh.get(0)*dZhdW.get(1)); //dEdW3 (Deriv Error/ Deriv h1) * (d H1/ d Zh1) * (d Zh1/ d w3)
        dEdW.add(dEdH.get(0)*dHdZh.get(0)*dZhdW.get(2)); // //dEdW5 = dEdH1*dH1dZh1*dZh1dW1; 
        dEdW.add((o.get(1)-t2)*(o.get(1)*(1-o.get(1)))*dZodW.get(1)); //dEdW8 = (o2-t2)*(o2*(1-o2))*dZo2dW8 equivale a  dEdO1* dO1dZo1 *dZo1dW7
        dEdW.add((o.get(1)-t2)*(o.get(1)*(1-o.get(1)))*dZodW.get(3)); //dEdW10  Zo2 in respect of W10
        dEdW.add(dEdH.get(1)*dHdZh.get(1)*dZhdW.get(3)); //dEdW2 = dEdH2*dH2dZh2*dZh2dW2;  
        dEdW.add(dEdH.get(1)*dHdZh.get(1)*dZhdW.get(4)); //dEdW4 = dEdH2*dH2dZh2*dZh2dW4;
        dEdW.add(dEdH.get(1)*dHdZh.get(1)*dZhdW.get(5)); //dEdW6 = dEdH2*dH2dZh2*dZh2dW6;    
        return dEdW;
    }
    public ArrayList<Double> derivativesZoBias(ArrayList<Double> h){
        ArrayList<Double> dZodB = new ArrayList<>();
        dZodB.add((double)1); // derivative of Zo1 - B2
        dZodB.add((double)1); // derivative of Zo2 - B2
        return dZodB;
    }
    public ArrayList<Double> derivativesEHidden(ArrayList<Double> dEdO, ArrayList<Double> dOdZo, double[] w){
        ArrayList<Double> dEdH = new ArrayList<>();
        dEdH.add(dEdO.get(0)*dOdZo.get(0)*w[6] + dEdO.get(1)*dOdZo.get(1)*w[8]); // dEdH1= dEdO1*dO1dZo1*w[6] + dEdO2*dO2dZo2*w[8];
        dEdH.add(dEdO.get(1)*dOdZo.get(1)*w[9] + dEdO.get(0)*dOdZo.get(0)*w[7]); // dEdH2= dEdO2*dO2dZo2*w[9] + dEdO1*dO1dZo1*w[8];
        return dEdH;
    }
    public ArrayList<Double> derivativesHiddenZh(ArrayList<Double> h){
        ArrayList<Double> dHdZh = new ArrayList<>();
        dHdZh.add(h.get(0)*(1-h.get(0))); // dH1/dZh1 = h1*(1-h1);, d= Deriv
        dHdZh.add(h.get(1)*(1-h.get(1))); // dH2/dZh2  d= Deriv
        return dHdZh;
    }
    public ArrayList<Double> derivativesZhWeights(ArrayList<Double> x){
       ArrayList<Double> dZhdW = new ArrayList<>();
        dZhdW.add(x.get(0)); //dZh1dW1 = x1;
        dZhdW.add(x.get(1)); //dZh1/dW3 = x2;
        dZhdW.add(x.get(2)); //dZh1/dW5 = x3;
        dZhdW.add(x.get(0)); //dZh2dW2 = x1;
        dZhdW.add(x.get(1)); //dZh2dW4 = x2;
        dZhdW.add(x.get(2)); //dZh2dW6 = x3;
        return dZhdW;
    }
    public ArrayList<Double> derivativesEBias(ArrayList<Double> dEdO, ArrayList<Double> dOdZo, ArrayList<Double> dZodB,
            ArrayList<Double> dZodH, ArrayList<Double> dHdZh,  ArrayList<Double> dZhdB){
        ArrayList<Double> dEdB = new ArrayList<>();
        dEdB.add(dEdO.get(0)*dOdZo.get(0)*dZodB.get(0) + dEdO.get(1)*dOdZo.get(1)*dZodB.get(1)); //dEdB2 = dEdO1*dO1dZo1*dZo1dB2 + dEdO2*dO2dZo2*dZo2dB2;  
        dEdB.add(dEdO.get(0)*dOdZo.get(0)*dZodH.get(0)*dHdZh.get(0)*dZhdB.get(0)+dEdO.get(1)*dOdZo.get(1)*dZodH.get(1)*dHdZh.get(1)*dZhdB.get(1)); //dEdB1
        return dEdB;
    }
    public ArrayList<Double> derivativesEOutput(ArrayList<Double> o){//n = ds.getX.length
        ArrayList<Double> dEdO = new ArrayList<>();
        dEdO.add((o.get(0)-t1)); // Deriv Error respect deriv Output 1
        dEdO.add((o.get(1)-t2)); // Deriv Error respect deriv Output 2
        return dEdO;
    }
    public ArrayList<Double> derivativesOutputZo(ArrayList<Double> o){//n = ds.getX.length
        ArrayList<Double> dOdZo = new ArrayList<>();
        dOdZo.add((o.get(0)*(1-o.get(0)))); // Deriv Output 1 respect deriv Z Output 1
        dOdZo.add((o.get(1)*(1-o.get(1)))); // Deriv Output 2 respect deriv Z Output 2
        return dOdZo;
    }
    public ArrayList<Double> derivativesZoHidden(ArrayList<Double> o, double[] w){//n = ds.getX.length
        ArrayList<Double> dZodH = new ArrayList<>();
        dZodH.add(w[6]); //deriv Zo1 respect of h1 : W7
        dZodH.add(w[9]); //deriv Zo2 respect of h2 : W10
        return dZodH;
    }
    public ArrayList<Double> derivativesZhBias(ArrayList<Double> o, double[] w){//n = ds.getX.length
        ArrayList<Double> dZhdB = new ArrayList<>();
        dZhdB.add((double)1); //Deriv Zh1 respect of Bias1 : 1
        dZhdB.add((double)1); //Deriv Zh2 respect of Bias1 : 1
        return dZhdB;
    }
    
    public void recalculus(ArrayList<Double> o, double[] w, ArrayList<Double> dEdW, ArrayList<Double> dEdB, ArrayList<Double> b, ArrayList<Double> x){
        double[] bias = new double[100];
        double[] Zh = new double[100];
        double[] Zo = new double[100];
        double[] Output = new double[100];
        double o1,o2,h1,h2;
        int iterations = 0;
        Error = ((Math.pow((o.get(0)-t1),2)+Math.pow((o.get(1)-t2),2)))/2;
        System.out.println("Error total inicial: "+df.format(Error));
        do{
            w[0] = w[0]-alpha*dEdW.get(2); //w1
            w[2] = w[2]-alpha*dEdW.get(3); //w3
            w[4] = w[4]-alpha*dEdW.get(4); //w5
            w[6] = w[6]-alpha*dEdW.get(0); //w7
            w[8] = w[8]-alpha*dEdW.get(1); //w9
            w[1] = w[1]-alpha*dEdW.get(7); //w2
            w[3] = w[3]-alpha*dEdW.get(8); //w4
            w[5] = w[5]-alpha*dEdW.get(9); //w6
            w[7] = w[7]-alpha*dEdW.get(5); //w8
            w[9] = w[9]-alpha*dEdW.get(6); //w10
            bias[0] = b.get(0)-alpha*dEdB.get(0); //bias 2
            bias[1] = b.get(1)-alpha*dEdB.get(1); //bias 1
            // For each neurone
                Zh[0] = w[0]*x.get(0)+w[2]*x.get(1)+w[4]*x.get(2)+b.get(0);
                Zh[1] = w[1]*x.get(0)+w[3]*x.get(1)+w[5]*x.get(2)+b.get(0);
                h1 = 1/(1+Math.pow(2.718,-Zh[0]));
                h2 = 1/(1+Math.pow(2.718,-Zh[1]));
                Zo[0] = w[6]*h1+w[8]*h2+bias[1]; //Hidden to output
                Zo[1] = w[7]*h1+w[9]*h2+bias[1];
                o1 = 1/(1+Math.pow(2.718,-Zo[0]));
                o2 = 1/(1+Math.pow(2.718,-Zo[1]));
                Error = ((Math.pow((o1-t1),2)+Math.pow((o2-t2),2)))/2;
                System.out.println("O1: "+df.format(o1));
                System.out.println("O2: "+df.format(o2));
                System.out.println("Error: "+df.format(Error));
                iterations++;
        }while(Error > 0.1);
        System.out.println("ACTUALIZED WEIGHTS AND BIAS VALUES");
        System.out.println("W1: "+df.format(w[0]));
        System.out.println("W2: "+df.format(w[1]));
        System.out.println("W3: "+df.format(w[2]));
        System.out.println("W4: "+df.format(w[3]));
        System.out.println("W5: "+df.format(w[4]));
        System.out.println("W6: "+df.format(w[5]));
        System.out.println("W7: "+df.format(w[6]));
        System.out.println("W8: "+df.format(w[7]));
        System.out.println("W9: "+df.format(w[8]));
        System.out.println("W10: "+df.format(w[9]));     
        System.out.println("Bias1: "+df.format(bias[1]));
        System.out.println("Bias2: "+df.format(bias[0])); 
        System.out.println("Output1: "+df.format(o1));
        System.out.println("Output2: "+df.format(o2));
        System.out.println("Iterations: "+iterations);
        System.out.println("Error: "+Error);
    }


    public ArrayList<Double> bias(){//n = ds.getX.length
        ArrayList<Double> b = new ArrayList<>();
        b.add(0.5);
        b.add(0.5);
        return b;
    }
        
}
