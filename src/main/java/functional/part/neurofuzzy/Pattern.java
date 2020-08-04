/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional.part.neurofuzzy;

/**
 *
 * @author femi
 */
public class Pattern {

    private double[] input_values;
    private double desired_output;

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
     * @return the desired_output
     */
    public double getDesired_output() {
        return desired_output;
    }

    /**
     * @param desired_output the desired_output to set
     */
    public void setDesired_output(double desired_output) {
        this.desired_output = desired_output;
    }

    @Override
    public String toString() {
        double out = getDesired_output();
        double[] in = getInput_values();
        String s = "";
        for (double i : in) {
            s += (i + ",");
        }
        s += (" | " + out);
        return s;
    }
}
