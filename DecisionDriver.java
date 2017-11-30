/*******************************************************
Main Driver for the Decision Tree Program.
********************************************************/
import java.util.*;
import java.nio.file.*;

public class DecisionDriver {
  public static void main(String[] args) {

    String path = "Data/";
    String attr_desc_filename = "";
    String train_filename = "";
    String test_filename = "";
    List<String> attr_description = new ArrayList<String>();
    List<String> train_data = new ArrayList<String>();
    List<String> test_data = new ArrayList<String>();

    try {
      if (args.length < 1) {
        System.out.println("Missing Arguments: Must enter Attribute Description file location and Train Data file location. Test Data file location is optional.");
      } else if (args.length > 3) {
        System.out.println("Maximum number of arguments allowed is 3!");
      } else {
        attr_desc_filename = args[0];
        train_filename = args[1];

        attr_description = Files.readAllLines(Paths.get(path, attr_desc_filename)); //Extract Restaurant Attributes from File
        train_data = Files.readAllLines(Paths.get(path, train_filename)); //Extract Restaurant Train Data from File

        if (args.length == 3) {
          test_filename = args[2];
          test_data = Files.readAllLines(Paths.get(path, test_filename)); //Extract Restaurant Train Data from File
        }

        Decision decision = new Decision(attr_description, train_data, test_data);
        decision.run();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
