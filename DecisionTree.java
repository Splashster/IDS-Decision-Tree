import java.util.*;

public class DecisionTree{
  String lastkey = "";

  public Node decisionTreeLearner(List<Example> examples, Map<String, String[]> attributes, List<Example> parent_examples){
      for(String key : attributes.keySet()){
          lastkey = key;
      }

      /*if(examples.isEmpty()){
        return plularityValue(parent_examples);
      }*/
      //else if(allTheSame(examples)){return examples.get(0).getAttributeValue(lastkey);}
      //else if(attributes.isEmpty()){return plularityValue(examples);}
      //else{
        String a;
        int argmax = 0;
        int temp = 0;

      /* for(List attribute : attributes){
          for(String val : attribute){
            temp = importance(attribute, examples)
            if(temp > argmax){
              a = attribute;
            }
          }
        }*/

        Node tree = new Node(lastkey,null);
        System.out.println(tree.isRoot());
        System.out.println(tree.getValue());

        return tree;
      }


 public int posCount(List<Example> examples){
   int pos_count = 0;

     for (Example ex: examples){
         if(ex.getAttributeValue(lastkey).equalsIgnoreCase("Yes")){
           pos_count++;
         }
     }
     return pos_count;
 }

 public double valueOccurence(String attribute, String value, List<Example> examples){
    double valueOccurences = 0.0;
    double posCount = 0.0, total = 0.0;

    for(Example ex : examples){
      if(ex.getAttributeValue(attribute).equalsIgnoreCase(value)){
          posCount++;
        if(ex.getAttributeValue(lastkey).equalsIgnoreCase("Yes")){
          total++;
        }
      }
    }
    valueOccurences = (posCount/total);

    return valueOccurences;
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
      classification = choices[rand.nextInt(choices.length)];
    }

    return classification;
  }

  public boolean allTheSame(List<Example> examples){
    boolean answer = true;
    String testcase = examples.get(0).getAttributeValue(lastkey);

    for (Example ex: examples){
      if(!ex.getAttributeValue(lastkey).equalsIgnoreCase(testcase)){
        answer = false;
        break;
      }
    }
    return answer;
  }
  
  public double entropy (double p, double q) {
      return -((q*((Math.log(q))/(Math.log(2))))+(p*((Math.log(p))/(Math.log(2)))));
  }

  public double importance(Attribute attributes, List<Example> examples){
      
      double pos = posCount(examples);
      double total = examples.size();
      double neg = total-pos;
      
      double p = (pos/total);
      double q = (neg/total);
      double entropy = entropy(p,q);
      
      /*for(Map.Entry<String, String[]> entry : attributes.entrySet()){ 
          for (String item : entry.getValue()){ 
              System.out.println(entry.getKey());
              System.out.println(item);
          }  
      }*/
      //valueOccurence()
      double info_gain = 0.0;
      
      /*for (Example ex: examples){
          if(ex.getAttritbuteValue(attribute) ){
            pos_count++;
          }else{
            neg_count++;
          }
      }*/
      return info_gain;
  }
}
