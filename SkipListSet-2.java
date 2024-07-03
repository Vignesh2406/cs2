import java.util.*;

public class SkipListSet <T extends Comparable<T>> implements SortedSet<T> {

    int size = 0;

    int maxLevel = 4;
    ArrayList<SkipListSetItem> head; //An increase in head means an increase in levels

    public SkipListSet() {
        ArrayList<SkipListSetItem> arrList = new ArrayList<>();
        for(int i = 0; i < maxLevel; i++){
            arrList.add(null);
        }
        //Set head to be null arrayList of size maxLevel
       head = arrList;
    }

    public SkipListSet(Collection<T> collection){
        for (T item: collection) {
            add(item);
        }
    }

    /**
     * Returns the comparator used to order the elements in this set,
     * or {@code null} if this set uses the {@linkplain Comparable
     * natural ordering} of its elements.
     *
     * @return the comparator used to order the elements in this set,
     * or {@code null} if this set uses the natural ordering
     * of its elements
     */
    @Override
    public Comparator comparator() {
        return null;
    }

    /**
     * Returns a view of the portion of this set whose elements range
     * from {@code fromElement}, inclusive, to {@code toElement},
     * exclusive.  (If {@code fromElement} and {@code toElement} are
     * equal, the returned set is empty.)  The returned set is backed
     * by this set, so changes in the returned set are reflected in
     * this set, and vice-versa.  The returned set supports all
     * optional set operations that this set supports.
     *
     * <p>The returned set will throw an {@code IllegalArgumentException}
     * on an attempt to insert an element outside its range.
     *
     * @param fromElement low endpoint (inclusive) of the returned set
     * @param toElement   high endpoint (exclusive) of the returned set
     * @return a view of the portion of this set whose elements range from
     * {@code fromElement}, inclusive, to {@code toElement}, exclusive
     * @throws ClassCastException       if {@code fromElement} and
     *                                  {@code toElement} cannot be compared to one another using this
     *                                  set's comparator (or, if the set has no comparator, using
     *                                  natural ordering).  Implementations may, but are not required
     *                                  to, throw this exception if {@code fromElement} or
     *                                  {@code toElement} cannot be compared to elements currently in
     *                                  the set.
     * @throws NullPointerException     if {@code fromElement} or
     *                                  {@code toElement} is null and this set does not permit null
     *                                  elements
     * @throws IllegalArgumentException if {@code fromElement} is
     *                                  greater than {@code toElement}; or if this set itself
     *                                  has a restricted range, and {@code fromElement} or
     *                                  {@code toElement} lies outside the bounds of the range
     */
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a view of the portion of this set whose elements are
     * strictly less than {@code toElement}.  The returned set is
     * backed by this set, so changes in the returned set are
     * reflected in this set, and vice-versa.  The returned set
     * supports all optional set operations that this set supports.
     *
     * <p>The returned set will throw an {@code IllegalArgumentException}
     * on an attempt to insert an element outside its range.
     *
     * @param toElement high endpoint (exclusive) of the returned set
     * @return a view of the portion of this set whose elements are strictly
     * less than {@code toElement}
     * @throws ClassCastException       if {@code toElement} is not compatible
     *                                  with this set's comparator (or, if the set has no comparator,
     *                                  if {@code toElement} does not implement {@link Comparable}).
     *                                  Implementations may, but are not required to, throw this
     *                                  exception if {@code toElement} cannot be compared to elements
     *                                  currently in the set.
     * @throws NullPointerException     if {@code toElement} is null and
     *                                  this set does not permit null elements
     * @throws IllegalArgumentException if this set itself has a
     *                                  restricted range, and {@code toElement} lies outside the
     *                                  bounds of the range
     */
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a view of the portion of this set whose elements are
     * greater than or equal to {@code fromElement}.  The returned
     * set is backed by this set, so changes in the returned set are
     * reflected in this set, and vice-versa.  The returned set
     * supports all optional set operations that this set supports.
     *
     * <p>The returned set will throw an {@code IllegalArgumentException}
     * on an attempt to insert an element outside its range.
     *
     * @param fromElement low endpoint (inclusive) of the returned set
     * @return a view of the portion of this set whose elements are greater
     * than or equal to {@code fromElement}
     * @throws ClassCastException       if {@code fromElement} is not compatible
     *                                  with this set's comparator (or, if the set has no comparator,
     *                                  if {@code fromElement} does not implement {@link Comparable}).
     *                                  Implementations may, but are not required to, throw this
     *                                  exception if {@code fromElement} cannot be compared to elements
     *                                  currently in the set.
     * @throws NullPointerException     if {@code fromElement} is null
     *                                  and this set does not permit null elements
     * @throws IllegalArgumentException if this set itself has a
     *                                  restricted range, and {@code fromElement} lies outside the
     *                                  bounds of the range
     */
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the first (lowest) element currently in this set.
     *
     * @return the first (lowest) element currently in this set
     * @throws NoSuchElementException if this set is empty
     */
    @Override
    public T first() {
        if(head.isEmpty()){
            throw new NoSuchElementException();
        }else{
            return getElementAtIndex(0).payload;
        }
    }

    /**
     * Returns the last (highest) element currently in this set.
     *
     * @return the last (highest) element currently in this set
     * @throws NoSuchElementException if this set is empty
     */
    @Override
    public T last() {
        if(head.isEmpty()){
            throw new NoSuchElementException();
        }else{
            SkipListSetItem node = getElementAtIndex(0);
            while(node.getNextNode() != null){
                node = node.getNextNode();
            }
            return node.payload;
        }
    }

    /**
     * Returns the number of elements in this set (its cardinality).  If this
     * set contains more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @return the number of elements in this set (its cardinality)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if this set contains no elements.
     *
     * @return {@code true} if this set contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns {@code true} if this set contains the specified element.
     * More formally, returns {@code true} if and only if this set
     * contains an element {@code e} such that
     * {@code Objects.equals(o, e)}.
     *
     * @param o element whose presence in this set is to be tested
     * @return {@code true} if this set contains the specified element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this set
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              set does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean contains(Object o) {
        if(o == null){
            throw new NullPointerException();
        }

        boolean returnCond = false;
        if(size == 0){
            returnCond = false;
        }else{
            SkipListSetItem skipItem = findElement((T) o);
            if(skipItem == null){
                returnCond = false;
            }else{
                returnCond = true;
            }
        }
        return returnCond;
    }

    /**
     * Returns an iterator over the elements in this set.  The elements are
     * returned in no particular order (unless this set is an instance of some
     * class that provides a guarantee).
     *
     * @return an iterator over the elements in this set
     */
    @Override
    public Iterator iterator() {
        return new SkipListSetIterator();
    }

    /**
     * Returns an array containing all of the elements in this set.
     * If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the
     * elements in the same order.
     *
     * <p>The returned array will be "safe" in that no references to it
     * are maintained by this set.  (In other words, this method must
     * allocate a new array even if this set is backed by an array).
     * The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all the elements in this set
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        SkipListSetItem skipItem = getElementAtIndex(0);
        while(index < size) {
            array[index] = skipItem.payload;
            skipItem = skipItem.getNextNode();
            index++;
        }
        return array;
    }

    /**
     * Adds the specified element to this set if it is not already present
     * (optional operation).  More formally, adds the specified element
     * {@code e} to this set if the set contains no element {@code e2}
     * such that
     * {@code Objects.equals(e, e2)}.
     * If this set already contains the element, the call leaves the set
     * unchanged and returns {@code false}.  In combination with the
     * restriction on constructors, this ensures that sets never contain
     * duplicate elements.
     *
     * <p>The stipulation above does not imply that sets must accept all
     * elements; sets may refuse to add any particular element, including
     * {@code null}, and throw an exception, as described in the
     * specification for {@link Collection#add Collection.add}.
     * Individual set implementations should clearly document any
     * restrictions on the elements that they may contain.
     *
     * @param obj element to be added to this set
     * @return {@code true} if this set did not already contain the specified
     * element
     * @throws UnsupportedOperationException if the {@code add} operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this set
     * @throws NullPointerException          if the specified element is null and this
     *                                       set does not permit null elements
     * @throws IllegalArgumentException      if some property of the specified element
     *                                       prevents it from being added to this set
     */
    @Override
    public boolean add(T obj) {
        boolean returnCond = true;

        if(size == 0){ //Case when there are no elements
            int height = generateRandomHeight();
            SkipListSetItem element = new SkipListSetItem(obj, null, null, height);
            for (int i = 0; i <= height; i++) {
                head.set(i,element);
            }
        }else if(obj.compareTo(head.get(0).payload)==-1){ //Case where this element is the smallest, set as head
            int height = generateRandomHeight();
            ArrayList<SkipListSetItem> nextNodes = new ArrayList<>(height);
            for (int i = 0; i <= height; i++) {
                nextNodes.add(head.get(i));
            }
            SkipListSetItem element = new SkipListSetItem(obj, head.get(0), null, nextNodes);
            setHeadNode(element, height);
        }else if(obj.compareTo(getElementAtIndex(0).payload)==0){
            return false;
        }else {
            SkipListSetItem currentNode;
            int level = head.size()-1; //Start at top

            //Find starting level from head
            while(level > 0){
                if(head.get(level) == null || obj.compareTo(head.get(level).payload) == -1) {
                    level--;
                }else{
                    break;
                }
            }

            currentNode = head.get(level);

            //Work way down if next value is null or too big, otherwise move forward. Stop at level 0
            while (level != 0) {
                if (currentNode.getNextLevelItem(level) == null || obj.compareTo(currentNode.getNextLevelItem(level).payload) == -1) {
                    level--;
                } else {
                    currentNode = currentNode.getNextLevelItem(level);
                }
            }

            //Current level is 0, check if currentNode equals obj, if so return false
            if (obj.compareTo(currentNode.payload) == 0) {
                return false;
            }else{ //Add object
                while(currentNode.getNextNode() != null && obj.compareTo(currentNode.getNextNode().payload) != -1) { //Object is greater than NextNode value, so keep going
                    currentNode = currentNode.getNextNode();
                }
                if (obj.compareTo(currentNode.payload) == 0) {
                    return false;
                }
                //Add method for adding in a newNode after currentNode spot
                addAfterNode(currentNode, obj);

                returnCond = true;
            }
        }
        size++;

        //TODO Rebalancing the list
        if(size > Math.pow(2,maxLevel)){
            increaseMaxLevel();
        }

        return returnCond;
    }

    /**
     * Increase the maxLevel of this skipListSet
     */
    private void increaseMaxLevel() {
        maxLevel++;
        head.add(null);
        reBalance();
    }

    /**
     * @param currentNode
     * @param obj
     *
     * Creates a new skipListSetItem from obj, and adds it after current node
     *
     */
    private void addAfterNode(SkipListSetItem currentNode, T obj) {
        int newHeight = generateRandomHeight(); //Height of new Node
        SkipListSetItem newNode = new SkipListSetItem(obj,currentNode.nextNode, currentNode, newHeight);

        //Put newNode as nextNode of current, and previous of its nextNode
        currentNode.setNextNode(newNode);
        if(newNode.nextNode != null){
            newNode.nextNode.setPreviousNode(newNode);
        }

        //Go back to every node with height greater than currentNode and less than or equal to newNode. And have them point to newNode
        updatePreviousNextNodesToThisNode(newNode);
    }

    /**
     * @param newNode
     *
     * This method does 2 things.
     * 1) Goes back and sets any previous nodes to point to this Node, saves nextNodesOfPreviousNodes
     * 2) Sets nextNodes(Arraylist) inside newNode to point to nextNodesOfPreviousNodes
     *
     */
    private void updatePreviousNextNodesToThisNode(SkipListSetItem newNode) {
        int level = 0;
        int height = newNode.getHeight();

        SkipListSetItem currentNode = newNode.previousNode;
        while(level <= height) { //Transfer nodes from currentNode to newNode
            if(currentNode == null){ //We have to change head
                newNode.setNextLevelItem(level, head.get(level));
                head.set(level, newNode);
                level++;
            }else if(currentNode.getHeight() >= level){
                while(level <= height && level <= currentNode.getHeight()){
                    SkipListSetItem nextLevelNode = currentNode.getNextLevelItem(level);
                    newNode.setNextLevelItem(level, nextLevelNode);
                    currentNode.setNextLevelItem(level, newNode);
                    level++;
                }
            }else{
                currentNode = currentNode.getPreviousNode();
            }
        }
    }

    /**
     * @param element Element to set at head node starting at topmost height
     * @param height Top level to reach for setting head
     */
    private void setHeadNode(SkipListSetItem element, int height) {
        for (int i = 0; i <= height; i++) {
            if(head.get(i) != null){
                head.get(i).setPreviousNode(element); //Set previous pointer
            }
            head.set(i,element);
        }
    }

    /**
     * @return Return random height less than maxLevel
     */
    private int generateRandomHeight() {
        float r = (float)Math.random();
        int lvl = 0;
        while (r < .50 && lvl < maxLevel - 1) {
            lvl++;
            r = (float)Math.random();
        }
        return lvl;
    }

