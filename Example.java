/*******************************************************
The purpose of the Example class is to store all of the
attribute names and values for each example.
********************************************************/
import java.util.*;

public class Example {
  private Map<String, String> map = new LinkedHashMap<String, String>();
  private List<String> attributes = new ArrayList<String>();
  private List<String> values;

  public Example(List<String> attrs, List<String> vals) {
    attributes = attrs;
    values = vals;
    setAttributeValues(attributes, values);
  }

  //Sets all of the attribute values for a single example and
  //stores the attribute name and value (key, value) in a map for each example
  public void setAttributeValues(List<String> attrs, List<String> vals) {
    int i = 0;
    for (String attribute : attributes) {
      map.put(attribute, vals.get(i));
      i++;
    }
  }

  public String getAttributeValue(String attr) {
    String val = "";
    val = map.get(attr);
    return val;
  }
}
