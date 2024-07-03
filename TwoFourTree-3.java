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
  public void addValue(int value) {
    if (root == null) {
      root = new TwoFourTreeItem(value);
    } else {
      insertValue(null, root, value);
    }
  }

  private void insertValue(TwoFourTreeItem parent, TwoFourTreeItem node, int value) {
    if (node.isLeaf) {
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
      } else if (node.isFourNode()) {
        TwoFourTreeItem newLeft = new TwoFourTreeItem(node.value1);
        TwoFourTreeItem newRight = new TwoFourTreeItem(node.value3);
        TwoFourTreeItem newCenter = new TwoFourTreeItem(node.value2);

        // Set the correct leaf flag for the new nodes
        newLeft.isLeaf = node.isLeaf;
        newRight.isLeaf = node.isLeaf;
        newCenter.isLeaf = node.isLeaf;

        if (value < node.value1) {
          newLeft.value2 = newLeft.value1;
          newLeft.value1 = value;
          newLeft.isLeaf = true;
        } else if (value > node.value3) {
          newRight.value2 = value;
          newRight.isLeaf = true;
        } else if (value > node.value1 && value < node.value2) {
          newCenter.value2 = newCenter.value1;
          newCenter.value1 = value;
          newCenter.isLeaf = true;
        } else {
          newCenter.value2 = value;
          newCenter.isLeaf = true;
        }

        TwoFourTreeItem newRootNode = new TwoFourTreeItem(node.value2);
        newRootNode.leftChild = newLeft;
        newRootNode.rightChild = newRight;
        newRootNode.centerChild = newCenter;
        newRootNode.values = 2;
        newRootNode.isLeaf = false;

        newRootNode.leftChild.parent = newRootNode;
        newRootNode.rightChild.parent = newRootNode;
        newRootNode.centerChild.parent = newRootNode;

        if (parent != null) {
          insertValue(parent, newRootNode, value);
        } else {
          root = newRootNode;
        }
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
      } else if (node.isFourNode()) {
        if (value < node.value1) {
          insertValue(node, node.leftChild, value);
        } else if (value > node.value3) {
          insertValue(node, node.rightChild, value);
        } else if (value > node.value1 && value < node.value2) {
          insertValue(node, node.centerChild, value);
        } else {
          insertValue(node, node.centerChild, value);
        }
      }
    }
  }

  // creat
  public void create(int[] values) {
    for (int value : values) {
      addValue(value);
    }
  }

  // delet
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
        if (value < node.value1) {
          node.leftChild = delete(node.leftChild, value);
        } else if (value > node.value2) {
          node.rightChild = delete(node.rightChild, value);
        } else {
          node.centerChild = delete(node.centerChild, value);
        }
      } else if (node.isFourNode()) {
        if (value < node.value1) {
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

  // testing
  public static void main(String[] args) throws Exception {
    TwoFourTree tft = new TwoFourTree();

    // Add values to the tree
    tft.addValue(9);
    tft.addValue(7);
    tft.addValue(2);
    tft.addValue(3);
    tft.addValue(1);

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