//    /** Deprecated
//     * @param addItem SkipListSetItem to add
//     * @param index Index of arrayList inside arrList to add item
//     * @return
//     */
//    private boolean add(SkipListSetItem addItem, int index) {
//        boolean returnCond = true;
//        ArrayList<SkipListSetItem> list = arrList.get(index);
//        for (SkipListSetItem skipItem : list) {
//            if(skipItem.compareTo(addItem) == 0){
//                returnCond = false;
//                break;
//            }
//        }
//        if(returnCond == true){
//            list.add(addItem);
//            Collections.sort(list);
//            if(list.size() >= maxLevel){
//                rebalance(list, index);
//            }
//            returnCond = true;
//            size++;
//        }
//        return returnCond;
//    }


//    /** Deprecated
//     * @param list Index of arrayList skipList that needs to be rebalanced
//     * This method assumes that the list is at least size maxLevel/2
//     */
//    private void rebalance(ArrayList<SkipListSetItem> list, int index) {
////        ArrayList<SkipListSetItem> newList = new ArrayList<>();
////        for (int i = maxLevel/2; i < list.size(); i++) {
////            newList.add(list.get(i));
////        }
////        for (int i = maxLevel/2; i < list.size(); i++) {
////            list.remove(i);
////        }
////        list.remove(list.size()-1);
////        arrList.add(index+1, newList);
//    }

    /**
     * Removes the specified element from this set if it is present
     * (optional operation).  More formally, removes an element {@code e}
     * such that
     * {@code Objects.equals(o, e)}, if
     * this set contains such an element.  Returns {@code true} if this set
     * contained the element (or equivalently, if this set changed as a
     * result of the call).  (This set will not contain the element once the
     * call returns.)
     *
     * @param o object to be removed from this set, if present
     * @return {@code true} if this set contained the specified element
     * @throws ClassCastException            if the type of the specified element
     *                                       is incompatible with this set
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified element is null and this
     *                                       set does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the {@code remove} operation
     *                                       is not supported by this set
     */
    @Override
    public boolean remove(Object o) {
        if (o == null){
            throw new NullPointerException();
        }
        boolean returnCond = false;
        T element = (T) o;
        if(size == 0) {
            returnCond = false;
        } else{
            SkipListSetItem removeItem = findElement(element);
            if (removeItem != null) {
                if(removeItem.getPreviousNode() != null){ //Previous node is head
//                    int height = removeItem.getHeight();
//                    while(height >= 0){
//                        head.set(height, removeItem.getNextLevelItem(height));
//                        height--;
//                    }
                    removeItem.getPreviousNode().setNextNode(removeItem.getNextNode());
                }
                if(removeItem.getNextNode()!=null){
                    removeItem.getNextNode().setPreviousNode(removeItem.getPreviousNode());
                }
                updatePreviousNextNodesToRemoveThisNode(removeItem);
                returnCond = true;
                size--;
            }else{
                returnCond = false;
            }
        }

        if(size < Math.pow(2,maxLevel-2)){
            decreaseMaxLevel();
        }

        return returnCond;
    }

    private void decreaseMaxLevel() {
        maxLevel--;
        head.remove(0);
        reBalance();
    }

    /**
     *
     * Set the nextNodes of any previousItem, including the head, to not have this item
     *
     * @param removeItem Item to remove from any nextNodes in this set
     */
    private void updatePreviousNextNodesToRemoveThisNode(SkipListSetItem removeItem) {
        int level = 0;
        int height = removeItem.getHeight();

        SkipListSetItem currentNode = removeItem.previousNode;
        while(level <= height) { //Transfer nodes from currentNode to newNode
            if(currentNode == null){ //We have to change head
                head.set(level, removeItem.getNextLevelItem(level));
                level++;
            }else if(currentNode.getHeight() >= level){
                while(level <= height && level <= currentNode.getHeight()){
                    currentNode.setNextLevelItem(level, removeItem.getNextLevelItem(level));
                    level++;
                }
            }else{
                currentNode = currentNode.getPreviousNode();
            }
        }
    }

    /**
     * @param element Element to find in this skipListSet
     * @return
     */
    private SkipListSetItem findElement(T element) {
        SkipListSetItem currentNode;
        SkipListSetItem returnElement = null;
        int level = head.size()-1; //Start at top

        //Find starting level from head
        while(level > 0){
            if(head.get(level) == null || element.compareTo(head.get(level).payload) != 1) {
                level--;
            }else{
                break;
            }
        }

        currentNode = head.get(level);

        //Work way down if next value is null or too big, otherwise move forward. Stop at level 0
        while (level != 0) {
            if (currentNode.getNextLevelItem(level) == null || element.compareTo(currentNode.getNextLevelItem(level).payload) != 1) {
                level--;
            } else {
                currentNode = currentNode.getNextLevelItem(level);
            }
        }

        //Level has to be at 0, keep moving forward if that's possible
        while(currentNode.getNextLevelItem(level) != null && element.compareTo(currentNode.getNextLevelItem(level).payload) != -1){
            currentNode = currentNode.getNextNode();
        }

        if(element.compareTo(currentNode.payload) == 0){
            returnElement = currentNode;
        }

        return returnElement;
    }

    /**
     * Adds all of the elements in the specified collection to this set if
     * they're not already present (optional operation).  If the specified
     * collection is also a set, the {@code addAll} operation effectively
     * modifies this set so that its value is the <i>union</i> of the two
     * sets.  The behavior of this operation is undefined if the specified
     * collection is modified while the operation is in progress.
     *
     * @param c collection containing elements to be added to this set
     * @return {@code true} if this set changed as a result of the call
     * @throws UnsupportedOperationException if the {@code addAll} operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of the
     *                                       specified collection prevents it from being added to this set
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null elements and this set does not permit null
     *                                       elements, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this set
     * @see #add(Object)
     */
    @Override
    public boolean addAll(Collection c) {
        if(c == null){
            throw new NullPointerException();
        }
        boolean returnCond = false;
        for (Object item : c) {
            if(item == null){
                throw new NullPointerException();
            }
            if(add((T) item)){
                returnCond = true;
            }
        }
        return returnCond;
    }

    /**
     * Removes all of the elements from this set (optional operation).
     * The set will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the {@code clear} method
     *                                       is not supported by this set
     */
    @Override
    public void clear() {
        for(int i = 0; i < head.size(); i++){
            head.set(i, null);
        }
        this.size = 0;
    }

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection (optional operation).  If the specified
     * collection is also a set, this operation effectively modifies this
     * set so that its value is the <i>asymmetric set difference</i> of
     * the two sets.
     *
     * @param c collection containing elements to be removed from this set
     * @return {@code true} if this set changed as a result of the call
     * @throws UnsupportedOperationException if the {@code removeAll} operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of this set
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this set contains a null element and the
     *                                       specified collection does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean removeAll(Collection c) {
        boolean returnCond = false;
        for (Object obj : c) {
            if(remove(obj)){
                returnCond = true;
            }
        }
        return returnCond;
    }

    /**
     * Retains only the elements in this set that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this set all of its elements that are not contained in the
     * specified collection.  If the specified collection is also a set, this
     * operation effectively modifies this set so that its value is the
     * <i>intersection</i> of the two sets.
     *
     * @param c collection containing elements to be retained in this set
     * @return {@code true} if this set changed as a result of the call
     * @throws UnsupportedOperationException if the {@code retainAll} operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of this set
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this set contains a null element and the
     *                                       specified collection does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     */
    @Override
    public boolean retainAll(Collection c) {
        boolean returnCond = false;

        SkipListSetItem skipItem = getElementAtIndex(0);
        while(skipItem != null) {
            if (!c.contains(skipItem.payload)) {
                remove(skipItem.payload);
                returnCond = true;
            }
            skipItem = skipItem.nextNode;
        }
        return returnCond;
    }

    /**
     * Returns {@code true} if this set contains all of the elements of the
     * specified collection.  If the specified collection is also a set, this
     * method returns {@code true} if it is a <i>subset</i> of this set.
     *
     * @param c collection to be checked for containment in this set
     * @return {@code true} if this set contains all of the elements of the
     * specified collection
     * @throws ClassCastException   if the types of one or more elements
     *                              in the specified collection are incompatible with this
     *                              set
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *                              or more null elements and this set does not permit null
     *                              elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              or if the specified collection is null
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(Collection c) {
        boolean returnCond = true;
        for (Object item : c) {
            if(!contains(item)){
                returnCond = false;
                break;
            }
        }
        return returnCond;
    }

    /**
     * Returns an array containing all of the elements in this set; the
     * runtime type of the returned array is that of the specified array.
     * If the set fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this set.
     *
     * <p>If this set fits in the specified array with room to spare
     * (i.e., the array has more elements than this set), the element in
     * the array immediately following the end of the set is set to
     * {@code null}.  (This is useful in determining the length of this
     * set <i>only</i> if the caller knows that this set does not contain
     * any null elements.)
     *
     * <p>If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements
     * in the same order.
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose {@code x} is a set known to contain only strings.
     * The following code can be used to dump the set into a newly allocated
     * array of {@code String}:
     *
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     * <p>
     * Note that {@code toArray(new Object[0])} is identical in function to
     * {@code toArray()}.
     *
     * @param a the array into which the elements of this set are to be
     *          stored, if it is big enough; otherwise, a new array of the same
     *          runtime type is allocated for this purpose.
     * @return an array containing all the elements in this set
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in this
     *                              set
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public Object[] toArray(Object[] a) {
//        if(a.length >= size){
//            int index = 0;
//            for (ArrayList<SkipListSetItem> list : arrList) {
//                for (SkipListSetItem subList : list) {
//                    a[index] = subList.payload;
//                    index++;
//                }
//            }
//            while(index < a.length){
//                a[index] = null;
//                index++;
//            }
//            return a;
//        }else{
//            return toArray();
//        }
        SkipListSet<T> skipList = new SkipListSet<>();
        skipList.addAll(List.of(a));
        return skipList.toArray();
    }

    /**
     * @param obj object to be compared for equality with this set
     * @return
     *
     * Check if all the elements of both sets are the same, return true if yes
     *
     */
    @Override
    public boolean equals(Object obj){
        boolean returnCond = false;
        SkipListSet<T> skipsListSet = (SkipListSet<T>) obj;

        SkipListSetItem thisSetItems = getElementAtIndex(0);
        SkipListSetItem objSetItems = skipsListSet.getElementAtIndex(0);
        while(thisSetItems != null && objSetItems != null){
            if(thisSetItems.compareTo(objSetItems)==0){
                returnCond = true;
            }else{
                break;
            }
        }

        return returnCond;
    }

    /**
     * @param index Index of element to get
     * @return
     *
     * Get element at specified index
     *
     */
    private SkipListSetItem getElementAtIndex(int index) {
        if(size < index || index < 0){
            throw new IndexOutOfBoundsException();
        }
        SkipListSetItem currentElement = head.get(0); //Start at bottom level where all the elements are
        for(int i = 0; i < index; i++){
            currentElement = currentElement.getNextNode();
        }
        return currentElement;
    }

    public void reBalance() {
        SkipListSetItem currentItem = getElementAtIndex(0);
        clear();
        while(currentItem != null){
            add(currentItem.payload);
            currentItem = currentItem.getNextNode();
        }
    }

    private class SkipListSetIterator<T extends Comparable<T>> implements Iterator<T>{
        int indexList = 0;
        int indexElements = 0;

        SkipListSetItem currentItem = getElementAtIndex(0);
        int index = 0;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            if(size > index+1){
                return true;
            }else{
                return false;
            }
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            T payload = (T) currentItem.payload;
            currentItem = currentItem.getNextNode();
            return payload;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.
         * <p>
         * The behavior of an iterator is unspecified if the underlying collection
         * is modified while the iteration is in progress in any way other than by
         * calling this method, unless an overriding class has specified a
         * concurrent modification policy.
         * <p>
         * The behavior of an iterator is unspecified if this method is called
         * after a call to the {@link #forEachRemaining forEachRemaining} method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
            SkipListSet.this.remove(currentItem);
        }

    }

    private class SkipListSetItem implements Comparable{

        //Height starts at 0, which is level 1
        public int height;
        private T payload; //Holds the value

        private SkipListSetItem nextNode; //Points to next node

        private SkipListSetItem previousNode; //Points to previous node

        private ArrayList<SkipListSetItem> nextNodes;

        public SkipListSetItem(T payload, SkipListSetItem nextNode, SkipListSetItem previousNode, ArrayList<SkipListSetItem> nextNodes){
            this.payload = payload;
            this.nextNode = nextNode;
            this.previousNode = previousNode;
            this.nextNodes = nextNodes;
            this.height = nextNodes.size()-1;
        }

        public SkipListSetItem(T payload, SkipListSetItem nextNode, SkipListSetItem previousNode, int height){
            this.payload = payload;
            this.nextNode = nextNode;
            this.previousNode = previousNode;
            this.height = height;

            ArrayList<SkipListSetItem> arrList = new ArrayList<>();
            for(int i = 0; i <= height; i++){
                arrList.add(null);
            }
            //Set head to be null arrayList of size maxLevel
            this.nextNodes = arrList;
        }

        public T getPayload() {
            return payload;
        }

        public SkipListSetItem getNextNode() {
            return nextNode;
        }
//
        public SkipListSetItem getPreviousNode() {
            return previousNode;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }


        public void setPayload(T payload) {
            this.payload = payload;
        }

        public void setNextNode(SkipListSetItem nextNode) {
            this.nextNode = nextNode;
        }

        public void setPreviousNode(SkipListSetItem previousNode) {
            this.previousNode = previousNode;
        }

        /**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.
         *
         * <p>The implementor must ensure {@link Integer#signum
         * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
         * all {@code x} and {@code y}.  (This implies that {@code
         * x.compareTo(y)} must throw an exception if and only if {@code
         * y.compareTo(x)} throws an exception.)
         *
         * <p>The implementor must also ensure that the relation is transitive:
         * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
         * {@code x.compareTo(z) > 0}.
         *
         * <p>Finally, the implementor must ensure that {@code
         * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
         * == signum(y.compareTo(z))}, for all {@code z}.
         *
         * @param obj the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         * @throws NullPointerException if the specified object is null
         * @throws ClassCastException   if the specified object's type prevents it
         *                              from being compared to this object.
         * @apiNote It is strongly recommended, but <i>not</i> strictly required that
         * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
         * class that implements the {@code Comparable} interface and violates
         * this condition should clearly indicate this fact.  The recommended
         * language is "Note: this class has a natural ordering that is
         * inconsistent with equals."
         */
        @Override
        public int compareTo(Object obj) {
            SkipListSetItem skipItem = (SkipListSetItem) obj;
            return payload.compareTo(skipItem.payload);
        }

        /**
         * @param level
         * @return Get the next node stored in the arrayList at that level
         */
        public SkipListSetItem getNextLevelItem(int level) {
            return nextNodes.get(level);
        }

        public void setNextNodes(ArrayList<SkipListSetItem> newNextNodes) {
            this.nextNodes = newNextNodes;
        }

        public void setNextLevelItem(int level, SkipListSetItem skipItem) {
            nextNodes.set(level,skipItem);
        }
    }
}
