import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
public class TreeExample {
JFrame f;
TreeExample(Node tree){
    f=new JFrame();

    JTree jt=new JTree(tree);
    f.add(jt);
    f.setSize(200,200);
    f.setVisible(true);
}  
