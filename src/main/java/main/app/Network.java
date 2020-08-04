/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.app;

import functional.part.neurofuzzy.Consequent;
import functional.part.neurofuzzy.GaussianFunction;
import functional.part.neurofuzzy.MembershipFunction;
import functional.part.neurofuzzy.Pattern;
import functional.part.neurofuzzy.Rule;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author femi
 */
public class Network {

    private final Pattern[] patterns;
    private final Rule[] rules;

    private final double[] past_error;
    private final double[][] error_data;

    public Network(Pattern[] patterns, Rule[] rules) {
        this.patterns = patterns;

        this.rules = rules;

        past_error = new double[2];
        error_data = new double[patterns.length - 2][3];
    }

    private double sum_of_weight() {
        double sum_weight = 0;

        for (Rule rule : this.rules) {
            double rule_weight = rule.getWeight();
            sum_weight += rule_weight;
        }
        return sum_weight;
    }

    public double predict(double inputs[]) {

        for (Rule rule : this.rules) {
            rule.setInputs(inputs);
            rule.executeLayer1();
//            System.out.println(".................");
            rule.executeLayer2();
        }

        double sum_weight = sum_of_weight();

        for (Rule rule : this.rules) {
            rule.executeLayer3(sum_weight);
            rule.executeLayer4();
        }
        double output = 0;

        for (Rule rule : this.rules) {
            double ruleInference = rule.getInference();

            output += ruleInference;
        }

        return output;

    }

    public void trainNetwork() {

        for (int i = 1; i <= 10000000; i++) {
            double sum_error_square = 0.0;
            for (int u = 0; u < patterns.length; u++) {

                double target = patterns[u].getDesired_output();
                double[] inputs = patterns[u].getInput_values();

                double prediction = predict(inputs);
                double error = target - prediction;
                double error_abs = Math.pow(error, 2);
                sum_error_square += error_abs;

                double consequent_eta = consequentEta();// 10;
                double[] antecedent_eta = antecedentEta();
                Map<String, Double> etas = new HashMap<>();
                etas.put(GaussianFunction.STD_DEVIATION, antecedent_eta[1]);
                etas.put(GaussianFunction.MEAN, antecedent_eta[0]);
                if (error_abs > 1000000) {
                    System.out.println("SUM OF WEIGHT " + consequentEta() + " normalized ");
                }
                for (Rule rule : rules) {
                    rule.getConsequent().correctError(error, consequent_eta, rule.getNormalizedWeight());
                    for (int h = 0; h < rule.getMembership_functions().length; h++) {
                        rule.getMembership_functions()[h].correctError(error, etas);
                    }
                }

            }
            double root_mean_error_square = Math.sqrt(sum_error_square / patterns.length);

            System.out.printf("ERROR IN " + i + " iteration is %.14f\n", root_mean_error_square);

        }
        /*
        for (int u = 0; u < patterns.length; u++) {
            double target = patterns[u].getDesired_output();
            double[] inputs = patterns[u].getInput_values();

            double prediction = predict(inputs);
            double error = target - prediction;
            if (u <= 1) {
                past_error[u] = error;
            } else {
                double new_error_data[] = new double[3];
                new_error_data[0] = past_error[0];
                new_error_data[1] = past_error[1];
                new_error_data[2] = error;
                // System.out.println(u-2 +" "+error_data.length);
                error_data[u - 2] = new_error_data;
                //update past error 
                
                //swap past error
                past_error[0] = past_error[1];
                //make currrent error the most recent
                past_error[1] = error;

                
            }
        }

        for (double[] er : this.error_data) {
            System.out.println(String.format("%.6f", er[0]) + "\t" +String.format("%.6f", er[1]) + "\t" + String.format("%.6f", er[2]));
        }
         */
        for (Rule rule : this.rules) {
            Consequent con = rule.getConsequent();
            System.out.println("Consequent " + con);
            for (MembershipFunction mf : rule.getMembership_functions()) {
                GaussianFunction gf = (GaussianFunction) mf;
                System.out.println(gf);
            }
            System.out.println(".............RULED..........");
        }
        // printNetWork();
    }

