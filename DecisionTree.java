/*******************************************************
The purpose of the DecisionTree class is to generate 
and return the decision tree and to classify 
each test example.
********************************************************/
import java.util.*;
import java.lang.Math.*;

public class DecisionTree {
  String lastkey = "";
  String pos_value = "";
  String neg_value = "";


  public DecisionTree(String lkey, List<String> class_values) {
    lastkey = lkey;
    pos_value = class_values.get(0);
    neg_value = class_values.get(1);
  }

  //Generates the decision tree model
  public Node decisionTreeLearner(List<Example> examples, Map<String, List<String>> attributes, List<Example> parent_examples) {
    String common_output = "";
    Node tree;
    if (examples.isEmpty()) {
      common_output = plularityValue(parent_examples);
      tree = new Node(common_output);
      return tree;
    } else if (allTheSame(examples)) {
      tree = new Node(examples.get(0).getAttributeValue(lastkey));
      return tree;
    } else if (attributes.isEmpty()) {
      common_output = plularityValue(examples);
      tree = new Node(common_output);
      return tree;
    } else {
      String a = "";
      double argmax = 0.0;
      double temp = 0.0;
      int count = 0;
      List<Example> exs = new ArrayList<Example>();
      List<String> removal = new ArrayList<String>();
      Node subtree;
      Node branch;

      for (Map.Entry<String, List<String>> attribute : attributes.entrySet()) {
        if (count == attributes.size() - 1) {
          break;
        } else {
          temp = importance(attribute.getKey(), attribute.getValue(), examples);
          if (temp <= 0.0) {
            removal.add(attribute.getKey());
          } else if (temp > argmax) {
            argmax = temp;
            a = attribute.getKey();
          }
          count++;
        }
      }
      tree = new Node(a);

      if (!a.equals("")) {
        for (String value : attributes.get(a)) {
          for (Example e : examples) {
            if (e.getAttributeValue(a).equalsIgnoreCase(value)) {
              exs.add(e);
            }
          }
          if (removal.size() >= 1) {
            for (String atr : removal) {
              attributes.remove(atr);
            }
            attributes.remove(a);
            removal.clear();
          } else {
            attributes.remove(a);
          }
          subtree = decisionTreeLearner(exs, attributes, examples);
          branch = new Node(value);
          branch.setBranch();
          branch.setParent(tree);
          subtree.setParent(branch);

          exs.clear();
        }
      }

    }
    return tree;
  }

  //Predicts the classification for each example
  public List<String> predict(List<Example> examples, Node tr) {
    List<String> predictions = new ArrayList<String>();
    String classification = "";
    for (Example ex : examples) {
      classification = tr.predictTraversal(ex, tr);
      predictions.add(classification);
      tr.resetDone();
    }
    return predictions;
  }


  //Calculates the number of positive examples in the
  //train data
  public int attrPosCount(List<Example> examples) {
    int pos_count = 0;
    for (Example ex : examples) {
      if (ex.getAttributeValue(lastkey).equalsIgnoreCase(pos_value)) {
        pos_count++;
      }
    }
    return pos_count;
  }

  //Calculates the number of occurences of a particular attribute value in each
  //example
  public int[] valueOccurence(String attribute, String value, List<Example> examples) {
    int[] valueOccurences = {0, 0};

    for (Example ex : examples) {
      if (ex.getAttributeValue(attribute).equalsIgnoreCase(value)) {
        valueOccurences[0]++;
        if (ex.getAttributeValue(lastkey).equalsIgnoreCase(pos_value)) {
          valueOccurences[1]++;
        }
      }
    }

    return valueOccurences;
  }

  //Determines the most common output for the passed in examples
  public String plularityValue(List<Example> parent_examples) {
    int pos_count = 0;
    int neg_count = 0;
    String classification = neg_value;
    String[] choices = {pos_value, neg_value};
    Random rand = new Random();

    for (Example ex : parent_examples) {
      if (ex.getAttributeValue(lastkey).equalsIgnoreCase(pos_value)) {
        pos_count++;
      } else {
        neg_count++;
      }
    }

    if (pos_count > neg_count) {
      classification = pos_value;
    } else if (pos_count == neg_count) {
      classification = choices[rand.nextInt(choices.length)];
    }

    return classification;
  }

  //Checks to see if the passed in examples all have the same
  //classification
  public boolean allTheSame(List<Example> examples) {
    boolean answer = true;
    String testcase = examples.get(0).getAttributeValue(lastkey);

    for (Example ex : examples) {
      if (!ex.getAttributeValue(lastkey).equalsIgnoreCase(testcase)) {
        answer = false;
        break;
      }
    }
    return answer;
  }

  //Calculates the information gain for each attribute
  public double importance(String attribute, List<String> values , List<Example> examples) {
    double entropy = 0.0;
    double remaining_entropy = 0.0;
    double info_gain = 0.0;
    int[] valueOccurences;
    double posValOccurence = 0,  negValOccurence = 0, totalValOccurence = 0;
    int totalNumExamples = examples.size();
    int totalPosAttrCount = 0, totalNegAttrCount = 0;
    double temp = 0.0;

    totalPosAttrCount = attrPosCount(examples);
    totalNegAttrCount = totalNumExamples - totalPosAttrCount;
    entropy = (-(((double)totalPosAttrCount / totalNumExamples) * (Math.log((double)totalPosAttrCount / totalNumExamples) / (Math.log(2))))) -
              (((double)totalNegAttrCount / totalNumExamples) * (Math.log((double)totalNegAttrCount / totalNumExamples) / (Math.log(2))));

    for (String value : values) {
      valueOccurences = valueOccurence(attribute, value, examples);
      totalValOccurence = valueOccurences[0];
      posValOccurence = valueOccurences[1];
      negValOccurence = totalValOccurence - posValOccurence;

      temp = ((double)totalValOccurence / totalNumExamples) * (((-(double)posValOccurence / totalValOccurence) * (Math.log((double)posValOccurence / totalValOccurence) / (Math.log(2)))) -
             (((double)negValOccurence / totalValOccurence) * (Math.log((double)negValOccurence / totalValOccurence) / (Math.log(2)))));

      if (!Double.isNaN(temp)) {
        remaining_entropy += temp;
      }

    }

    info_gain = entropy - remaining_entropy;
    return info_gain;
  }
}
