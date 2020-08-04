/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional.part.neurofuzzy;

import java.util.Random;

/**
 *
 * @author femi
 */
public class Consequent {

    private double[] parameters;
    private double[] input_values;
    private double bias;

    private double[] last_dchange;

    private final Random rand = new Random();

    public Consequent(double[] parameters, double bias) {
        this.parameters = parameters;
        this.bias = bias;
        last_dchange = new double[parameters.length + 1];

    }

    /**
     * @return the parameters
     */
    public double[] getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(double[] parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the input_values
     */
    public double[] getInput_values() {
        return input_values;
    }

    /**
     * @param input_values the input_values to set
     */
    public void setInput_values(double[] input_values) {
        this.input_values = input_values;
    }

    /**
     * @return the bias
     */
    public double getBias() {
        return bias;
    }

    /**
     * @param bias the bias to set
     */
    public void setBias(double bias) {
        this.bias = bias;
    }

    public void correctError(double error, double eta, double normalized_weight) {
        //  System.out.print(normalized_weight+" "+" "+error+" "+eta+" | ");
        for (int i = 0; i < input_values.length; i++) {
            double input_value = input_values[i];
            double parameter_delta = eta * error * normalized_weight * input_value;

            parameters[i] += (parameter_delta + last_dchange[i] * 0.009);//0.009
            last_dchange[i] = parameter_delta;
        }
        double bias_delta = eta * error * normalized_weight;
        // System.out.print(" oldbias "+bias);
        bias += (bias_delta + last_dchange[last_dchange.length - 1] * 0.009);
        last_dchange[last_dchange.length - 1] = bias_delta;
        // System.out.print(" newbias "+bias);
        // System.out.println("\n....................................");
    }

    public double computeInference() {
        double f = bias;

        for (int i = 0; i < input_values.length; i++) {
            double p_f = input_values[i] * parameters[i];
            f += p_f;
        }

        return f;
    }

    public double determineMaxDchange(double normalizedWeight) {
        double max = Math.abs(normalizedWeight);

        for (int i = 0; i < input_values.length; i++) {
            double p_change = Math.abs(normalizedWeight * input_values[i]);
            if (max < p_change) {
                max = p_change;
            }
        }

        return max;
    }

    /**
     * @return the last_dchange
     */
    public double[] getLast_dchange() {
        return last_dchange;
    }

    /**
     * @param last_dchange the last_dchange to set
     */
    public void setLast_dchange(double[] last_dchange) {
        this.last_dchange = last_dchange;
    }

    @Override
    public String toString() {
        String s = "[";
        for (double d : this.getParameters()) {
            String e = formatOutput(d);
            String all = e + "X";
            s += (all+" ");
        }
        String biased = formatOutput(bias);
        return s += (biased +"]");
    }

    private String formatOutput(double entry) {
        String prefix = entry < 0 ? "" : "+";
        String entry_string = String.format("%.5f", entry);
        return prefix + entry_string;
    }
}
