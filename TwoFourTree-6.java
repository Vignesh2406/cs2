public class TwoFourTree {
  public class TwoFourTreeItem {
    public TwoFourTreeItem() {
      // Default constructor
    }

    int values = 1;
    int value1 = 0; // always exists.
    int value2 = 0; // exists iff the node is a 3-node or 4-node.
    int value3 = 0; // exists iff the node is a 4-node.
    boolean isLeaf = true;

    TwoFourTreeItem parent = null; // parent exists iff the node is not root.
    TwoFourTreeItem leftChild = null; // left and right child exist iff the note is a non-leaf.
    TwoFourTreeItem rightChild = null;
    TwoFourTreeItem centerChild = null; // center child exists iff the node is a non-leaf 3-node.
    TwoFourTreeItem centerLeftChild = null; // center-left and center-right children exist iff the node is a non-leaf
                                            // 4-node.
    TwoFourTreeItem centerRightChild = null;

    public boolean isTwoNode() {
      return values == 1;
    }

    public boolean isThreeNode() {
      return values == 2;
    }

    public boolean isFourNode() {
      return values == 3;
    }

    public boolean isRoot() {
      return parent == null;
    }

    public TwoFourTreeItem(int value1) {
      this.value1 = value1;
    }

    public TwoFourTreeItem(int value1, int value2) {
      this.value1 = value1;
      this.value2 = value2;
      values = 2;
    }

    public TwoFourTreeItem(int value1, int value2, int value3) {
      this.value1 = value1;
      this.value2 = value2;
      this.value3 = value3;
      values = 3;
    }

    private void printIndents(int indent) {
      for (int i = 0; i < indent; i++)
        System.out.printf("  ");
    }

    public void printInOrder(int indent) {
      if (!isLeaf)
        leftChild.printInOrder(indent + 1);
      printIndents(indent);
      System.out.printf("%d\n", value1);
      if (isThreeNode()) {
        if (!isLeaf)
          centerChild.printInOrder(indent + 1);
        printIndents(indent);
        System.out.printf("%d\n", value2);
      } else if (isFourNode()) {
        if (!isLeaf)
          centerLeftChild.printInOrder(indent + 1);
        printIndents(indent);
        System.out.printf("%d\n", value2);
        if (!isLeaf)
          centerRightChild.printInOrder(indent + 1);
        printIndents(indent);
        System.out.printf("%d\n", value3);
      }
      if (!isLeaf)
        rightChild.printInOrder(indent + 1);
    }

  }

  TwoFourTreeItem root = null;

  // ADD Value
  //TODO
  public void addValue(int value) {
    if (root == null) {
      root = new TwoFourTreeItem(value);
    } else {
      insertValue(null, root, value);
    }
  }

  private void insertValue(TwoFourTreeItem parent, TwoFourTreeItem node, int value) {
    if(node.isFourNode()){ //Completely deals with four nodes
      split(parent, node, value); //Also insert value
    }else if (node.isLeaf) {
      if (node.isTwoNode()) {
        if (value < node.value1) {
          node.value2 = node.value1;
          node.value1 = value;
        } else {
          node.value2 = value;
        }
        node.values = 2;
      } else if (node.isThreeNode()) {
        if (value < node.value1) {
          node.value3 = node.value2;
          node.value2 = node.value1;
          node.value1 = value;
        } else if (value > node.value2) {
          node.value3 = value;
        } else {
          node.value3 = node.value2;
          node.value2 = value;
        }
        node.values = 3;
      }
    } else {
      if (node.isTwoNode()) {
        if (value < node.value1) {
          insertValue(node, node.leftChild, value);
        } else {
          insertValue(node, node.rightChild, value);
        }
      } else if (node.isThreeNode()) {
        if (value < node.value1) {
          insertValue(node, node.leftChild, value);
        } else if (value > node.value2) {
          insertValue(node, node.rightChild, value);
        } else {
          insertValue(node, node.centerChild, value);
        }
      }
    }
  }

  /**
   * @param node
   * @param intValue
   *
   * Split iff this is a 4 node
   *
   */
  private void split(TwoFourTreeItem parent, TwoFourTreeItem node, int intValue) {
    int middleVal = node.value2;
    node.value2 = 0;

    TwoFourTreeItem newLeft = new TwoFourTreeItem(node.value1);
    newLeft.leftChild = node.leftChild;
    newLeft.rightChild = node.centerLeftChild;
    newLeft.parent = node.parent;
    newLeft.isLeaf = node.isLeaf;
    TwoFourTreeItem newRight = new TwoFourTreeItem(node.value3);
    newRight.leftChild = node.centerRightChild;
    newRight.rightChild = node.rightChild;
    newRight.parent = node.parent;
    newRight.isLeaf = node.isLeaf;

    TwoFourTreeItem ascend;

    if (parent == null){ //This node is root, so add another level
      TwoFourTreeItem root = new TwoFourTreeItem(middleVal);
      root.isLeaf = false;
      this.root = root;
      root.leftChild = newLeft;
      newLeft.parent = root;
      root.rightChild = newRight;
      newRight.parent = root;
      ascend = root;
    }else{ //Shift around values of parent to accommodate new value
      if(parent.isTwoNode()){
        if(middleVal < parent.value1){
          parent.value2 = parent.value1;
          parent.value1 = middleVal;

          parent.centerChild = newRight;
          parent.leftChild = newLeft;
        }else{
          parent.value2 = middleVal;
          parent.centerChild = newLeft;
          parent.rightChild = newRight;
        }
        parent.values = 2; //Make into 3 node
      }else if(node.parent.isThreeNode()){
        if(middleVal < parent.value1){
          parent.value3 = parent.value2;
          parent.value2 = parent.value1;
          parent.value1 = middleVal;

          parent.centerRightChild = parent.centerChild;
          parent.centerLeftChild = newRight;
          parent.leftChild = newLeft;
        }else if(middleVal > parent.value2){
          parent.value3 = middleVal;

          parent.centerLeftChild = parent.centerChild;
          parent.centerRightChild = newLeft;
          parent.rightChild = newRight;
        }else{
          parent.value3 = parent.value2;
          parent.value2 = middleVal;

          parent.centerLeftChild = newLeft;
          parent.centerRightChild = newRight;
        }
        parent.values = 3; //Make into 4 node
      }
      ascend = newLeft.parent;
      newLeft.parent = parent;
      newLeft.isLeaf = node.isLeaf;
      newRight.parent = parent;
      newRight.isLeaf = node.isLeaf;
    }

    if(ascend.isFourNode()){
      if (intValue < parent.value1) {
        insertValue(parent, parent.leftChild, intValue);
      } else if (intValue > parent.value3) {
        insertValue(parent, parent.rightChild, intValue);
      } else if (intValue > parent.value1 && intValue < parent.value2) {
        insertValue(parent, parent.centerChild, intValue);
      } else {
        insertValue(parent, parent.centerChild, intValue);
      }
    }else{
      insertValue(ascend.parent, ascend, intValue); //Move up to parent to insert value
    }
  }

  public void create(int[] values) {
    for (int value : values) {
      addValue(value);
    }
  }
  public void deleteValue(int value) {
    if (root == null) {
      System.out.println("Tree is empty. Cannot delete value.");
      return;
    }
    delete(root, value);
  }

  private TwoFourTreeItem delete(TwoFourTreeItem node, int value) {
    if (node == null) {
      System.out.println("Value not found in the tree.");
      return null;
    }

    if (node.isLeaf) {
      if (node.isTwoNode() && node.value1 == value) {
        //TODO
        return null; // Value found and deleted
      } else if (node.isThreeNode()) {
        if (node.value1 == value) {
          node.value1 = node.value2;
          node.value2 = 0;
          node.values = 1;
        } else if (node.value2 == value) {
          node.value2 = 0;
          node.values = 1;
        } else {
          System.out.println("Value not found in the tree.");
        }
      } else if (node.isFourNode()) {
        if (node.value1 == value) {
          node.value1 = node.value2;
          node.value2 = node.value3;
          node.value3 = 0;
          node.values = 2;
        } else if (node.value2 == value) {
          node.value2 = node.value3;
          node.value3 = 0;
          node.values = 2;
        } else if (node.value3 == value) {
          node.value3 = 0;
          node.values = 2;
        } else {
          System.out.println("Value not found in the tree.");
        }
      }
      return node;
    } else {
      if (node.isTwoNode()) {
        if (value < node.value1) {
          node.leftChild = delete(node.leftChild, value);
        } else {
          node.rightChild = delete(node.rightChild, value);
        }
      } else if (node.isThreeNode()) {
        if(node.value1 == value || node.value2 == value || node.value3 == value) {
          TwoFourTreeItem searchNode = node.rightChild;
          while (searchNode.leftChild != null) {
            searchNode = searchNode.leftChild;
          }
          if(node.value1 == value){
            node.value1 = searchNode.value1;
            //delete(node.rightChild, searchNode.value1);
          }else if(node.value2 == value){
            node.value2 = searchNode.value2;
            //delete(node.rightChild, searchNode.value2);
          }else{
            node.value3 = searchNode.value3;
            //delete(node.rightChild, searchNode.value3);
          }
        }else if (value < node.value1) {
          node.leftChild = delete(node.leftChild, value);
        } else if (value > node.value2) {
          node.rightChild = delete(node.rightChild, value);
        } else {
          node.centerChild = delete(node.centerChild, value);
        }
      } else if (node.isFourNode()) {
        if(node.value1 == value || node.value2 == value || node.value3 == value) {
          TwoFourTreeItem searchNode = node.rightChild;
          while (searchNode.leftChild != null) {
            searchNode = searchNode.leftChild;
          }
          if(node.value1 == value){
            node.value1 = searchNode.value1;
            //delete(node.rightChild, searchNode.value1);
          }else if(node.value2 == value){
            node.value2 = searchNode.value2;
            //delete(node.rightChild, searchNode.value2);
          }else{
            node.value3 = searchNode.value3;
            //delete(node.rightChild, searchNode.value3);
          }
        }else if (value < node.value1) {
          node.leftChild = delete(node.leftChild, value);
        } else if (value > node.value3) {
          node.rightChild = delete(node.rightChild, value);
        } else if (value > node.value1 && value < node.value2) {
          node.centerLeftChild = delete(node.centerLeftChild, value);
        } else if (value > node.value2 && value < node.value3) {
          node.centerRightChild = delete(node.centerRightChild, value);
        }
      }
    }
    return node;
  }

  public boolean hasValue(int intValue) {
    return hasValue(root, intValue);
  }

  private boolean hasValue(TwoFourTreeItem node, int intValue) {
    if(node.value1 == intValue || node.value2 == intValue || node.value3 == intValue){
      return true;
    }
    if(node.isLeaf) {
      return false;
    }else if (node.isTwoNode()) {
        if (intValue < node.value1) {
          return hasValue(node.leftChild, intValue);
        } else {
          return hasValue(node.rightChild, intValue);
        }
    } else if (node.isThreeNode()) {
        if (intValue < node.value1) {
          return hasValue(node.leftChild, intValue);
        } else if (intValue > node.value2) {
          return hasValue(node.rightChild, intValue);
        } else {
          return hasValue(node.centerChild, intValue);
        }
    } else if (node.isFourNode()) {
        if (intValue < node.value1) {
          return hasValue(node.leftChild, intValue);
        } else if (intValue > node.value3) {
          return hasValue(node.rightChild, intValue);
        } else if (intValue > node.value1 && intValue < node.value2) {
          return hasValue(node.centerLeftChild, intValue);
        } else {
          return hasValue(node.centerRightChild, intValue);
        }
    }
      return false;
  }

  public void printInOrder(){
    if(root != null) root.printInOrder(0);
  }

  public TwoFourTree() {

  }

  // testing
  public static void main(String[] args) throws Exception {
    TwoFourTree tft = new TwoFourTree();

    // Add values to the tree
    tft.addValue(9);
    tft.addValue(7);
    tft.addValue(2);
    tft.addValue(3);
    tft.addValue(1);

    if(tft.hasValue(2) && tft.hasValue(7) && tft.hasValue(9)){
      System.out.println("True");
    }else{
      System.out.println("False");
    }

    // Print the tree in order
    System.out.println("Original Tree:");
    tft.root.printInOrder(0);

    // Create a new tree using create method
    int[] values = { 8, 6, 0 };
    tft.create(values);

    // Print the new tree in order
    System.out.println("New Tree:");
    tft.root.printInOrder(0);

    // Delete values from the tree
    tft.deleteValue(1);
    tft.deleteValue(8);

    // Print the tree after deletion
    System.out.println("Tree after deletion:");
    tft.root.printInOrder(0);
  }
}
