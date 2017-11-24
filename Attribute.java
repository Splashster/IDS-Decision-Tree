
public class Attribute{
  String name, value;

  public Attribute(String n, String val){
     name = n;
     value = val;
  }

  public getAttributeValue(String n){
    if(n == name){
      return value;
    }
  }
}
