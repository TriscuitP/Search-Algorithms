import java.util.ArrayList;

public class ClosedList 
    {
    
    public class NodeCL
        {
        private State key;
        private NodeCL next;
        
        public NodeCL(State inputKey)
            {
            this.key = inputKey;
            this.next = null;
            }
        
        public State getKey()
            {
            return this.key;
            }
        
        public NodeCL getNext()
            {
            return this.next;
            }
        
        public void setNext(NodeCL val)
            {
            this.next = val;
            }
        
        }
    
    private static final int HASHSIZE = 50;
    private NodeCL[] table;
    private int size;
    private int numOfNodes;
    
    public ClosedList()
        {
        this.table = new NodeCL[HASHSIZE];
        this.size = HASHSIZE;
        this.numOfNodes = 0;
        }
    
    public void add(State val, int index)
        {
        numOfNodes++;
        int hashVal = hash(index);
        if(table[hashVal] == null)
            table[hashVal] = new NodeCL(val);
        else
            {
            NodeCL root = table[hashVal];
            while(root.getNext() != null)
                {
                root = root.getNext();
                }
            NodeCL next = new NodeCL(val);
            root.setNext(next);
            }
        }
    
    public boolean find(State val)
        {
        int h = val.hashCode();
        int hVal = hash(h);
        
        for(NodeCL curr = table[hVal]; curr != null; curr = curr.getNext())
            {
            if(curr.getKey().equals(val))
                return true;
            }
        
        return false;
        }
    
    
    private int hash(int index)
        {
        int hashVal = index;
        hashVal %= this.table.length;
        if (hashVal < 0)
            hashVal += table.length;
        return hashVal;
        }
    
    public boolean isEmpty()
        {
        return this.size == 0;
        }
    

    }




