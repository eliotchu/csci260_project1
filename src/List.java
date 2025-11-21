import java.util.ArrayList;

public class List <T>{
    Node<T> head;
    Node<T> tail;
    int numElements;
    public List(){
        this.head = null;
        this.tail = null;
        this.numElements = 0;
    }

    //checks if List is empty
    public boolean isEmpty(){
        return head == null;
    }

    //linear search of data. returns true if found, else returns false if not found
    public boolean find(T data){
        if(!isEmpty()){
            Node<T> current = head;
            for(int i = 0; i < numElements; i++) {
                if (current.data.equals(data)) {
                    return true;
                }
                current = current.nextNode;
            }
        }
        return false;
    }

    //adds an element to the end of List
    public void add(T data){
        if(isEmpty()){
            head = new Node<T>(data);
            tail = head;

        }else{
            tail.nextNode = new Node<T>(data);
            tail = tail.nextNode;
        }
        numElements++;
    }

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

    //removes first node that contains target data, returns null if target data not found
    public T remove(T data) throws ListUnderFlowException{
        if(isEmpty()){
            throw new ListUnderFlowException("List is empty");
        }else{
            if(find(data)){
                Node<T> current = head;
                Node<T> previous = null;
                for(int i = 0; i < numElements - 1; i++){
                    if(!current.data.equals(data)){
                        previous = current;
                    }else{
                        break;
                    }
                    current = current.nextNode;
                }
                previous.nextNode = current.nextNode;

                numElements--;
                return current.data;
                }
            }
            return null;

        }

    //returns size of list
    public int size(){
        return this.numElements;
    }
}

class Node<T>{
    T data;
    Node<T> nextNode;
    public Node(T data){
        this.data = data;
        this.nextNode = null;
    }
}
class ListUnderFlowException extends Exception{
    public ListUnderFlowException(String message){
        super(message);
    }
}
