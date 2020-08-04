/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional.part.neurofuzzy;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author femi
 */
public class GaussianFunction extends MembershipFunction {

    private double mean;
    private double standard_deviation;

    public static final String STD_DEVIATION = "standard deviation";
    public static final String MEAN = "mean";
    private final Random rand = new Random();

    public GaussianFunction(double mean, double standard_deviation) {
        this.mean = mean;
        this.standard_deviation = standard_deviation;

        getLast_delta().put(MEAN, 0.0);
        getLast_delta().put(STD_DEVIATION, 0.0);

    }

    @Override
    public void computeMiu(double input) {
        double numerator = input - mean;
        double power = numerator / standard_deviation;
        power = -1 * (power * power);

        setMiu(Math.exp(power));
        if(Double.isNaN(getMiu())){
       //     System.out.println(this +" | "+input);
        }
        
    }

    @Override
    public void correctError(double error, Map<String, Double> etas) {

        double std_eta = etas.get(GaussianFunction.STD_DEVIATION);
        double mean_eta = etas.get(GaussianFunction.MEAN);

        double std_change = this.getParameter_change_d_prediction().get(GaussianFunction.STD_DEVIATION);
        double std_delta = std_eta * std_change * error;
        double new_std = getStandard_deviation() + std_delta + (0.01 * getLast_delta().get(STD_DEVIATION));
        
        
        setStandard_deviation(new_std);
        getLast_delta().put(STD_DEVIATION, std_delta);//

        double mean_change = getParameter_change_d_prediction().get(GaussianFunction.MEAN);
        double mean_delta = mean_eta * mean_change * error;
        double new_mean = mean_delta + getMean() + (0.01 * getLast_delta().get(MEAN));
        
        
        
        setMean(new_mean);
        getLast_delta().put(MEAN, mean_delta);

    }

    @Override
    public void determineChange_dPrediction(double input, double sum_weight, Rule myRule, Rule[] otherRules) {
        Map<String, Double> changes = new HashMap<>();
        double this_inference = myRule.getConsequent().computeInference();
        double dy_dweight = 0;

        for (int i = 0; i < otherRules.length; i++) {
            double other_beta = otherRules[i].getNormalizedWeight();
            double other_inference = otherRules[i].getConsequent().computeInference();
            double numerator = other_beta * (this_inference - other_inference);
            double other_change = numerator / sum_weight;
            dy_dweight += other_change;
        }
        if (this.getMiu() == 0) {
            changes.put(STD_DEVIATION, 0.0);
            changes.put(MEAN, 0.0);

        } else {
            double dweight_dmiu = myRule.getWeight() / getMiu();

            double dy_dstd = getMiu() * 2 * Math.pow((input - getMean()), 2) / Math.pow(getStandard_deviation(), 3);

            double change_std = dy_dweight * dweight_dmiu * dy_dstd;

            double dy_dmean = getMiu() * 2 * (input - getMean()) / Math.pow(getStandard_deviation(), 2);

            double change_mean = dy_dweight * dweight_dmiu * dy_dmean;

            if (Double.isNaN(dweight_dmiu) || Double.isNaN(dy_dstd) || Double.isNaN(dy_dmean)) {
                System.out.println("HERE");
                System.out.println(getMiu());
                System.out.println(dy_dstd);
                System.out.println(dy_dmean);
                System.exit(0);
            }

            changes.put(STD_DEVIATION, change_std);
            changes.put(MEAN, change_mean);
        }
        this.setParameter_change_d_prediction(changes);
    }

    /**
     * @return the mean
     */
    public double getMean() {
        return mean;
    }

    /**
     * @param mean the mean to set
     */
    public void setMean(double mean) {
        this.mean = mean;
    }

    /**
     * @return the standard_deviation
     */
    public double getStandard_deviation() {
        return standard_deviation;
    }

    /**
     * @param standard_deviation the standard_deviation to set
     */
    public void setStandard_deviation(double standard_deviation) {
        this.standard_deviation = standard_deviation;
    }
    
    @Override
    public String toString(){
        String mean_string = String.format("Mean is: %.5f", mean);
        String std_string = String.format("Std deviation is: %.5f", standard_deviation);
        String miu_string = String.format("Miu is:%.5f", getMiu());
        
        return mean_string +":"+std_string +":"+miu_string;
    }

}
