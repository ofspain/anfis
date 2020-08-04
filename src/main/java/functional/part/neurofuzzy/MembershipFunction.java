/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional.part.neurofuzzy;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author femi
 */
public abstract class MembershipFunction {

    /**
     * @param miu the miu to set
     */
    protected void setMiu(double miu) {
        this.miu = miu;
    }
    /**
     * @return the parameter_change_d_prediction
     */
    public Map<String,Double> getParameter_change_d_prediction() {
        return parameter_change_d_prediction;
    }

    /**
     * @param parameter_change_d_prediction the parameter_change_d_prediction to set
     */
    protected void setParameter_change_d_prediction(Map<String,Double> parameter_change_d_prediction) {
        this.parameter_change_d_prediction = parameter_change_d_prediction;
    }
    
    /**
     * @return the last_delta
     */
    public Map<String,Double> getLast_delta() {
        return last_delta;
    }

    
    private double miu;
    private Map<String,Double> parameter_change_d_prediction = new HashMap<>();
    private final Map<String,Double> last_delta = new HashMap<>();
    public abstract void  computeMiu(double input);
    public abstract void correctError(double error,Map<String,Double> etas);
    public abstract void determineChange_dPrediction(double input,double sum_weight,Rule myRule, Rule[] otherRules);
    
    public double getMiu(){
        return miu;
    }

    
}
