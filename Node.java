import java.util.*;
public class Node {

    private String name; //node value
    private List<Node> child; //child node
    private Node parent; //patent node

    public Node() { //constructor
        name = "";
        child = null;
        parent = null;
    }

    public Node(String name ) { //constructor
        this.name = name;
        child = new ArrayList<>();
    }

    /*public Node(String name, Node parent) {
        name = name;
        this.parent = parent;
        child = new ArrayList<>();
    }*/
    public List<Node> getChildren() { // returns child subtree
        return child;
    }

    public void addChild(Node c) { //add child method
        child.add(c);
    }

    public void removeChild() { //deletes the child note
        this.child = null;
    }

    public Node getParent() { //returns parent subtree
        return this.parent;
    }

    public String getParentName() { //returns parent name
        return this.parent.getValue();
    }

    public String getValue() { //returns name of that node
        return this.name;
    }

    public void setParent(Node parent) { //sets parent node to current node
        parent.addChild(this);
        this.parent = parent;
    }

    public Boolean isLeaf() { //checks if the node is a leaf node
        if(this.child.size() == 0) {
            return true;
        }else {
            return false;
        }
    }

    public Boolean isRoot() { //checks if the node is a root node
        return (this.parent == null);
    }

    public String toString() {
        String result = name + " ";
        if (child != null) {
            result += child.toString();
        }
        return result;
    }
    public static void main(String [] args)
    {
        Node root = new Node("Patrons");
        Node child1 = new Node("Full");
        Node child2 = new Node("Some");
        child1.setParent(root);
        child2.setParent(root);
        Node child3 = new Node("child3");
        child3.setParent(root);
        Node child4 = new Node("Hungry");
        child4.setParent(child3);
        Node child6 = new Node("child3.2");
        child6.setParent(child3);
        Node child5 = new Node("child3.1.1");
        child5.setParent(child4);


        System.out.println(root);
        /*System.out.println(root.getChildren());
        System.out.println(child3.getChildren());
        System.out.println(child2.getChildren());
        System.out.println(root.getParent());
        System.out.println(child4.getParent().getValue());
        System.out.println(child4.isLeaf());
        System.out.println(child3.isLeaf());
        System.out.println(root.isRoot());
        System.out.println(child3.isRoot());
        System.out.println(child4.getValue());
        System.out.println(child3.getValue());
        System.out.println(child4.getParentName());
        System.out.println(child3.getParentName());*/
        child3.removeChild();
        System.out.println(root);
    }
}
