public class List<T>{
    protected Node<T> head;
    protected Node<T> tail;
    protected Node<T> iterator; //used only for reset() and getNext()
    protected int numElements;

    public List(){
        this.head = null;
        this.tail = null;
        this.iterator = null;
        this.numElements = 0;
    }

    //returns true if the list has no elements
    public boolean isEmpty(){
        return head == null;
    }

    //prepares the iterator for sequential access
    public void reset(){
        iterator = head;
    }

    //returns current iterator value and then moves to the next node
    public T getNext(){
        if(iterator == null){
            return null;
        }
        T data = iterator.data;
        iterator = iterator.nextNode;
        return data;
    }

    //searches for a node with the given data and returns the node
    public Node<T> find(T data){
        Node<T> current = head;
        while(current != null){
            if(current.getData().equals(data)){
                return current;
            }
            current = current.getNextNode();
        }
        return null;
    }

    //returns true if a matching element is found
    public boolean contains(T data){
        return find(data) != null;
    }

    //adds a new node at the end of list
    public void add(T data){
        Node<T> newNode = new Node<>(data);
        if(isEmpty()){
            head = newNode;
            tail = newNode;
        }
        else{
            tail.setNextNode(newNode);
            tail = newNode;
        }
        numElements++;
    }

    //returns the stored version of a value if found
    public T get(T data) {
        Node<T> target = find(data);
        if(target == null){
            return null;
        }
        return target.getData();
    }

    //removes the first node that contains the target data
    public T remove(T data) throws ListUnderFlowException{
        if(isEmpty()){
            throw new ListUnderFlowException("List is empty");
        }

        //case 1: removing head
        if(head.getData().equals(data)){
            T removed = head.getData();
            head = head.getNextNode();

            //if head becomes null, list is now empty
            if(head == null){
                tail = null;
            }
            numElements--;
            return removed;
        }

        //case 2: removing in the middle or end
        Node<T> previous = head;
        Node<T> current = head.getNextNode();
        while(current != null && !current.getData().equals(data)){
            previous = current;
            current = current.getNextNode();
        }

        //case 3: not found
        if(current == null){
            return null;
        }
        T removed = current.getData();
        previous.setNextNode(current.getNextNode());
        if(current == tail){
            tail = previous;
        }
        numElements--;
        return removed;
    }

    //returns the number of elements stored
    public int size(){
        return numElements;
    }

    //returns a string listing all elements in order
    public String toString(){
        String toPrint = "List:\n";
        Node<T> current = head;
        while(current != null){
            toPrint += "\t" + current.getData() + "\n";
            current = current.getNextNode();
        }
        return toPrint  ;
    }
}

class Node<T>{
    T data;
    Node<T> nextNode;

    public Node(T data){
        this.data = data;
        this.nextNode = null;
    }

    //getter methods
    public T getData(){
        return data;
    }

    public Node<T> getNextNode(){
        return nextNode;
    }

    //setter methods
    public void setData(T data){
        this.data = data;
    }

    public void setNextNode(Node<T> nextNode){
        this.nextNode = nextNode;
    }
}

//custom exception when some operations are performed on empty list
class ListUnderFlowException extends Exception{
    public ListUnderFlowException(String message){
        super(message);
    }
}

