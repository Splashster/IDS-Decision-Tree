/*******************************************************
The purpose of the Metrics class is to perform metrics on
the prediction results.
********************************************************/
import java.util.*;

public class Metrics {

  //Calculates and prints the following metrics
  //total number of correctly and incorrectly labeled instances,
  //tp, tn, fp, fn, accuracy, precision, recall, and f1_score
  public void performanceMetrics(String dataType, List<String> answers, List<String> predictions, String pos, String neg) {
    int count = 0;
    int true_pos = 0;
    int true_neg = 0;
    int false_pos = 0;
    int false_neg = 0;

    double accuracy = 0.0;
    double precision = 0.0;
    double recall = 0.0;
    double f1_score = 0.0;

    System.out.println();
    for (String answer : answers) {
      if (answer.equalsIgnoreCase(predictions.get(count))) {
        if (answer.equalsIgnoreCase(pos)) {
          true_pos++;
        } else if (answer.equalsIgnoreCase(neg)) {
          true_neg++;
        }
      } else {
        if (answer.equalsIgnoreCase(pos)) {
          false_pos++;
        } else if (answer.equalsIgnoreCase(neg)) {
          false_neg++;
        }
      }
      count++;
    }

    accuracy = ((true_pos + true_neg) / answers.size()) * 100;
    precision = (true_pos / (true_pos + false_pos)) * 100;
    recall = (true_pos / (true_pos + false_neg)) * 100;
    f1_score = ((2 * recall * precision) / (recall + precision));

    System.out.println(dataType + " Results");
    System.out.println("--------------------------------------");
    System.out.println("Total Instances: " + answers.size());
    System.out.println("Total Correctly Labeled Instances: " + (true_pos + true_neg));
    System.out.println("Total Incorrectly Labeled Instances: " + (false_pos + false_neg));
    System.out.println("TP: " + true_pos);
    System.out.println("TN: " + true_neg);
    System.out.println("FP: " + false_pos);
    System.out.println("FN: " + false_neg);
    System.out.println("accuracy = " + accuracy + "%");
    System.out.println("precision = " + precision + "%");
    System.out.println("recall = " + recall + "%");
    System.out.println("f1_score = " + f1_score + "%");
  }

}
