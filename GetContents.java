import java.util.*;
import java.nio.file.*;

public class GetContents{
  public static void main(String[] args){

    String path = "Data/";
    String attr_desc_filename = "";
    String train_filename = "";
    String test_filename = "";
    String[] parts;
    String lastkey = "";
    int count = 1;
    List<String> attr_names = new ArrayList<String>();
    List<Example> train_examples = new ArrayList<Example>();
    List<Example> test_examples = new ArrayList<Example>();
    List<String> train_predictions = new ArrayList<String>();
    List<String> test_predictions = new ArrayList<String>();
    Map<String, String[]> attributes = new LinkedHashMap<String, String []>();
    Metrics metrics = new Metrics();

    if(args.length < 2){
      System.out.println("Missing Arguments: Must enter Attribute Description file location, Train Data file location, and Test Data file location.");
    }else{

          try{
            attr_desc_filename = args[0];
            train_filename = args[1];
            test_filename = args[2];
            List<String> attr_description = Files.readAllLines(Paths.get(path, attr_desc_filename)); //Extract Restaurant Attributes from File
            List<String> train_data = Files.readAllLines(Paths.get(path, train_filename)); //Extract Restaurant Train Data from File
            List<String> test_data = Files.readAllLines(Paths.get(path, test_filename)); //Extract Restaurant Train Data from File

            for(String attr : attr_description){
              parts = attr.split(" ", 2);
              attr_names.add(parts[0]);
              attributes.put(parts[0], parts[1].split(" "));
            }

            for(String data : train_data){
              parts = data.split(" ");
              //Example example = ;
              train_examples.add(new Example(attr_names, parts));

            }

            for(String data : test_data){
              parts = data.split(" ");
              //Example example = ;
              test_examples.add(new Example(attr_names, parts));
            }

            for(String key : attributes.keySet()){
                lastkey = key;
            }
            System.out.println(lastkey);
            //System.out.println(attributes.keySet().iterator().next());
            /*
            for(String key : attributes.keySet()){
                lastkey = key;
            }
            for(Map.Entry<String, String[]> entry : attributes.entrySet()){
                for (String item : entry.getValue()){
                  System.out.println(entry.getKey());
                  System.out.println(item);
                }
            }*/
          //  System.out.println(entry.getKey());
          //  System.out.println(entry.getValue());
            //System.out.println(lkey);
            //System.out.println(examples.get(1).getAttributeValue(lastkey));
            /*for (Map.Entry<String, String[]> entry : attributes.entrySet())
            {
                for(String item : entry.getValue()){
                    System.out.println("key: " + entry.getKey() + "; value: " + item);
                }

            }*/
            DecisionTree dt = new DecisionTree(lastkey, attributes.get(lastkey));
            attributes.remove(lastkey);
          //  System.out.println(dt.decisionTreeLearner(examples, attributes, null));
            Node tree = dt.decisionTreeLearner(train_examples, attributes, null);
            System.out.println(tree);
          //  System.out.println(tree.getChildren());

            //for(String t : tree.toString()){
              //System.out.println(tree.getChildren().get(1));
              //System.out.println(tree.getNodeName());

              /*if(!c.isLeaf()){
                for(Node b : c.getChildren()){
                  names.add
                }
              }*/


              List<List<String>> names = new ArrayList<List<String>>();
              //names.add(tree.getNodeName());
              for(Node t : tree.getChildren()){
                  //names.add(t.getNodeName());
                //  System.out.println("Node: " + t.getNodeName() + " Has Parent: " + t.hasParent());
                //  System.out.println("Node: " + t.getNodeName() + " Has Children: " + t.hasChildren());
                //  System.out.println("Is Node: " + t.getNodeName() + " Root? " + t.isRoot() );
                //  System.out.println("My parent is: " + t.getParentName());
                  //for(Node c : t.getChildren())(){
                  //  for(Node c : t.getChildren()){
                  //    names.add(c.getNodeName());
                  //  }
                  //}
                  names.add(t.printTraversal(t));
              }

              System.out.println(names);
              train_predictions = dt.predict(train_examples, tree);
              count = 1;
              for(String train_prediction : train_predictions){
                //System.out.println("Example: " + count + " Classification: " + prediction);
                count++;
              }
              System.out.println("Train Accuracy: " + metrics.getAccuracy(train_examples, train_predictions, lastkey));

              test_predictions = dt.predict(test_examples, tree);
              count = 1;
              for(String test_prediction : test_predictions){
                //System.out.println("Example: " + count + " Classification: " + test_prediction);
                count++;
              }

              System.out.println(" TestAccuracy: " + metrics.getAccuracy(test_examples, test_predictions, lastkey));

            //}
          }catch(Exception r){
            System.out.println(r);
          }

        }
    }

}
