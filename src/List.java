public class List <T>{
    protected Node<T> head;
    protected Node<T> tail;
    protected Node<T> current;
    protected int numElements;
    protected boolean found;
    public List(){
        this.head = null;
        this.tail = null;
        this.current = null;
        this.numElements = 0;
    }

    //checks if List is empty
    public boolean isEmpty(){
        return head == null;
    }

    //linear search of specified data. returns node of where data is found, returns null if data not found
    public Node<T> find(T data){
        if(!isEmpty()){
            reset();
            while(current != null){
                if(current.getData().equals(data)){
                    found = true;
                    return current;
                }
                current = current.getNextNode();
            }
        }
        found = false;
        return null;
    }

    //checks if list contains specified data. returns true if found, else returns false if not found
    public boolean contains(T data){
        find(data);
        return found;
    }

    //adds an element to the end of List
    public void add(T data){
        if(isEmpty()){
            head = new Node<T>(data);
            tail = head;

        }else{
            tail.setNextNode(new Node<T>(data));
            tail = tail.getNextNode();
        }
        numElements++;
    }

    public T get(T data){
        return find(data).getData();
    }

    /*
    //removes element at the end of List
    public T remove() throws ListUnderFlowException{
        if(isEmpty()){
            throw new ListUnderFlowException("List is empty");
        }else{
            Node<T> current = head;
            Node<T> previous = null;
            for(int i = 0; i < numElements; i++){
                if(current.nextNode != null){
                    previous = current;
                }else{
                    break;
                }
                current = current.nextNode;
            }
            previous.nextNode = null;
            tail = previous;
            numElements--;
            return current.data;
        }
    }
    */


    //removes first node that contains target data, returns null if target data not found
    public T remove(T data) throws ListUnderFlowException{
        int count = 0;

        if(isEmpty()){
            throw new ListUnderFlowException("List is empty");
        }

        //return null if data not found in list, otherwise, search for element and delete
        if(head.getData().equals(data)){
            T removedData = head.getData();
            head = head.getNextNode();
            if(head == null){
                tail = null;
            }
            numElements--;
            return removedData;
        }else{
            reset();
            //check if next node is the tail or has the matching data, sets current to the node before the node with the matching data after loop condition is met
            while(current.getNextNode() != null && !current.getNextNode().data.equals(data)){
                current = current.getNextNode();
                count++;
            }
            if(count == numElements){
                return null;
            }

            Node<T> target = current.getNextNode();
            T removedData = target.getData();
            //if target (node after current and node with the matching data) is the last node (tail), set the new tail as current (node before matching data)
            //this deletes the last node
            if(target == tail){
                tail = current;
            }
            //if target is not the tail delete the node with matching data
            current.setNextNode(target.getNextNode());
            numElements--;
            return removedData;
        }

    }

    //returns size of list
    public int size(){
        return this.numElements;
    }

    public void reset(){
        this.current = head;
    }

    public T getNext(){
        T next = current.data;
        if(current.getNextNode() == null){
            current = head;
        }else{
            current = current.getNextNode();
        }
        return current.getData();
    }

//    public String toString(){
//        String[] toPrint = new String[numElements];
//        reset();
//        for(int i = 0; i < numElements; i++){
//            toPrint[i] = "{currentNode: " + current + ", currentData: " + current.data + ", nextNode: " + current.nextNode + "}\n\t";
//            if(current.nextNode != null){
//                current = current.nextNode;
//            }
//        }
//        return "[\n\t" + String.join(", ", toPrint) + "\n]";
//    }

    public String toString(){
        String toPrint = "List: \n";
        reset();
        while(current != null){
            toPrint += "\t" + current.getData() + "\n";
            current = current.getNextNode();
        }
        return toPrint;
    }
}

class Node<T>{
    T data;
    Node<T> nextNode;
    public Node(T data){
        this.data = data;
        this.nextNode = null;
    }

    public T getData(){
        return this.data;
    }

    public Node<T> getNextNode(){
        return this.nextNode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setNextNode(Node<T> nextNode) {
        this.nextNode = nextNode;
    }
}
class ListUnderFlowException extends Exception{
    public ListUnderFlowException(String message){
        super(message);
    }
}
