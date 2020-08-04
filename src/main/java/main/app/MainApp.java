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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author femi
 */
public class MainApp {

    private List<Double[]> readFromFile(String file_name, int input_number) {
        java.util.Scanner scanner;
        List<Double[]> result = new ArrayList<>();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(file_name);
            scanner = new Scanner(is);
            int lined = 0;
            while (scanner.hasNextLine()) {
                //  System.out.println(++lined);
                String line = scanner.nextLine();
                Double[] values = new Double[input_number + 1];
                for (int i = 0; i < input_number; i++) {
                    String yinput = line.split(" ")[i];
                    values[i] = new Double(yinput);

                }
                String youtput = line.split(" ")[input_number];
                values[input_number] = new Double(youtput);
                result.add(values);

            }
        } catch (SecurityException securityException) {
            System.err.println("Write permission denied or file do no exsit.  Terminating...");
            System.exit(1); // terminate the program
        }
        return result;
    }

    private Pattern[] formPatterns(List<Double[]> values) {
        Pattern patterns[] = new Pattern[values.size()];
        for (int j = 0; j < values.size(); j++) {
            Double[] value = values.get(j);
            Pattern pattern = new Pattern();
            //last value is the desired value
            double output_value = value[value.length - 1];
            pattern.setDesired_output(output_value);
            double[] inputs = new double[value.length - 1];
            for (int i = 0; i < value.length - 1; i++) {
                inputs[i] = value[i];
            }
            pattern.setInput_values(inputs);
            patterns[j] = pattern;
        }
        return patterns;
    }

    private MembershipFunction formGaussianFunction(double mean, double std) {
        GaussianFunction guass_mf = new GaussianFunction(mean, std);
        return guass_mf;
    }

    public static void main(String[] args) {

      // tryDailyTemp();
       tryChaotic();
    }

    private static void tryDailyTemp() {
        MainApp mainApp = new MainApp();

        double std = 0.1334;

        //RULE 1
        MembershipFunction y1_c1 = mainApp.formGaussianFunction(0.3434, std);
        MembershipFunction y2_c1 = mainApp.formGaussianFunction(0.3132, std);
        MembershipFunction y3_c1 = mainApp.formGaussianFunction(0.3057, std);
        MembershipFunction y4_c1 = mainApp.formGaussianFunction(0.2906, std);
        MembershipFunction y5_c1 = mainApp.formGaussianFunction(0.3509, std);
        MembershipFunction mf_1[] = new MembershipFunction[5];
        mf_1[0] = y1_c1;
        mf_1[1] = y2_c1;
        mf_1[2] = y3_c1;
        mf_1[3] = y4_c1;
        mf_1[4] = y5_c1;

        double[] consequent_paramters = new double[5];
        consequent_paramters[0] = 0.3382;
        consequent_paramters[1] = -0.2608;
        consequent_paramters[2] = -0.01642;
        consequent_paramters[3] = 0.006721;
        consequent_paramters[4] = 0.2069;

        Consequent con_1 = new Consequent(consequent_paramters, 0.224);

        Rule rule_1 = new Rule(mf_1, con_1);

        //RULE 2
        MembershipFunction y1_c2 = mainApp.formGaussianFunction(0.434, std);
        MembershipFunction y2_c2 = mainApp.formGaussianFunction(0.5019, std);
        MembershipFunction y3_c2 = mainApp.formGaussianFunction(0.5472, std);
        MembershipFunction y4_c2 = mainApp.formGaussianFunction(0.5472, std);
        MembershipFunction y5_c2 = mainApp.formGaussianFunction(0.566, std);

        MembershipFunction mf_2[] = new MembershipFunction[5];
        mf_2[0] = y1_c2;
        mf_2[1] = y2_c2;
        mf_2[2] = y3_c2;
        mf_2[3] = y4_c2;
        mf_2[4] = y5_c2;

        double[] consequent_paramter = new double[5];
        consequent_paramter[0] = 0.6437;
        consequent_paramter[1] = 0.008672;
        consequent_paramter[2] = -0.1381;
        consequent_paramter[3] = -0.03504;
        consequent_paramter[4] = -0.03737;

        Consequent con_2 = new Consequent(consequent_paramter, 0.3132);

        Rule rule_2 = new Rule(mf_2, con_2);

        //RULE 3
        MembershipFunction y1_c3 = mainApp.formGaussianFunction(0.5396, std);
        MembershipFunction y2_c3 = mainApp.formGaussianFunction(0.4981, std);
        MembershipFunction y3_c3 = mainApp.formGaussianFunction(0.4755, std);
        MembershipFunction y4_c3 = mainApp.formGaussianFunction(0.3849, std);
        MembershipFunction y5_c3 = mainApp.formGaussianFunction(0.3736, std);
        MembershipFunction mf_3[] = new MembershipFunction[5];
        mf_3[0] = y1_c3;
        mf_3[1] = y2_c3;
        mf_3[2] = y3_c3;
        mf_3[3] = y4_c3;
        mf_3[4] = y5_c3;

        double[] consequents_paramter = new double[5];
        consequents_paramter[0] = 0.5557;
        consequents_paramter[1] = -0.18;
        consequents_paramter[2] = 0.03819;
        consequents_paramter[3] = -0.09817;
        consequents_paramter[4] = 0.08364;

        Consequent con_3 = new Consequent(consequents_paramter, 0.1875);

        Rule rule_3 = new Rule(mf_3, con_3);

        Rule[] rules = new Rule[3];
        rules[0] = rule_1;
        rules[1] = rule_2;
        rules[2] = rule_3;

        Pattern[] patterns = mainApp.formPatterns(mainApp.readFromFile("normalized_training_5.txt", 5));

        // mainApp.writeFile(patterns);
        Network anfis = new Network(patterns, rules);
        anfis.trainNetwork();
       // Pattern[] test_patterns = mainApp.formPatterns(mainApp.readFromFile("normalized_training_5.txt",5));
         //System.out.println("...........TESTING..........");
         //anfis.test(test_patterns);
    }

    private static void tryChaotic() {

        MainApp mainApp = new MainApp();

        double std = 0.157;

        //RULE 1
        MembershipFunction y1_c1 = mainApp.formGaussianFunction(0.9491, std);
        MembershipFunction y2_c1 = mainApp.formGaussianFunction(1.065, std);
        MembershipFunction y3_c1 = mainApp.formGaussianFunction(1.135, std);
        MembershipFunction y4_c1 = mainApp.formGaussianFunction(1.137, std);
        MembershipFunction mf_1[] = new MembershipFunction[4];
        mf_1[0] = y1_c1;
        mf_1[1] = y2_c1;
        mf_1[2] = y3_c1;
        mf_1[3] = y4_c1;

        double[] consequent_paramters = new double[4];
        consequent_paramters[0] = 0.2491;
        consequent_paramters[1] = -1.383;
        consequent_paramters[2] = 0.4418;
        consequent_paramters[3] = 0.2728;

        Consequent con_1 = new Consequent(consequent_paramters, 1.506);

        Rule rule_1 = new Rule(mf_1, con_1);

        //RULE 2
        MembershipFunction y1_c2 = mainApp.formGaussianFunction(1.136, std);
        MembershipFunction y2_c2 = mainApp.formGaussianFunction(0.6607, std);
        MembershipFunction y3_c2 = mainApp.formGaussianFunction(1.065, std);
        MembershipFunction y4_c2 = mainApp.formGaussianFunction(0.8115, std);

        MembershipFunction mf_2[] = new MembershipFunction[4];
        mf_2[0] = y1_c2;
        mf_2[1] = y2_c2;
        mf_2[2] = y3_c2;
        mf_2[3] = y4_c2;

        double[] consequent_paramter = new double[4];
        consequent_paramter[0] = -0.06253;
        consequent_paramter[1] = -0.8344;
        consequent_paramter[2] = 0.01587;
        consequent_paramter[3] = 0.2893;

        Consequent con_2 = new Consequent(consequent_paramter, 1.465);

        Rule rule_2 = new Rule(mf_2, con_2);

        //RULE 3
        MembershipFunction y1_c3 = mainApp.formGaussianFunction(0.6607, std);
        MembershipFunction y2_c3 = mainApp.formGaussianFunction(0.7511, std);
        MembershipFunction y3_c3 = mainApp.formGaussianFunction(0.9983, std);
        MembershipFunction y4_c3 = mainApp.formGaussianFunction(1.162, std);
        MembershipFunction mf_3[] = new MembershipFunction[4];
        mf_3[0] = y1_c3;
        mf_3[1] = y2_c3;
        mf_3[2] = y3_c3;
        mf_3[3] = y4_c3;

        double[] consequents_paramter = new double[4];
        consequents_paramter[0] = 0.04565;
        consequents_paramter[1] = 0.1746;
        consequents_paramter[2] = -0.5651;
        consequents_paramter[3] = 1.725;

        Consequent con_3 = new Consequent(consequents_paramter, -0.3692);
        Rule rule_3 = new Rule(mf_3, con_3);

        //RULE 4
        MembershipFunction y1_c4 = mainApp.formGaussianFunction(0.9234, std);
        MembershipFunction y2_c4 = mainApp.formGaussianFunction(0.732, std);
        MembershipFunction y3_c4 = mainApp.formGaussianFunction(0.6033, std);
        MembershipFunction y4_c4 = mainApp.formGaussianFunction(0.7759, std);
        MembershipFunction mf_4[] = new MembershipFunction[4];
        mf_4[0] = y1_c4;
        mf_4[1] = y2_c4;
        mf_4[2] = y3_c4;
        mf_4[3] = y4_c4;

        double[] consequent_paramter4 = new double[4];
        consequent_paramter4[0] = -0.2567;
        consequent_paramter4[1] = 0.4388;
        consequent_paramter4[2] = 0.2159;
        consequent_paramter4[3] = 0.4307;

        Consequent con_4 = new Consequent(consequent_paramter4, 0.5094);

        Rule rule_4 = new Rule(mf_4, con_4);

        //RULE 5
        MembershipFunction y1_c5 = mainApp.formGaussianFunction(1.162, std);
        MembershipFunction y2_c5 = mainApp.formGaussianFunction(0.9696, std);
        MembershipFunction y3_c5 = mainApp.formGaussianFunction(0.7566, std);
        MembershipFunction y4_c5 = mainApp.formGaussianFunction(0.618, std);

        MembershipFunction mf_5[] = new MembershipFunction[4];
        mf_5[0] = y1_c5;
        mf_5[1] = y2_c5;
        mf_5[2] = y3_c5;
        mf_5[3] = y4_c5;

        double[] consequent_paramter5 = new double[4];
        consequent_paramter5[0] = -1.123;
        consequent_paramter5[1] = -0.5676;
        consequent_paramter5[2] = 1.448;
        consequent_paramter5[3] = 0.6076;

        Consequent con_5 = new Consequent(consequent_paramter5, 1.911);

        Rule rule_5 = new Rule(mf_5, con_5);

        //RULE 6
        MembershipFunction y1_c6 = mainApp.formGaussianFunction(0.6425, std);
        MembershipFunction y2_c6 = mainApp.formGaussianFunction(0.4787, std);
        MembershipFunction y3_c6 = mainApp.formGaussianFunction(0.7369, std);
        MembershipFunction y4_c6 = mainApp.formGaussianFunction(1.013, std);
        MembershipFunction mf_6[] = new MembershipFunction[4];
        mf_6[0] = y1_c6;
        mf_6[1] = y2_c6;
        mf_6[2] = y3_c6;
        mf_6[3] = y4_c6;

        double[] consequents_paramter6 = new double[4];
        consequents_paramter6[0] = 0.4855;
        consequents_paramter6[1] = 0.4445;
        consequents_paramter6[2] = -0.1297;
        consequents_paramter6[3] = 0.4371;

        Consequent con_6 = new Consequent(consequents_paramter6, -0.05268);

        Rule rule_6 = new Rule(mf_6, con_6);

        //RULE 7
        MembershipFunction y1_c7 = mainApp.formGaussianFunction(1.093, std);
        MembershipFunction y2_c7 = mainApp.formGaussianFunction(1.155, std);
        MembershipFunction y3_c7 = mainApp.formGaussianFunction(1.248, std);
        MembershipFunction y4_c7 = mainApp.formGaussianFunction(1.072, std);
        MembershipFunction mf_7[] = new MembershipFunction[4];
        mf_7[0] = y1_c7;
        mf_7[1] = y2_c7;
        mf_7[2] = y3_c7;
        mf_7[3] = y4_c7;

        double[] consequent_paramter7 = new double[4];
        consequent_paramter7[0] = -2.553;
        consequent_paramter7[1] = -0.5079;
        consequent_paramter7[2] = 1.31;
        consequent_paramter7[3] = -0.9441;

        Consequent con_7 = new Consequent(consequent_paramter7, 3.468);

        Rule rule_7 = new Rule(mf_7, con_7);

        //RULE 8
        MembershipFunction y1_c8 = mainApp.formGaussianFunction(0.442, std);
        MembershipFunction y2_c8 = mainApp.formGaussianFunction(0.8023, std);
        MembershipFunction y3_c8 = mainApp.formGaussianFunction(0.9815, std);
        MembershipFunction y4_c8 = mainApp.formGaussianFunction(0.9447, std);

        MembershipFunction mf_8[] = new MembershipFunction[4];
        mf_8[0] = y1_c8;
        mf_8[1] = y2_c8;
        mf_8[2] = y3_c8;
        mf_8[3] = y4_c8;

        double[] consequent_paramter8 = new double[4];
        consequent_paramter8[0] = -1.848;
        consequent_paramter8[1] = 1.207;
        consequent_paramter8[2] = -1.981;
        consequent_paramter8[3] = 3.49;

        Consequent con_8 = new Consequent(consequent_paramter8, -0.3675);

        Rule rule_8 = new Rule(mf_8, con_8);

        //RULE 9
        MembershipFunction y1_c9 = mainApp.formGaussianFunction(1.271, std);
        MembershipFunction y2_c9 = mainApp.formGaussianFunction(1.188, std);
        MembershipFunction y3_c9 = mainApp.formGaussianFunction(0.8618, std);
        MembershipFunction y4_c9 = mainApp.formGaussianFunction(1.5915, std);
        MembershipFunction mf_9[] = new MembershipFunction[4];
        mf_9[0] = y1_c9;
        mf_9[1] = y2_c9;
        mf_9[2] = y3_c9;
        mf_9[3] = y4_c9;

        double[] consequents_paramter9 = new double[4];
        consequents_paramter9[0] = -0.6616;
        consequents_paramter9[1] = -0.1778;
        consequents_paramter9[2] = 0.8223;
        consequents_paramter9[3] = -0.9962;

        Consequent con_9 = new Consequent(consequents_paramter9, -0.3692);
        Rule rule_9 = new Rule(mf_9, con_9);

        //RULE 4
        MembershipFunction y1_c0 = mainApp.formGaussianFunction(1.078, std);
        MembershipFunction y2_c0 = mainApp.formGaussianFunction(0.7659, std);
        MembershipFunction y3_c0 = mainApp.formGaussianFunction(0.5184, std);
        MembershipFunction y4_c0 = mainApp.formGaussianFunction(0.4974, std);
        MembershipFunction mf_0[] = new MembershipFunction[4];
        mf_0[0] = y1_c0;
        mf_0[1] = y2_c0;
        mf_0[2] = y3_c0;
        mf_0[3] = y4_c0;

        double[] consequent_paramter0 = new double[4];
        consequent_paramter0[0] = -0.8813;
        consequent_paramter0[1] = -0.5617;
        consequent_paramter0[2] = 0.9688;
        consequent_paramter0[3] = 0.07286;

        Consequent con_0 = new Consequent(consequent_paramter0, 1.728);

        Rule rule_0 = new Rule(mf_0, con_0);

        Rule[] rules = new Rule[10];
        rules[0] = rule_1;
        rules[1] = rule_2;
        rules[2] = rule_3;
        rules[3] = rule_4;
        rules[4] = rule_5;
        rules[5] = rule_6;
        rules[6] = rule_7;
        rules[7] = rule_8;
        rules[8] = rule_9;
        rules[9] = rule_0;

        Pattern[] patterns = mainApp.formPatterns(mainApp.readFromFile("chaotic.txt", 4));
        
        Pattern[] test_patterns = mainApp.formPatterns(mainApp.readFromFile("chaotic_test.txt", 4));
        Network anfis = new Network(patterns, rules);

        anfis.test(test_patterns);
        System.out.println("........>>>>");
        anfis.test(patterns);
        System.out.println("...........TESTED..........");

        anfis.trainNetwork();

        System.out.println("......;.....TESTING..........");
        anfis.test(test_patterns);
        System.out.println("........>>>>");
        //anfis.test(patterns);
    }

    private double normalize(double value) {
        return value / 26.5;
    }

    private void writeFile(Pattern[] pat) {
        Formatter output;
        try {
            output = new Formatter("normalized_data_5.txt"); // open the file

            for (Pattern p : pat) {
                double y_t = normalize(p.getDesired_output());
                double[] ys = p.getInput_values();
                double y_t1 = normalize(ys[0]);
                double y_t2 = normalize(ys[1]);
                double y_t3 = normalize(ys[2]);
                double y_t4 = normalize(ys[3]);
                double y_t5 = normalize(ys[4]);

                output.format("%.6f %.6f %.6f %.6f %.6f %.6f\n", y_t1, y_t2, y_t3, y_t4, y_t5, y_t);

            }
            if (output != null) {
                output.close();

            }
        } catch (SecurityException securityException) {
            System.err.println("Write permission denied. Terminating.");
            System.exit(1); // terminate the program
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error opening file. Terminating.");
            System.exit(1); // terminate the program
        }
    }
    
    
    
     private static void tryDailyTempError() {
        MainApp mainApp = new MainApp();

        double std = 0.1228;

        //RULE 1
        MembershipFunction y1_c1 = mainApp.formGaussianFunction(0.08758, std);
        MembershipFunction y2_c1 = mainApp.formGaussianFunction(0.1861, std);
        MembershipFunction mf_1[] = new MembershipFunction[2];
        mf_1[0] = y1_c1;
        mf_1[1] = y2_c1;

        double[] consequent_paramters = new double[2];
        consequent_paramters[0] = 0.1861;
        consequent_paramters[1] = -0.2317;

        Consequent con_1 = new Consequent(consequent_paramters, 0.03734);

        Rule rule_1 = new Rule(mf_1, con_1);

        //RULE 2
        MembershipFunction y1_c2 = mainApp.formGaussianFunction(0.2889, std);
        MembershipFunction y2_c2 = mainApp.formGaussianFunction(0.2669, std);

        MembershipFunction mf_2[] = new MembershipFunction[2];
        mf_2[0] = y1_c2;
        mf_2[1] = y2_c2;

        double[] consequent_paramter = new double[2];
        consequent_paramter[0] = 0.25;
        consequent_paramter[1] = 0.2341;

        Consequent con_2 = new Consequent(consequent_paramter, 0.1481);

        Rule rule_2 = new Rule(mf_2, con_2);

        
        Rule[] rules = new Rule[2];
        rules[0] = rule_1;
        rules[1] = rule_2;

        Pattern[] patterns = mainApp.formPatterns(mainApp.readFromFile("error_normalized_training_5.txt", 5));

        // mainApp.writeFile(patterns);
        Network anfis = new Network(patterns, rules);
        anfis.trainNetwork();
       // Pattern[] test_patterns = mainApp.formPatterns(mainApp.readFromFile("normalized_training_5.txt",5));
         //System.out.println("...........TESTING..........");
         //anfis.test(test_patterns);
    }
}
