package weka;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.pmml.jaxbbindings.Value;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Spliterator;

public class Main {

    public static void main(String[] args) throws Exception {
        Random random = new Random(1);

        Instances data = getFullDataSet();

        Instance instanceToClassify = data.remove(data.numInstances() - 1);
        instanceToClassify.setClassMissing();

        System.out.println(classifyWithNaiveBayes(data, instanceToClassify));
        System.out.println(getNaiveBayesCorrectPercentage(getFullDataSet(), random));

        System.out.println(classifyWithRules(data, instanceToClassify));
        System.out.println(getNumOfRules(getFullDataSet()));

        System.out.println(classifyWithDecisionTree(data, instanceToClassify));
        for (Instance anIncorrectlyClassifiedInstance : getDecisionTreeIncorrectlyClassifiedInstances(getFullDataSet(),
                random))
            System.out.println(anIncorrectlyClassifiedInstance);

        System.out.println(classifyWithCaseBasedReasoning(data, instanceToClassify));
        System.out.println(getCBRNumSingleValueCorrectlyClassified(getFullDataSet(), "Walnuss", random));
    }

    private static Instances getFullDataSet() throws Exception {
        InputStream arffFile = Main.class.getResourceAsStream("baeume.arff");
        DataSource dataSource = new DataSource(arffFile);
        Instances data = dataSource.getDataSet();
        setClassIndex(data, "Baumgattung");
        return data;
    }

    public static void setClassIndex(Instances data, String attributeName) {
        Attribute attr = data.attribute(attributeName);
        data.setClass(attr);

    }

    public static String classifyWithNaiveBayes(Instances data, Instance instanceToClassify) throws Exception {
        String result = null;
        int counterSame = 0;
        for (Instance in : data) {
            int counter = 0;
            for (int i = 1; i < instanceToClassify.numAttributes(); i++) {
                if (instanceToClassify.attribute(i).isNumeric()) {
                    double v1 = in.value(i);
                    double v2 = instanceToClassify.value(i);
                    if (v1 == v2) {
                        counter++;
                    }
                } else {
                    if (in.stringValue(i).equals(instanceToClassify.stringValue(i))) {
                        counter++;
                    }
                }
            }
            if (counter > counterSame) {
                counterSame = counter;
                result = in.stringValue(0);
            }
        }
        return result;
    }

    public static double getNaiveBayesCorrectPercentage(Instances data, Random random) throws Exception {
        double counter=0;
        int runs = 10;
        for (int i = 0; i < runs; i++) {
            Instances rndData = new Instances(data);
            rndData.randomize(random);

            for (int n = 0; n < 10; n++) {
                //Instances train = rndData.trainCV(10, n, random);
                Instance targetInstance = rndData.get(n);
                Instances test = rndData.testCV(10, n);
                test.remove(rndData.get(n));
                String s = classifyWithNaiveBayes(test, rndData.get(n));
                if (targetInstance.stringValue(0).equals(s)) {
                    counter++;
                }
            }

            }
        return counter / (data.numInstances()/10.0) / data.numAttributes();










      /*
        int numStart = (int) Math.ceil(33.0 / 10);
        int numEnd = numStart;
        while (numStart != 0) {
            for (int i = numEnd - numStart; i < (numEnd - numStart) + numEnd; i++) {
                Instances dataNew = new Instances(data);
                dataNew.remove(data.get(i));
                String s = classifyWithNaiveBayes(dataNew, data.get(i));
                if (targetInstance.stringValue(0).equals(s)) {
                    counter++;
                }
            }
            result += counter;
            numStart -= numEnd;
            if (i)
        }
        return (result / 3) / data.numAttributes();*/
    }

    public static String classifyWithRules(Instances data, Instance instanceToClassify) throws Exception {
        return null;
    }

    public static int getNumOfRules(Instances data) throws Exception {
        return 0;
    }

    public static String classifyWithDecisionTree(Instances data, Instance instanceToClassify) throws Exception {
        return null;
    }

    public static List<Instance> getDecisionTreeIncorrectlyClassifiedInstances(Instances data, Random random)
            throws Exception {
        return Collections.emptyList();
    }

    public static String classifyWithCaseBasedReasoning(Instances data, Instance instanceToClassify) throws Exception {
        return null;
    }

    public static int getCBRNumSingleValueCorrectlyClassified(Instances data, String valueToCheck, Random random)
            throws Exception {
        return 0;
    }

}
