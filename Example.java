import java.util.*;

public class Example{
  Map<String, String> map = new LinkedHashMap<String, String>();
  List<String> attributes = new ArrayList<String>();
  List<String> values;

  public Example(List<String> attrs, List<String> vals){
    attributes = attrs;
    values = vals;
    setAttributeValues(attributes, values);
  }

  public void setAttributeValues(List<String> attrs, List<String> vals){
    int i = 0;
    for(String attribute : attributes){
        //System.out.println("Attribute: " + attribute + " Value: " + vals.get(i));
        map.put(attribute, vals.get(i));
        i++;
    }
  }

  public String getAttributeValue(String attr){
    String val = "";
    val = map.get(attr);
    return val;
  }
}
