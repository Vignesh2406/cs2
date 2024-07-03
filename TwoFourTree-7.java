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
    delete(null, root, value);
  }

  private TwoFourTreeItem delete(TwoFourTreeItem parent, TwoFourTreeItem node, int value) {
    if (node == null) {
      System.out.println("Value not found in the tree.");
      return null;
    }

    if (node.isTwoNode() && node.isRoot()) {
      if(node.leftChild.isTwoNode() && node.rightChild.isTwoNode()){ //Case if root is 2 node and children are 2 nodes, combine root and children
        TwoFourTreeItem newRoot = new TwoFourTreeItem(node.value1, node.leftChild.value1, node.rightChild.value1); //Combine root and children into one node
        //Update all childNode to have parents reflect this newRoot
        node.leftChild.leftChild.parent = newRoot;
        node.leftChild.rightChild.parent = newRoot;
        node.rightChild.leftChild.parent = newRoot;
        node.rightChild.rightChild.parent = newRoot;

        //Set children of newRoot
        newRoot.leftChild = node.leftChild.leftChild;
        newRoot.centerLeftChild = node.leftChild.rightChild;
        newRoot.centerRightChild = node.rightChild.leftChild;
        newRoot.rightChild = node.rightChild.rightChild;
      }else{
        //TODO If something doesn't work correctly with the root
      }
    }else if(node.isTwoNode()){
      TwoFourTreeItem fusedNode = null;
      //Check to see if any siblings are 2 or 3 nodes
      if(parent.isThreeNode()){ //Parent is a 3 Node and this is a 2 Node
        if(node.equals(parent.leftChild) || node.equals(parent.rightChild)){ //Node is leftChild or rightChild
          if(parent.centerChild.isThreeNode() || parent.centerChild.isFourNode()){
            rotateWithSibling(parent, node, parent.centerChild);
          }else { //Plan B, fuse with parent facing element and sibling
            fusedNode = fuse(parent, node, parent.centerChild); //Fuse has to be done with larger sibling
          }
        }else { //Node is centerChild, check it's left and right siblings are 3 or 4 nodes
          if(parent.leftChild.isThreeNode() || parent.leftChild.isFourNode()){
            rotateWithSibling(parent, node, parent.leftChild);
          }else if(parent.rightChild.isThreeNode() || parent.rightChild.isFourNode()){
            rotateWithSibling(parent, node, parent.rightChild);
          } else { //Plan B
            fusedNode = fuse(parent, node, parent.rightChild); //Fuse has to be done with larger sibling
          }
        }
      } else { //Parent is 4 Node and this is a 2 Node
        if(node.equals(parent.leftChild)){ //Node is leftChild
          if(parent.centerLeftChild.isThreeNode() || parent.centerLeftChild.isFourNode()){
            rotateWithSibling(parent, node, parent.centerLeftChild);
          }else { //Plan B
            fusedNode = fuse(parent, node, parent.centerLeftChild); //Fuse has to be done with larger sibling
          }
        }else if(node.equals(parent.centerLeftChild)){ //Node is centerLeftChild, check it's left and right siblings are 3 or 4 nodes
          if(parent.leftChild.isThreeNode() || parent.leftChild.isFourNode()){
            rotateWithSibling(parent, node, parent.leftChild);
          }else if(parent.centerRightChild.isThreeNode() || parent.centerRightChild.isFourNode()){
            rotateWithSibling(parent, node, parent.centerRightChild);
          } else { //Plan B
            fusedNode = fuse(parent, node, parent.centerRightChild); //Fuse has to be done with larger sibling
          }
        }else if(node.equals(parent.centerRightChild)){ //Node is centerChild, check it's left and right siblings are 3 or 4 nodes
          if(parent.centerLeftChild.isThreeNode() || parent.centerLeftChild.isFourNode()){
            rotateWithSibling(parent, node, parent.centerLeftChild);
          }else if(parent.rightChild.isThreeNode() || parent.rightChild.isFourNode()){
            rotateWithSibling(parent, node, parent.rightChild);
          } else { //Plan B
            fusedNode = fuse(parent, node, parent.rightChild); //Fuse has to be done with larger sibling
          }
        }else{ //Node is rightChild
          if(parent.centerRightChild.isThreeNode() || parent.centerRightChild.isFourNode()){ //Check to see if sibling is 3 or 4 node
            rotateWithSibling(parent, node, parent.centerRightChild);
          } else { //Plan B
            fusedNode = fuse(parent, node, parent.centerRightChild); //Smaller child in this case
          }
        }
      }
      //Run delete on new fused node instead
      return delete(fusedNode.parent, fusedNode, value);
    }
    if (node.isLeaf) { //If node is leaf, delete value or state value is not found
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
    } else { //Node is not leaf and need to keep searching
      //SHOULD ONLY HAVE 3 AND 4 NODES
      if (node.isTwoNode()) {
        //TODO WHat happens if we need to delete the root node?
//        if(node.value1 == value || node.value2 == value) {
//          TwoFourTreeItem searchNode = node.rightChild;
//          while (searchNode.leftChild != null) {
//            searchNode = searchNode.leftChild;
//          }
//          if(node.value1 == value){
//            node.value1 = searchNode.value1;
//            delete(node, node.rightChild, searchNode.value1);
//          }else if(node.value2 == value){
//            node.value2 = searchNode.value1;
//            delete(node,node.rightChild, searchNode.value1);
//          }else{
//            node.value3 = searchNode.value1;
//            delete(node,node.rightChild, searchNode.value1);
//          }
//        }else
          if (value < node.value1) {
          node.leftChild = delete(node, node.leftChild, value);
        } else {
          node.rightChild = delete(node, node.rightChild, value);
        }
      } else if (node.isThreeNode()) {
        if(node.value1 == value || node.value2 == value) {
          TwoFourTreeItem searchNode = node.rightChild;
          while (searchNode.leftChild != null) {
            searchNode = searchNode.leftChild;
          }
          if(node.value1 == value){
            node.value1 = searchNode.value1;
            delete(node, node.rightChild, searchNode.value1);
          }else if(node.value2 == value){
            node.value2 = searchNode.value1;
            delete(node,node.rightChild, searchNode.value1);
          }else{
            node.value3 = searchNode.value1;
            delete(node,node.rightChild, searchNode.value1);
          }
        }else if (value < node.value1) {
          delete(node, node.leftChild, value);
        } else if (value > node.value2) {
          delete(node, node.rightChild, value);
        } else {
          delete(node, node.centerChild, value);
        }
      } else if (node.isFourNode()) {
        if(node.value1 == value || node.value2 == value || node.value3 == value) {
          TwoFourTreeItem searchNode = node.rightChild;
          while (searchNode.leftChild != null) {
            searchNode = searchNode.leftChild;
          }
          if(node.value1 == value){
            node.value1 = searchNode.value1;
            delete(node, node.rightChild, searchNode.value1);
          }else if(node.value2 == value){
            node.value2 = searchNode.value1;
            delete(node,node.rightChild, searchNode.value1);
          }else{
            node.value3 = searchNode.value1;
            delete(node,node.rightChild, searchNode.value1);
          }
        }else if (value < node.value1) {
          node.leftChild = delete(node, node.leftChild, value);
        } else if (value > node.value3) {
          node.rightChild = delete(node,node.rightChild, value);
        } else if (value > node.value1 && value < node.value2) {
          node.centerLeftChild = delete(node,node.centerLeftChild, value);
        } else if (value > node.value2 && value < node.value3) {
          node.centerRightChild = delete(node,node.centerRightChild, value);
        }
      }
    }
    return node;
  }

  /**
   * @param parent
   * @param node
   * @param greaterSibling Always greater child if possible
   * @return
   */
  private TwoFourTreeItem fuse(TwoFourTreeItem parent, TwoFourTreeItem node, TwoFourTreeItem greaterSibling) {
    TwoFourTreeItem newNode;
    if (parent.isThreeNode()){
      if(node.equals(parent.leftChild)){ //Node is left child of parent

        //Create new 4 Node and replace node and greaterSibling node with it
        newNode = new TwoFourTreeItem(node.value1, parent.value1, greaterSibling.value1);
        newNode.leftChild = node.leftChild;
        newNode.centerLeftChild = node.rightChild;
        newNode.centerRightChild = greaterSibling.leftChild;
        newNode.rightChild = greaterSibling.rightChild;

        //Update parent variable of children nodes to newNode
        if(newNode.leftChild != null){
          newNode.leftChild.parent = newNode;
        }
        if(newNode.centerLeftChild != null){
          newNode.centerLeftChild.parent = newNode;
        }
        if(newNode.centerRightChild != null){
          newNode.centerRightChild.parent = newNode;
        }
        if(newNode.rightChild != null){
          newNode.rightChild.parent = newNode;
        }

        //Update parent node to 2 node
        parent.values = 1;
        parent.value1 = parent.value2;
        parent.value2 = 0;
        parent.leftChild = newNode;
        parent.centerChild = null;

      }else if(node.equals(parent.centerChild)){ //Node is center child of parent

        //Create new 4 Node and replace node and greaterSibling node with it
        newNode = new TwoFourTreeItem(node.value1, parent.value2, greaterSibling.value1);
        newNode.leftChild = node.leftChild;
        newNode.centerLeftChild = node.rightChild;
        newNode.centerRightChild = greaterSibling.leftChild;
        newNode.rightChild = greaterSibling.rightChild;

        //Update parent variable of children nodes to newNode
        if(newNode.leftChild != null){
          newNode.leftChild.parent = newNode;
        }
        if(newNode.centerLeftChild != null){
          newNode.centerLeftChild.parent = newNode;
        }
        if(newNode.centerRightChild != null){
          newNode.centerRightChild.parent = newNode;
        }
        if(newNode.rightChild != null){
          newNode.rightChild.parent = newNode;
        }

        //Update parent node to 2 node
        parent.values = 1;
        parent.value2 = 0;
        parent.rightChild = newNode;
        parent.centerChild = null;
      }else{ //Node is right child of parent, greaterSibling is center IE to the left

        //Create new 4 Node and replace node and greaterSibling node with it
        newNode = new TwoFourTreeItem(greaterSibling.value1, parent.value2, node.value1);
        newNode.leftChild = greaterSibling.leftChild;
        newNode.centerLeftChild = greaterSibling.rightChild;
        newNode.centerRightChild = node.leftChild;
        newNode.rightChild = node.rightChild;

        //Update parent variable of children nodes to newNode
        if(newNode.leftChild != null){
          newNode.leftChild.parent = newNode;
        }
        if(newNode.centerLeftChild != null){
          newNode.centerLeftChild.parent = newNode;
        }
        if(newNode.centerRightChild != null){
          newNode.centerRightChild.parent = newNode;
        }
        if(newNode.rightChild != null){
          newNode.rightChild.parent = newNode;
        }

        //Update parent node to 2 node
        parent.values = 1;
        parent.value2 = 0;
        parent.rightChild = newNode;
        parent.centerChild = null;
      }
    }else{ //Parent is 4 Node
      if(node.equals(parent.leftChild)){ //Node is left child of 4 node parent and greaterSibling is centerLeftChild

        //Create new 4 Node and replace node and greaterSibling node with it
        newNode = new TwoFourTreeItem(node.value1, parent.value1, greaterSibling.value1);
        newNode.leftChild = node.leftChild;
        newNode.centerLeftChild = node.rightChild;
        newNode.centerRightChild = greaterSibling.leftChild;
        newNode.rightChild = greaterSibling.rightChild;

        //Update parent variable of children nodes to newNode
        if(newNode.leftChild != null){
          newNode.leftChild.parent = newNode;
        }
        if(newNode.centerLeftChild != null){
          newNode.centerLeftChild.parent = newNode;
        }
        if(newNode.centerRightChild != null){
          newNode.centerRightChild.parent = newNode;
        }
        if(newNode.rightChild != null){
          newNode.rightChild.parent = newNode;
        }

        //Update parent node to 3 node
        parent.values = 2;
        parent.value1 = parent.value2;
        parent.value2 = parent.value3;
        parent.value3 = 0;
        parent.leftChild = newNode;
        parent.centerLeftChild = null;
        parent.centerChild = parent.centerRightChild;
        parent.centerRightChild = null;

      }else if(node.equals(parent.centerLeftChild)){ //Node is centerLeftChild of 4 node parent, sibling is centerRightChild

        //Create new 4 Node and replace node and greaterSibling node with it
        newNode = new TwoFourTreeItem(node.value1, parent.value2, greaterSibling.value1);
        newNode.leftChild = node.leftChild;
        newNode.centerLeftChild = node.rightChild;
        newNode.centerRightChild = greaterSibling.leftChild;
        newNode.rightChild = greaterSibling.rightChild;

        //Update parent variable of children nodes to newNode
        if(newNode.leftChild != null){
          newNode.leftChild.parent = newNode;
        }
        if(newNode.centerLeftChild != null){
          newNode.centerLeftChild.parent = newNode;
        }
        if(newNode.centerRightChild != null){
          newNode.centerRightChild.parent = newNode;
        }
        if(newNode.rightChild != null){
          newNode.rightChild.parent = newNode;
        }
        //Update parent node to 3 node
        parent.values = 2;
        parent.value2 = parent.value3;
        parent.value3 = 0;
        parent.centerChild = newNode;
        parent.centerLeftChild = null;
        parent.centerRightChild = null;

      }else if(node.equals(parent.centerRightChild)) { //Node is centerRightChild of 4 node parent, sibling is rightChild

        //Create new 4 Node and replace node and greaterSibling node with it
        newNode = new TwoFourTreeItem(node.value1, parent.value3, greaterSibling.value1);
        newNode.leftChild = node.leftChild;
        newNode.centerLeftChild = node.rightChild;
        newNode.centerRightChild = greaterSibling.leftChild;
        newNode.rightChild = greaterSibling.rightChild;

        //Update parent variable of children nodes to newNode
        if(newNode.leftChild != null){
          newNode.leftChild.parent = newNode;
        }
        if(newNode.centerLeftChild != null){
          newNode.centerLeftChild.parent = newNode;
        }
        if(newNode.centerRightChild != null){
          newNode.centerRightChild.parent = newNode;
        }
        if(newNode.rightChild != null){
          newNode.rightChild.parent = newNode;
        }

        //Update parent node to 3 node, make rightChild newNode
        parent.values = 2;
        parent.value3 = 0;
        parent.centerChild = parent.centerLeftChild;
        parent.rightChild = newNode;
        parent.centerLeftChild = null;
        parent.centerRightChild = null;

      }else{ //Node is right child of 4 node parent, greaterSibling is centerRightChild IE to the left

        //Create new 4 Node and replace node and greaterSibling node with it
        newNode = new TwoFourTreeItem(greaterSibling.value1, parent.value3, node.value1);
        newNode.leftChild = greaterSibling.leftChild;
        newNode.centerLeftChild = greaterSibling.rightChild;
        newNode.centerRightChild = node.leftChild;
        newNode.rightChild = node.rightChild;

        //Update parent variable of children nodes to newNode
        if(newNode.leftChild != null){
          newNode.leftChild.parent = newNode;
        }
        if(newNode.centerLeftChild != null){
          newNode.centerLeftChild.parent = newNode;
        }
        if(newNode.centerRightChild != null){
          newNode.centerRightChild.parent = newNode;
        }
        if(newNode.rightChild != null){
          newNode.rightChild.parent = newNode;
        }

        //Update parent node to 3 node
        parent.values = 2;
        parent.value3 = 0;
        parent.rightChild = newNode;
        parent.centerChild = parent.centerLeftChild;
        parent.centerLeftChild = null;
        parent.centerRightChild = null;
      }
    }
    return newNode;
  }

  /**
   * @param parent A 3 or 4 node
   * @param node A 2 Node
   * @param sibling A 3 or 4 node
   */
  private void rotateWithSibling(TwoFourTreeItem parent, TwoFourTreeItem node, TwoFourTreeItem sibling){
    if(parent.isThreeNode()){
      if(sibling.value1 > node.value1){ //Sibling is to the right,IE greater
        if(node.equals(parent.leftChild)){ //This node is left child of 3 node, sibling is center
          //Make this a 3 node
          node.value2 = parent.value1;
          node.centerChild = node.rightChild;
          node.rightChild = sibling.leftChild;
          node.values = 2;

          //Fix parent value
          parent.value1 = sibling.value1;

          //Decrease sibling
          sibling.value1 = sibling.value2;
          sibling.value2 = sibling.value3;
          sibling.value3 = 0;
          sibling.values -= 1;
          if(sibling.isThreeNode()){ //Sibling is ThreeNode
            sibling.leftChild = sibling.centerChild;
            sibling.centerChild = null;
          }else{ //Sibling is FourNode
            sibling.leftChild = sibling.centerLeftChild;
            sibling.centerChild = sibling.centerRightChild;
            sibling.centerLeftChild = null;
            sibling.centerRightChild = null;
          }
        }else{ //This node is middle child of 3 node, sibling is rightChild
          //Make this a 3 node
          node.value2 = parent.value2;
          node.centerChild = node.rightChild;
          node.rightChild = sibling.leftChild;
          node.values = 2;

          //Fix parent value
          parent.value2 = sibling.value1;

          //Decrease sibling
          sibling.value1 = sibling.value2;
          sibling.value2 = sibling.value3;
          sibling.value3 = 0;
          sibling.values -= 1;
          if(sibling.isThreeNode()){ //Sibling is ThreeNode
            sibling.leftChild = sibling.centerChild;
            sibling.centerChild = null;
          }else{ //Sibling is FourNode
            sibling.leftChild = sibling.centerLeftChild;
            sibling.centerChild = sibling.centerRightChild;
            sibling.centerLeftChild = null;
            sibling.centerRightChild = null;
          }
        }
      }else{ //Sibling is to the left
        if(node.equals(parent.rightChild)) { //This node is right child of 3 node, sibling is centerChild
          //Make node a 3 node
          node.value2 = node.value1;
          node.value1 = parent.value2;
          node.centerChild = node.leftChild;
          node.leftChild = sibling.rightChild;
          node.values = 2;

          if(sibling.isThreeNode()){ //Sibling is ThreeNode
            //Fix parent value
            parent.value2 = sibling.value2;

            //Decrease sibling
            sibling.value2 = 0;
            sibling.values = 1;

            sibling.rightChild = sibling.centerChild;
            sibling.centerChild = null;
          }else{ //Sibling is FourNode
            //Fix parent value
            parent.value2 = sibling.value3;

            //Decrease sibling
            sibling.value3 = 0;
            sibling.values = 2;

            sibling.rightChild = sibling.centerRightChild;
            sibling.centerChild = sibling.centerLeftChild;
            sibling.centerLeftChild = null;
            sibling.centerRightChild = null;
          }
        }else{ //This node is middle child of 3 node, sibling is leftChild
          //Make node a 3 node
          node.value2 = node.value1;
          node.value1 = parent.value1;
          node.centerChild = node.leftChild;
          node.leftChild = sibling.rightChild;
          node.values = 2;

          if(sibling.isThreeNode()){ //Sibling is ThreeNode
            //Fix parent value
            parent.value1 = sibling.value2;

            //Decrease sibling
            sibling.value2 = 0;
            sibling.values = 1;

            sibling.rightChild = sibling.centerChild;
            sibling.centerChild = null;
          }else{ //Sibling is FourNode
            //Fix parent value
            parent.value1 = sibling.value3;

            //Decrease sibling
            sibling.value3 = 0;
            sibling.values = 2;

            sibling.rightChild = sibling.centerRightChild;
            sibling.centerChild = sibling.centerLeftChild;
            sibling.centerLeftChild = null;
            sibling.centerRightChild = null;
          }
        }
      }
    }else{ //Parent is 4 node
      if(sibling.value1 > node.value1){ //Sibling is to the right,IE greater
        if(node.equals(parent.leftChild)){ //This node is left child of 4 node, sibling is centerLeftChild
          //Make this a 3 node
          node.value2 = parent.value1;
          node.centerChild = node.rightChild;
          node.rightChild = sibling.leftChild;
          node.values = 2;

          //Fix parent value
          parent.value1 = sibling.value1;

          //Decrease sibling
          sibling.value1 = sibling.value2;
          sibling.value2 = sibling.value3;
          sibling.value3 = 0;
          sibling.values -= 1;
          if(sibling.isThreeNode()){ //Sibling is ThreeNode
            sibling.leftChild = sibling.centerChild;
            sibling.centerChild = null;
            sibling.values = 1;
          }else{ //Sibling is FourNode
            sibling.leftChild = sibling.centerLeftChild;
            sibling.centerChild = sibling.centerRightChild;
            sibling.centerLeftChild = null;
            sibling.centerRightChild = null;
            sibling.values = 1;
          }
        }else if(node.equals(parent.centerLeftChild)){ //This node is centerLeftChild of 4 node, sibling is centerRightChild
          //Make this a 3 node
          node.value2 = parent.value2;
          node.centerChild = node.rightChild;
          node.rightChild = sibling.leftChild;
          node.values = 2;

          //Fix parent value
          parent.value2 = sibling.value1;

          //Decrease sibling
          sibling.value1 = sibling.value2;
          sibling.value2 = sibling.value3;
          sibling.value3 = 0;
          sibling.values -= 1;
          if(sibling.isThreeNode()){ //Sibling is ThreeNode
            sibling.leftChild = sibling.centerChild;
            sibling.centerChild = null;
            sibling.values = 1;
          }else{ //Sibling is FourNode
            sibling.leftChild = sibling.centerLeftChild;
            sibling.centerChild = sibling.centerRightChild;
            sibling.centerLeftChild = null;
            sibling.centerRightChild = null;
            sibling.values = 2;
          }
        }else{ //This node is centerRightChild of 4 node, sibling is rightChild
          //Make this a 3 node
          node.value2 = parent.value3;
          node.centerChild = node.rightChild;
          node.rightChild = sibling.leftChild;
          node.values = 2;

          //Fix parent value
          parent.value3 = sibling.value1;

          //Decrease sibling
          sibling.value1 = sibling.value2;
          sibling.value2 = sibling.value3;
          sibling.value3 = 0;
          if(sibling.isThreeNode()){ //Sibling is ThreeNode
            sibling.leftChild = sibling.centerChild;
            sibling.centerChild = null;
            sibling.values = 1;
          }else{ //Sibling is FourNode
            sibling.leftChild = sibling.centerLeftChild;
            sibling.centerChild = sibling.centerRightChild;
            sibling.centerLeftChild = null;
            sibling.centerRightChild = null;
            sibling.values = 2;
          }
        }
      }else{ //Sibling is to the left
        if(node.equals(parent.rightChild)) { //This node is right child of 4 node, sibling is centerRightChild
          //Make node a 3 node
          node.value2 = node.value1;
          node.value1 = parent.value3;
          node.centerChild = node.leftChild;
          node.leftChild = sibling.rightChild;
          node.values = 2;

          if(sibling.isThreeNode()){ //Sibling is ThreeNode
            //Fix parent value
            parent.value3 = sibling.value2;

            //Decrease sibling
            sibling.value2 = 0;
            sibling.values = 1;

            sibling.rightChild = sibling.centerChild;
            sibling.centerChild = null;
          }else{ //Sibling is FourNode
            //Fix parent value
            parent.value3 = sibling.value3;

            //Decrease sibling
            sibling.value3 = 0;
            sibling.values = 2;

            sibling.rightChild = sibling.centerRightChild;
            sibling.centerChild = sibling.centerLeftChild;
            sibling.centerLeftChild = null;
            sibling.centerRightChild = null;
          }
        }else if(node.equals(parent.centerRightChild)){ //This node is centerRightChild of 4 node, sibling is centerLeftChild
          //Make node a 3 node
          node.value2 = node.value1;
          node.value1 = parent.value2;
          node.centerChild = node.leftChild;
          node.leftChild = sibling.rightChild;
          node.values = 2;

          if(sibling.isThreeNode()){ //Sibling is ThreeNode
            //Fix parent value
            parent.value2 = sibling.value2;

            //Decrease sibling
            sibling.value2 = 0;
            sibling.values = 1;

            sibling.rightChild = sibling.centerChild;
            sibling.centerChild = null;
          }else{ //Sibling is FourNode
            //Fix parent value
            parent.value2 = sibling.value3;

            //Decrease sibling
            sibling.value3 = 0;
            sibling.values = 2;

            sibling.rightChild = sibling.centerRightChild;
            sibling.centerChild = sibling.centerLeftChild;
            sibling.centerLeftChild = null;
            sibling.centerRightChild = null;
          }
        }else{//This node is centerLeftChild of 4 node, sibling is leftChild
          //Make node a 3 node
          node.value2 = node.value1;
          node.value1 = parent.value1;
          node.centerChild = node.leftChild;
          node.leftChild = sibling.rightChild;
          node.values = 2;

          if(sibling.isThreeNode()){ //Sibling is ThreeNode
            //Fix parent value
            parent.value1 = sibling.value2;

            //Decrease sibling
            sibling.value2 = 0;
            sibling.values = 1;

            sibling.rightChild = sibling.centerChild;
            sibling.centerChild = null;
          }else{ //Sibling is FourNode
            //Fix parent value
            parent.value1 = sibling.value3;

            //Decrease sibling
            sibling.value3 = 0;
            sibling.values = 2;

            sibling.rightChild = sibling.centerRightChild;
            sibling.centerChild = sibling.centerLeftChild;
            sibling.centerLeftChild = null;
            sibling.centerRightChild = null;
          }
        }
      }
    }
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
