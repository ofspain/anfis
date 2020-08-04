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
public class Rule {

    public static final String GAUSSIAN_MF = "GAUSSIAN_MF";

    private double inputs[];
    private final MembershipFunction[] membership_functions;
    private final Consequent consequent;

    private double weight;
    private double normalizedWeight;
    private double inference;

    public Rule(MembershipFunction[] membership_functions, Consequent consequent) {
        this.membership_functions = membership_functions;
        this.consequent = consequent;
    }

    /**
     * @return the membership_functions
     */
    public MembershipFunction[] getMembership_functions() {
        return membership_functions;
    }

    /**
     * @return the consequent
     */
    public Consequent getConsequent() {
        return consequent;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * @return the normalizedWeight
     */
    public double getNormalizedWeight() {
        return normalizedWeight;
    }

    /**
     * @param normalizedWeight the normalizedWeight to set
     */
    public void setNormalizedWeight(double normalizedWeight) {
        this.normalizedWeight = normalizedWeight;
    }

    /**
     * @return the inference
     */
    public double getInference() {
        return inference;
    }

    /**
     * @param inputs the inputs to set
     */
    public void setInputs(double[] inputs) {
        this.inputs = inputs;
    }

    public void executeLayer1() {

        for (int i = 0; i < inputs.length; i++) {
            membership_functions[i].computeMiu(inputs[i]);
        }
    }

    public void executeLayer2() {
        double norm = 1;
        for (MembershipFunction mf : this.membership_functions) {
            if(mf.getMiu() > 0 && mf.getMiu() <= 1)
                norm *= mf.getMiu();
        }
        weight = norm;
    }

    public void executeLayer3(double sum_weight) {
        normalizedWeight = weight / sum_weight;
    }

    public void executeLayer4() {

        consequent.setInput_values(inputs);
        double consequent_inference = consequent.computeInference();
        this.inference = normalizedWeight * consequent_inference;

    }

}
