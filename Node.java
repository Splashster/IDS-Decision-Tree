import java.util.*;
public class Node {

    private String name; //node value
    private List<Node> child; //child node
    private List<Node> branch; //branch node
    private Node parent; //patent node
    private List<String> items;
    private boolean isBranch;
    private boolean done;
    private String classification;

    public Node() { //constructor
        name = "";
        child = null;
        parent = null;

    }

    public Node(String name) { //constructor
        this.name = name;
        isBranch = false;
        done = false;
        classification = "";
        child = new ArrayList<>();
        branch = new ArrayList<>();
        items = new ArrayList<String>();
    }

    /*public Node(String name, Node parent) {
        name = name;
        this.parent = parent;
        child = new ArrayList<>();
    }*/
    public List<Node> getChildren() { // returns child subtree
        return child;
    }

    public List<Node> getBranches() { // returns child subtree
        return branch;
    }

    public String getNodeName(){
        return name;
    }

    public void addChild(Node c) { //add child method
        child.add(c);
    }

    public void addBranch(Node c) { //add child method
        branch.add(c);
    }

    public void removeChild() { //deletes the child note
        this.child = null;
    }

    public Node getParent() { //returns parent subtree
        return this.parent;
    }

    public String getParentName() { //returns parent name
        return this.parent.getNodeName();
    }

    public String getValue() { //returns name of that node
        return this.name;
    }

    public boolean hasParent(){
      boolean answer = false;
      if(this.parent != null){
        answer = true;
      }
      return answer;
    }

    public boolean hasChildren(){
      boolean answer = false;
      if(!this.getChildren().isEmpty()){
        answer = true;
      }
      return answer;
    }

    public boolean hasBranches(){
      boolean answer = false;
      if(!this.getBranches().isEmpty()){
        answer = true;
      }
      return answer;
    }

    public void setParent(Node parent) { //sets parent node to current node
        if(isBranch){
          parent.addBranch(this);
          parent.addChild(this);
          this.parent = parent;
        }else{
          parent.addChild(this);
          this.parent = parent;
        }
    }

    public void setBranch(){
      isBranch = true;
    }

    public Boolean isLeaf() { //checks if the node is a leaf node
        if(this.child.size() == 0) {
            return true;
        }else {
            return false;
        }
    }

    public Boolean isABranch() { //checks if the node is a leaf node
        if(isBranch) {
            return true;
        }else {
            return false;
        }
    }

    public Boolean isRoot() { //checks if the node is a root node
        return (this.parent == null);
    }

    public void resetDone(){
      done = false;
    }

    public String toString() {
        String result = name + " ";
        if (child != null) {
            result += child.toString();
        }
        return result;
    }

    public List<String> printTraversal(Node n){
      if(n.isABranch()){
        System.out.println("I am branch: " + n.getNodeName() + " My parent is: " + n.getParentName());
        items.add(n.getNodeName());
      }
      //
      if(n.hasChildren() && !n.hasBranches()){
        for(Node c : n.getChildren()){
          items.add(c.getNodeName());
          System.out.println("I am child: " + c.getNodeName() + " My parent is: " + c.getParentName());
          //for(Node t : c.getChildren()){
            //items.add(t.getNodeName());
            //System.out.println("My name is: " + t.getNodeName() + " and my parent is: " + t.getParentName());
            if(c.hasChildren() && !c.isLeaf()){
              System.out.println("I am:" + c.getNodeName() +  " and I have more children!" );
              printTraversal(c);
            }
          }
      }else if(n.hasChildren() && n.hasBranches()){
        //System.out.println("My parent is: " + n.getParentName());
        for(Node b : n.getBranches()){
          if(b.isABranch()){
            System.out.println("I am branch: " + b.getNodeName() + " My parent is: " + b.getParentName());
          }
          items.add(b.getNodeName());
          for(Node c : b.getChildren()){
            items.add(c.getNodeName());
            System.out.println("I am child: " + c.getNodeName() + " My parent is: " + c.getParentName());
            //for(Node t : c.getChildren()){
              //items.add(t.getNodeName());
              //System.out.println("My name is: " + t.getNodeName() + " and my parent is: " + t.getParentName());
              if(c.hasChildren() && !c.isLeaf()){
                System.out.println("I am:" + c.getNodeName() +  " and I have more children!" );
                printTraversal(c);
              }
            }
        }
      }else{
        items.add(n.getNodeName());
      }
      return items;
    }

    public String predictTraversal(Example ex, Node tr){
      if(!done){
        for(Node n : tr.getChildren()){
          //System.out.println("Example " + count + " Attribute " + c.getParentName() +
          //                  " Value is: " + ex.getAttributeValue(c.getParentName()) + " Node val is: " + c.getNodeName());
        //  System.out.println("Im: " + tr.getNodeName());
          if(n.isABranch()){
          //  System.out.println("Im branch: " + n.getNodeName());
            if(ex.getAttributeValue(n.getParentName()).equalsIgnoreCase(n.getNodeName())){
            //  System.out.println("\nMatches on branch: " + n.getNodeName() + " for attribute: " + n.getParentName() + "\n");
              if(n.hasChildren() && !n.hasBranches()){
                for(Node c : n.getChildren()){
                //  System.out.println("Im branch child: " + c.getNodeName());
                    if(c.hasChildren() && !c.isLeaf()){
                  //    System.out.println("Im: " + c.getNodeName());
                      predictTraversal(ex,c);
                    }else{
                    //  System.out.println("Im Classification: " + c.getNodeName());
                      classification = c.getNodeName();
                      done = true;
                      break;
                    }
                  }
              }else if(n.hasChildren() && n.hasBranches()){
              //  System.out.println("My parent is: " + n.getParentName());
                for(Node b : n.getBranches()){
                  if(done){break;}
                  if(ex.getAttributeValue(n.getParentName()).equalsIgnoreCase(n.getNodeName())){
                  for(Node c : b.getChildren()){
                    if(c.hasChildren() && !c.isLeaf()){
                      predictTraversal(ex,c);
                    }else{
                      classification = c.getNodeName();
                      done = true;
                      break;
                    }
                }
              }
              //System.out.println("\n Example " + count + " Match Found for value: " + c.getNodeName() + "\n");
            }
          }

        }
      }else if(!n.isABranch() && !n.isLeaf()){
        //System.out.println("Im a child: " + n.getNodeName());
        predictTraversal(ex,n);
      }else{
      //  System.out.println("\nI'm here\n");
        classification = n.getNodeName();
        done = true;
        break;
      }
      }
      }

      //System.out.println("Final Classification: " + classification);
      return classification;
    }


}
