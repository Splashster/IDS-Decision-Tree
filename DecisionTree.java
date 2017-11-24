import java.util.Arraylist;
import java.util.Random;

public class DecisionTree{
  String lastkey = "";

  public TreeNode decisionTreeLearner(List<Example> examples, Map<String, String[]> attributes, List<Example> parent_examples){
      for(String key : attributes.keySet()){
          lastkey = key;
      }

      if(examples.isEmpty()){return plularityValue(parent_examples);}
      else if(allTheSame(examples)){return examples.get(0).getAttributeValue(lastkey);}
      else if(attributes.isEmpty()){return plularityValue(examples);}
      else{
        String a;
        int argmax = 0;
        int temp = 0;

        for(List attribute : attributes){
          for(String val : attribute){
            temp = importance(attribute, examples)
            if(temp > argmax){
              a = attribute;
            }
          }
        }
      }
  }


  public String plularityValue(List<Example> parent_examples){
    int pos_count = 0;
    int neg_count = 0;
    String classification = "No";
    String[] choices = {"Yes", "No"};
    Random rand = new Random();

    for (Example ex: parent_examples){
        if(ex.getAttributeValue(lastkey).equalsIgnoreCase("Yes")){
          pos_count++;
        }else{
          neg_count++;
        }
    }

    if(pos_count > neg_count){
      classification = "Yes";
    }else if(pos_count == neg_count){
      classification = choices[ran.nextInt(choices.length)];
    }

    return classification;
  }

  public boolean allTheSame(List<Example> examples){
    boolean answer = true;
    String testcase = examples.get(0).getAttributeValue(lastkey);

    for (Example ex: examples){
      if(!ex.getAttributeValue(lastkey).equalsIgnoreCase(testcase)){
        boolean = false;
        break;
      }
    }
    return answer;
  }

/*  public int importance(Attribute attribute, List<Example> examples{
      int remaining_entropy = 0;
      int info_gain = 0;
      int num_examples = examples.size();

      for (Example ex: examples){
          if(ex.getAttritbuteValue(attribute) ){
            pos_count++;
          }else{
            neg_count++;
          }
      }
  }*/
}
