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
      for(String key : attributes.keySet()){
          lastkey = key;
      }
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
      DecisionTree dt = new DecisionTree(lastkey);
      attributes.remove(lastkey);
    //  System.out.println(dt.decisionTreeLearner(examples, attributes, null));
      Node tree = dt.decisionTreeLearner(examples, attributes, null);
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
            names.add(t.traversal(t));
        }

        System.out.println(names);
      //}
    }catch(Exception r){
      System.out.println(r);
    }

  }
}
