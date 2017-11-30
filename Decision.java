/*******************************************************************************
The purpose of the Decision class is to extract the data from the
input files, trigger the decision tree learner so that the decision tree
can be created, trigger the decision tree prediction function,
and get the accuracy of the prediction.
********************************************************************************/
import java.util.*;

public class Decision {

  private String lastkey = "";
  private String s = "     ";
  private boolean removed = false;
  private int count = 2;
  private List<String> attr_description;
  private List<String> train_data;
  private List<String> test_data;
  private List<String> parts;
  private List<String> attr_names = new ArrayList<String>();
  private List<Example> train_examples = new ArrayList<Example>();
  private List<Example> test_examples = new ArrayList<Example>();
  private List<String> train_answers = new ArrayList<String>();
  private List<String> test_answers = new ArrayList<String>();
  private List<String> train_predictions = new ArrayList<String>();
  private List<String> test_predictions = new ArrayList<String>();
  private Map<String, List<String>> attributes = new LinkedHashMap<String, List<String>>();
  private Metrics metrics = new Metrics();

  public Decision(List<String> attr_desc, List<String> tr_data, List<String> ts_data) {
    attr_description = attr_desc;
    train_data = tr_data;
    test_data = ts_data;
  }

  public void run() {
    try {

      //Extracts the attribute names and valid values from
      //the attrribute description file
      for (String attr : attr_description) {
        parts = Arrays.asList(attr.split(" ", 2));
        attr_names.add(parts.get(0));
        attributes.put(parts.get(0), new ArrayList<String>(Arrays.asList(parts.get(1).split("\\s|,"))));
      }

      //Gets the name of the classification attribute
      lastkey = attr_names.get(attr_names.size() - 1);

      //Extracts each example and stores their information in seperate
      //example objects
      for (String data : train_data) {
        parts = new ArrayList<String>(Arrays.asList(data.split("\\s|,")));
        train_answers.add(parts.get(parts.size() - 1));
        train_examples.add(new Example(attr_names, parts));

      }
      //Removes the classification attribute
      //from the attribute name list
      attr_names.remove(attr_names.size() - 1);



      DecisionTree dt = new DecisionTree(lastkey, attributes.get(lastkey));
      attributes.remove(lastkey);

      //Triggers the decision tree learner which produces the decision tree model
      Node tree = dt.decisionTreeLearner(train_examples, attributes, null);


      //Prints out generated decision tree
      System.out.println("Generated Tree");
      System.out.println("-------------------------------------------------------------------");
      System.out.println("Root: " + tree.getNodeName());
      for (Node t : tree.getChildren()) {
        t.printTree(t, s, count);
      }

      //Triggers the predicition function on the train examples
      //and the metrics function for the predictions
      train_predictions = dt.predict(train_examples, tree);
      metrics.performanceMetrics("\nTrain Data", train_answers, train_predictions, dt.pos_value, dt.neg_value);

      //Checks to see if any test data was provided
      if (!test_data.isEmpty()) {

        //Extracts each example and stores their information in seperate
        //example objects
        for (String data : test_data) {
          parts = new ArrayList<String>(Arrays.asList(data.split(" ")));
          test_answers.add(parts.get(parts.size() - 1));
          parts.remove(parts.size() - 1);
          test_examples.add(new Example(attr_names, parts));
        }

        //Triggers the predicition function on the test examples
        //and the metrics function for the predictions
        test_predictions = dt.predict(test_examples, tree);
        metrics.performanceMetrics("Test Data", test_answers, test_predictions, dt.pos_value, dt.neg_value);
      }

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
