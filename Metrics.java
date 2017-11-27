import java.util.*;

public class Metrics{

  public double getAccuracy(List<String> answers, List<String> predictions){
    int count = 0;
    int correct = 0;
    int incorrect = 0;
    double accuracy = 0.0;

    System.out.println();
    for(String answer : answers){
      if(answer.equalsIgnoreCase(predictions.get(count))){
        //System.out.println("Example: " + ex.getAttributeValue(lastkey) + " Prediction " + predictions.get(count));
          correct++;
      }else{
        incorrect++;
      }
      count++;
    }

    System.out.println("Correct: " + correct);
    System.out.println("Incorrect: " + incorrect);
    accuracy = (double)correct/answers.size();
    return accuracy;
  }

}
