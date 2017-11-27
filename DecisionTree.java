import java.util.*;
import java.lang.Math.*;

public class DecisionTree{
  String lastkey = "";

  public DecisionTree(String lkey){
     lastkey = lkey;
  }


  public Node decisionTreeLearner(List<Example> examples, Map<String, String[]> attributes, List<Example> parent_examples){
      Node tree;
      String common_output = "";

      if(examples.isEmpty()){
        common_output = plularityValue(parent_examples);
        tree = new Node(common_output);
        return tree;
      }else if(allTheSame(examples)){
        tree = new Node(examples.get(0).getAttributeValue(lastkey));
        return tree;
      }else if(attributes.isEmpty()){
        common_output = plularityValue(examples);
        tree = new Node(common_output);
        return tree;
      }else{
        String a = "";
        double argmax = 0.0;
        double temp = 0.0;
        int count = 0;
        List<Example> exs = new ArrayList<Example>();
        List<String> removal = new ArrayList<String>();
        Node subtree;
        Node branch;

        for(Map.Entry<String, String[]> attribute : attributes.entrySet()){
            if(count == attributes.size()-1){
              break;
            }else{
              //System.out.println("Importance coming Key: " + attribute.getKey());
              temp = importance(attribute.getKey(), attribute.getValue(), examples);
            //
             if(temp <= 0.0){
               removal.add(attribute.getKey());
             }else if(temp > argmax){
                argmax = temp;
                a = attribute.getKey();
              }
              count++;
            }
            //for(String value : attribute.getValue()){
              //  System.out.println("Attribute: " + a + "Value: " + value);
            //}

        }

        //System.out.println("Best Attribute: " + a);
      //  try{
          tree = new Node(a);
      //  }catch(Exception e){}

        if(!a.equals("")){
          for(String value: attributes.get(a)){
          //  System.out.println("Inside Attribute: " + a + " Value: " + value + " Current example size: " + examples.size());
             for(Example e : examples){
               if(e.getAttributeValue(a).equalsIgnoreCase(value)){
            //     System.out.println("Added example for Attribute: " + a + " Value: " + value);
                 exs.add(e);
               }
             }
            // System.out.println("New example size: " + exs.size());
             //System.out.println("Attributes size: " + attributes.size());
            // System.out.println("Attributes left " + attributes.keySet());
             if(removal.size() >= 1) {
               for(String atr: removal){
                 //System.out.println("Removed : " + atr);
                 attributes.remove(atr);

               }
               //System.out.println("Removed : " + a);
               attributes.remove(a);
               removal.clear();
             }else{
               attributes.remove(a);
               //System.out.println("Removed : " + a);
             }

             //System.out.println("Current attributes list" + attributes.keySet());
             subtree = decisionTreeLearner(exs, attributes, examples);
             branch = new Node(value);
             branch.setBranch();
             branch.setParent(tree);
             subtree.setParent(branch);
             //tree.addChild(branch);
             //branch.addChild(subtree);
            // System.out.println("Tree branches are: " + tree.getChildren());
            // System.out.println("Tree children for branch : " + value + " is: " + branch.getChildren());

             exs.clear();
             //System.out.println("Current Tree: " + tree);
          }
        }

    }
    return tree;
  }


 public int attrPosCount(List<Example> examples){
   int pos_count = 0;
     for (Example ex: examples){
         if(ex.getAttributeValue(lastkey).equalsIgnoreCase("Yes")){
           pos_count++;
         }
     }
     return pos_count;
 }

 public int[] valueOccurence(String attribute, String value, List<Example> examples){
    int[] valueOccurences = {0,0};

    for(Example ex : examples){
      if(ex.getAttributeValue(attribute).equalsIgnoreCase(value)){
          valueOccurences[0]++;
        if(ex.getAttributeValue(lastkey).equalsIgnoreCase("Yes")){
          valueOccurences[1]++;
        }
      }
    }

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

  public double importance(String attribute, String[] values ,List<Example> examples){
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
      entropy = (-(((double)totalPosAttrCount/totalNumExamples)*(Math.log((double)totalPosAttrCount/totalNumExamples)/(Math.log(2))))) -
                (((double)totalNegAttrCount/totalNumExamples)*(Math.log((double)totalNegAttrCount/totalNumExamples)/(Math.log(2))));

      for(String value : values){
        valueOccurences = valueOccurence(attribute, value, examples);
        totalValOccurence = valueOccurences[0];
        posValOccurence = valueOccurences[1];
        negValOccurence = totalValOccurence - posValOccurence;

        temp = ((double)totalValOccurence/totalNumExamples)*(((-(double)posValOccurence/totalValOccurence)*(Math.log((double)posValOccurence/totalValOccurence)/(Math.log(2)))) -
               (((double)negValOccurence/totalValOccurence)*(Math.log((double)negValOccurence/totalValOccurence)/(Math.log(2)))));
               //System.out.println("Attribute: " + attribute +  " Attribute pos occurence: " + totalValOccurence + " Value: " + value +  " Number pos occurence: " + posValOccurence +  " Number neg occurence: " + negValOccurence);
        if(!Double.isNaN(temp)){
          remaining_entropy += temp;

          //System.out.println("Part 1: " + (double)totalValOccurence/totalNumExamples);
          //System.out.println("Part 2: " + ((-(double)posValOccurence/totalValOccurence)*(Math.log((double)posValOccurence/totalValOccurence)/(Math.log(2)))));
          //System.out.println("Part 3: " + (((double)negValOccurence/totalValOccurence))*(Math.log((double)negValOccurence/totalValOccurence)/(Math.log(2))));
          //System.out.println("Part 4 re: " + temp);
          //System.out.println("Part 5 new re: " + remaining_entropy);
        }

      }

      info_gain = entropy - remaining_entropy;

      //System.out.println("Etropy is: " + entropy + " Info gained from attribute: " + attribute + " is: " + info_gain);

      return info_gain;
  }
}
