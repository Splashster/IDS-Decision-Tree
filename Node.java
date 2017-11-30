/*******************************************************
The purpose of the Node class is to generate the structure
of the decision tree, traverse through and print the
final tree, and traverse through the tree to find the
classification for each example.
********************************************************/
import java.util.*;

public class Node {

  private String name;
  private List<Node> child;
  private List<Node> branch;
  private Node parent;
  private List<String> items;
  private boolean isBranch;
  private boolean done;
  private String classification;

  public Node() {
    name = "";
    child = null;
    parent = null;

  }

  public Node(String name) {
    this.name = name;
    isBranch = false;
    done = false;
    classification = "";
    child = new ArrayList<>();
    branch = new ArrayList<>();
    items = new ArrayList<String>();
  }

  //Returns child subtree
  public List<Node> getChildren() {
    return child;
  }

  //Returns branch subtree
  public List<Node> getBranches() {
    return branch;
  }

  //Returns name of node
  public String getNodeName() {
    return name;
  }

  //Add child to node c
  public void addChild(Node c) {
    child.add(c);
  }

  //Add branch to node c
  public void addBranch(Node c) {
    branch.add(c);
  }

  //Deletes the child node
  public void removeChild() {
    this.child = null;
  }

  //Returns parent subtree
  public Node getParent() {
    return this.parent;
  }

  //Returns parent name
  public String getParentName() {
    return this.parent.getNodeName();
  }


  //Checks to see if current node has a parent
  public boolean hasParent() {
    boolean answer = false;
    if (this.parent != null) {
      answer = true;
    }
    return answer;
  }

  //Checks to see if current node has children
  public boolean hasChildren() {
    boolean answer = false;
    if (!this.getChildren().isEmpty()) {
      answer = true;
    }
    return answer;
  }

  //Checks to see if current node has branches
  public boolean hasBranches() {
    boolean answer = false;
    if (!this.getBranches().isEmpty()) {
      answer = true;
    }
    return answer;
  }

  //Sets parent node of current node
  public void setParent(Node parent) {
    if (isBranch) {
      parent.addBranch(this);
      parent.addChild(this);
      this.parent = parent;
    } else {
      parent.addChild(this);
      this.parent = parent;
    }
  }

  //Declares current node to be a branch
  public void setBranch() {
    isBranch = true;
  }

  //Checks if the node is a leaf node
  public Boolean isLeaf() {
    if (this.child.size() == 0) {
      return true;
    } else {
      return false;
    }
  }

  //Checks if the node is a leaf node
  public Boolean isABranch() {
    if (isBranch) {
      return true;
    } else {
      return false;
    }
  }

  //Checks if the node is a root node
  public Boolean isRoot() {
    return (this.parent == null);
  }

  //Resets the done boolean
  public void resetDone() {
    done = false;
  }

  //Prints decision tree
  public void printTree(Node n, String s, int count) {
    String indent = "     ";
    int tracker = 1;
    if (n.isABranch()) {
      System.out.println(s + "Branch: "  + n.getNodeName());
    }
    if (n.hasChildren() && !n.hasBranches()) {
      count++;
      for (Node c : n.getChildren()) {
        if (c.hasChildren()) {
          s = s.join("", Collections.nCopies(count, indent));
          System.out.println(s + "Attribute: " + c.getNodeName());

          count++;
          s = s.join("", Collections.nCopies(count, indent));
          printTree(c, s, count);
        } else {
          s = s.join("", Collections.nCopies(count, indent));
          System.out.println(s + "Classification: " + c.getNodeName());
          s = s.join("", Collections.nCopies(count, indent));
        }
      }
    } else if (n.hasChildren() && n.hasBranches()) {
      for (Node b : n.getBranches()) {
        if (b.isABranch()) {
          s = s.join("", Collections.nCopies(count, indent));
          System.out.println(s + "Branch: "  + b.getNodeName());
        }
        for (Node c : b.getChildren()) {
          if (c.hasChildren()) {
            tracker++;
            count++;
            s = s.join("", Collections.nCopies(count, indent));
            System.out.println(s + "Attribute: " + c.getNodeName());
            count++;
            s = s.join("", Collections.nCopies(count, indent));
            printTree(c, s, count);
          } else {
            count++;
            tracker++;
            s = s.join("", Collections.nCopies(count, indent));
            System.out.println(s + "Classification: " + c.getNodeName());
            tracker--;
          }
        }
        count -= tracker;
        tracker = 1;
      }
    }
  }


  //Traverses through the tree to get the classification for an example
  public String predictTraversal(Example ex, Node tr) {
    if (!done) {
      for (Node n : tr.getChildren()) {
        if (n.isABranch()) {
          if (ex.getAttributeValue(n.getParentName()).equalsIgnoreCase(n.getNodeName())) {
            if (n.hasChildren() && !n.hasBranches()) {
              for (Node c : n.getChildren()) {
                if (c.hasChildren()) {
                  predictTraversal(ex, c);
                } else {
                  classification = c.getNodeName();
                  done = true;
                  break;
                }
              }
            } else if (n.hasChildren() && n.hasBranches()) {
              for (Node b : n.getBranches()) {
                if (done) {break;}
                if (ex.getAttributeValue(n.getParentName()).equalsIgnoreCase(n.getNodeName())) {
                  for (Node c : b.getChildren()) {
                    if (c.hasChildren()) {
                      predictTraversal(ex, c);
                    } else {
                      classification = c.getNodeName();
                      done = true;
                      break;
                    }
                  }
                }
              }
            }

          }
        } else if (!n.isABranch() && !n.isLeaf()) {
          predictTraversal(ex, n);
        } else {
          classification = n.getNodeName();
          done = true;
          break;
        }
      }
    }

    return classification;
  }


}
