import java.util.*;
import java.nio.file.*;

public class GetContents{
  public static void main(String[] args){

    String path = "Data/";
    String[] parts;
    String lastkey = "";
    List<String> attr_names = new ArrayList<String>();
    List<Example> examples = new ArrayList<Example>();
    Map<String, String[]> attributes = new LinkedHashMap<String, String []>();

    try{
      List<String> rest_attr = Files.readAllLines(Paths.get(path, "restaurant-attr.txt")); //Extract Restaurant Attributes from File
      List<String> rest_data = Files.readAllLines(Paths.get(path, "restaurant-train.txt")); //Extract Restaurant Train Data from File

      for(String attr : rest_attr){
        parts = attr.split(" ", 2);
        attr_names.add(parts[0]);
        attributes.put(parts[0], parts[1].split(" "));
      }
      for(String data : rest_data){
        parts = data.split(" ");
        //Example example = ;
        examples.add(new Example(attr_names, parts));
      }

      //System.out.println(attributes.keySet().iterator().next());
      /*
      for(String key : attributes.keySet()){
          lastkey = key;
      }*/
      /*for(Map.Entry<String, String[]> entry : attributes.entrySet()){
        System.out.println(entry.getKey());
        System.out.println(entry.getValue()[1]);
      }*/
      //System.out.println(lkey);
      //System.out.println(examples.get(1).getAttributeValue(lastkey));
      /*for (Map.Entry<String, String[]> entry : attributes.entrySet())
      {
          for(String item : entry.getValue()){
              System.out.println("key: " + entry.getKey() + "; value: " + item);
          }

      }*/
      DecisionTree dt = new DecisionTree();
      dt.decisionTreeLearner(examples, attributes, null);
    }catch(Exception r){
      System.out.println(r);
    }

  }
}
