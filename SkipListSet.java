import java.util.*;

public class SkipListSet <T extends Comparable<T>> implements SortedSet<T> {

    int maxLevel = 5;

    int size;
    ArrayList<ArrayList<SkipListSetItem>> arrList;

    public SkipListSet() {
       arrList = new ArrayList<ArrayList<SkipListSetItem>>();
       int size = 0;
    }

    public SkipListSet(Collection<T> collection){
        for (T item: collection) {
            add(item);
        }
        int size = collection.size();
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
        if(arrList.isEmpty()){
            throw new NoSuchElementException();
        }else{
            return arrList.get(0).get(0).payload;
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
        if(arrList.isEmpty()){
            throw new NoSuchElementException();
        }else{
            ArrayList<SkipListSetItem> lastList = arrList.get(arrList.size() - 1);
            return lastList.get(lastList.size() - 1).payload;
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
        T element = (T) o;
        boolean returnCond = false;
        if(arrList.size() == 0){
            returnCond = false;
        }else{
            ArrayList<SkipListSetItem> skipList = arrList.get(0);

            //This skips through the lists
            for (ArrayList<SkipListSetItem> list : arrList) {
                if(list.get(0).compareTo(element) > -1){
                    skipList = list;
                }else{
                    break;
                }
            }

            //This scans through a list
            for (int i = 0; i < skipList.size(); i++) {
                if(element.compareTo(skipList.get(i).payload) == 0){
                    returnCond = true;
                    break;
                }
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
        return null;
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
        for (ArrayList<SkipListSetItem> list : arrList) {
            for (SkipListSetItem subList : list) {
                array[index] = subList.payload;
                index++;
            }
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
        SkipListSetItem addItem = new SkipListSetItem(obj, null, null);
        boolean returnCond = true;

        if(arrList.size() < 1){
            ArrayList<SkipListSetItem> arrSkipList = new ArrayList<>();
            arrSkipList.add(addItem);
            arrList.add(arrSkipList);
            returnCond = true;
            size++;
        }else{
            if(arrList.size() == 1){
                returnCond = add(addItem, 0);
            }else{
                int currIndex = 0;
                ArrayList<SkipListSetItem> list = arrList.get(currIndex);
                while(currIndex < arrList.size()-1 && list.get(0).compareTo(new SkipListSetItem(obj, null, null)) == -1){
                    currIndex++;
                    list = arrList.get(currIndex);
                }
                if(list.get(0).compareTo(new SkipListSetItem(obj, null, null)) == 1){
                    currIndex--;
                }
                returnCond = add(addItem, currIndex);
            }
        }
        return returnCond;
    }

    /**
     * @param addItem SkipListSetItem to add
     * @param index Index of arrayList inside arrList to add item
     * @return
     */
    private boolean add(SkipListSetItem addItem, int index) {
        boolean returnCond = true;
        ArrayList<SkipListSetItem> list = arrList.get(index);
        for (SkipListSetItem skipItem : list) {
            if(skipItem.compareTo(addItem) == 0){
                returnCond = false;
                break;
            }
        }
        if(returnCond == true){
            list.add(addItem);
            Collections.sort(list);
            if(list.size() >= maxLevel){
                rebalance(list, index);
            }
            returnCond = true;
            size++;
        }
        return returnCond;
    }


    /**
     * @param list Index of arrayList skipList that needs to be rebalanced
     * This method assumes that the list is at least size maxLevel/2
     */
    private void rebalance(ArrayList<SkipListSetItem> list, int index) {
        ArrayList<SkipListSetItem> newList = new ArrayList<>();
        for (int i = maxLevel/2; i < list.size(); i++) {
            newList.add(list.get(i));
        }
        for (int i = maxLevel/2; i < list.size(); i++) {
            list.remove(i);
        }
        list.remove(list.size()-1);
        arrList.add(index+1, newList);
    }

    //TODO Delete if not necessary
    private boolean sort(SkipListSetItem skipItem){
        return true;
    }

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
        SkipListSetItem skipItem = new SkipListSetItem(element, null, null);
        if(arrList.size() == 0) {
            returnCond = false;
        } else{
            ArrayList<SkipListSetItem> skipList = arrList.get(0);
            //This skips through the lists
            for (ArrayList<SkipListSetItem> list : arrList) {
                if(list.get(0).compareTo(skipItem) != 1){
                    skipList = list;
                }else{
                    break;
                }
            }

            //This scans through a list
            for (int i = 0; i < skipList.size(); i++) {
                if(skipItem.compareTo(skipList.get(i)) == 0){
                    skipList.remove(i);
                    if (skipList.size() == 0){
                        arrList.remove(skipList);
                    }
                    size--;
                    returnCond = true;
                    break;
                }
            }
        }

        return returnCond;
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
        arrList.clear();
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
        for (ArrayList<SkipListSetItem> list : arrList) {
            for (SkipListSetItem skipItem : list) {
                if(!c.contains(skipItem.payload)){
                    remove(skipItem.payload);
                    returnCond = true;
                }
            }
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
        if(a.length >= size){
            int index = 0;
            for (ArrayList<SkipListSetItem> list : arrList) {
                for (SkipListSetItem subList : list) {
                    a[index] = subList.payload;
                    index++;
                }
            }
            while(index < a.length){
                a[index] = null;
                index++;
            }
            return a;
        }else{
            return toArray();
        }
    }

    @Override
    public boolean equals(Object obj){
        boolean returnCond = true;
        SkipListSet<T> skipsListSet = (SkipListSet<T>) obj;
        for (ArrayList<SkipListSetItem> skipList : arrList) {
            for (SkipListSetItem skipItem : skipList) {
                if(!skipsListSet.contains(skipItem.payload)){
                    returnCond = false;
                }
            }
        }
        return returnCond;
    }

    private class SkipListSetIterator<T extends Comparable<T>> implements Iterator<T>{
        int indexList = 0;
        int indexElements = 0;

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
            if(arrList.size() > index+1){
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
            T returnElement;
            if(indexElements >= arrList.get(indexList).size()){
                indexElements = 0;
                indexList++;
                returnElement = (T) arrList.get(indexList).get(indexElements).payload;
            }else{
                returnElement = (T) arrList.get(indexList).get(indexElements).payload;
                indexElements++;
            }
            index++;
            return returnElement;
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
            Iterator.super.remove();
        }

    }

    private class SkipListSetItem implements Comparable{

        private T payload; //Holds the value

//        private SkipListSetItem nextNode; //Points to next node
//
//        private SkipListSetItem previousNode; //Points to previous node

        public SkipListSetItem(T payload, SkipListSetItem nextNode, SkipListSetItem previousNode){
            this.payload = payload;
//            this.nextNode = nextNode;
//            this.previousNode = previousNode;
        }

        public T getPayload() {
            return payload;
        }

//        public SkipListSetItem getNextNode() {
//            return nextNode;
//        }
//
//        public SkipListSetItem getPreviousNode() {
//            return previousNode;
//        }

        public void setPayload(T payload) {
            this.payload = payload;
        }

//        public void setNextNode(SkipListSetItem nextNode) {
//            this.nextNode = nextNode;
//        }
//
//        public void setPreviousNode(SkipListSetItem previousNode) {
//            this.previousNode = previousNode;
//        }

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
    }
}