    private double maxConsequentChange() {
        double max = Math.abs(rules[0].getConsequent().
                determineMaxDchange(rules[0].getNormalizedWeight()));

        for (Rule rule : rules) {
            double rule_norm = rule.getNormalizedWeight();
            double rule_max = Math.abs(rule.getConsequent().determineMaxDchange(rule_norm));
            if (max < rule_max) {
                max = rule_max;
            }
        }
        //    System.out.println("consequent "+max);
        return max;
    }

    private Rule[] otherRule(int index_exclude) {
        Rule[] otherRules = new Rule[rules.length - 1];
        int u = 0;
        for (int i = 0; i < rules.length; i++) {
            if (i != index_exclude) {
                otherRules[u++] = rules[i];
            }
        }
        return otherRules;
    }

    private double[] maxAntecedent() {

        double sum_weight = sum_of_weight();
        //determineChange_dPrediction(double input,double sum_weight,Rule myRule, Rule[] otherRules)
        for (int i = 0; i < rules.length; i++) {
            Rule otherRules[] = otherRule(i);
            Rule thisRule = rules[i];
            Consequent consequent = thisRule.getConsequent();
            for (int j = 0; j < thisRule.getMembership_functions().length; j++) {
                double input = consequent.getInput_values()[j];
                rules[i].getMembership_functions()[j].determineChange_dPrediction(input, sum_weight, thisRule, otherRules);

                //    System.out.println("My changes" + rules[i].getMembership_functions()[j].getParameter_change_d_prediction());
            }

        }

        Rule rule_first = this.rules[0];
        Map<String, Double> first_changes = rule_first.getMembership_functions()[0].getParameter_change_d_prediction();
        double max_std = Math.abs(first_changes.get(GaussianFunction.STD_DEVIATION));
        double max_mean = Math.abs(first_changes.get(GaussianFunction.MEAN));
        for (Rule rule : rules) {
            MembershipFunction mfs[] = rule.getMembership_functions();
            for (MembershipFunction mf : mfs) {
                Map<String, Double> changes = mf.getParameter_change_d_prediction();
                double change_std = Math.abs(changes.get(GaussianFunction.STD_DEVIATION));
                double change_mean = Math.abs(first_changes.get(GaussianFunction.MEAN));

                if (change_std > max_std) {
                    max_std = change_std;
                }
                if (change_mean > max_mean) {
                    max_mean = change_mean;
                }

            }
        }

        double[] maxs = new double[2];
        maxs[0] = max_mean;
        maxs[1] = max_std;
        return maxs;
    }

    private double consequentEta() {
        double max = maxConsequentChange();
        if (2 / (max * max) > 1) {
            return (max * max)/2;
        }
        return 2 / (max * max);
    }

    private double[] antecedentEta() {
        double[] max = maxAntecedent();
        //      System.out.printf("MAXS %f:%f\n",max[0],max[1]);
        double eta_mean;
        double eta_std;

        if (max[0] > 2) {
            eta_mean = 2 / (max[0] * max[0]);
        } else {
            eta_mean = (max[0] * max[0]) / 2;
        }

        if (max[1] > 2) {
            eta_std = 2 / (max[1] * max[1]);
        } else {
            eta_std = (max[1] * max[1]) / 2;
        }

        double[] etas = new double[2];
        etas[0] = eta_mean;
        etas[1] = eta_std;

        return etas;
    }

    public void test(Pattern[] patterns) {
        double sum_error_square = 0.0;
        double mean_absolute_error = 0.0;
        for (Pattern pattern : patterns) {
            double prediction = this.predict(pattern.getInput_values());
            double error = prediction - pattern.getDesired_output();
            sum_error_square += (error * error);
            mean_absolute_error += (Math.abs(error));
            System.out.println("Desired " + pattern.getDesired_output() + " | predicted " + prediction + " | error " + error);
        }

        double root_mean_error_square = Math.sqrt(sum_error_square / patterns.length);
        mean_absolute_error /= patterns.length;
        System.out.printf("RMSE IN  training is %.5f\n MAE IN  training is %.5f\n", root_mean_error_square, mean_absolute_error);
    }

    private void printNetWork() {
        for (Rule rule : this.rules) {
            System.out.println(rule.getConsequent().getBias());
            for (MembershipFunction mf : rule.getMembership_functions()) {
                GaussianFunction gf = (GaussianFunction) mf;
                System.out.println(gf.getMean() + " " + gf.getStandard_deviation());
            }
        }
    }
}
