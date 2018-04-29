public class OpenList 
    {
    
    public class NodeOL
        {
        private State value;
        private float priority;
        
        public NodeOL(State inputValue, float inputPriority)
            {
            this.value = inputValue;
            this.priority = inputPriority;
            }
        
        public State getState()
            {
            return this.value;
            }
        
        public float getPriority()
            {
            return this.priority;
            }
        
        public String toString()
            {
            String result = "";
            result += "State:\n" + value +
                      "Priority: " + priority + "\n";
            return result;
            }
        
        }
    
    private static final int HEAPSIZE = 10;
    private NodeOL[] Heap;
    private int maxsize;
    private int size;
    private int startIndex;
    
    public OpenList()
        {
        this.maxsize = HEAPSIZE;
        this.Heap = new NodeOL[maxsize];
        this.size = 0 ;
        this.Heap[0] = new NodeOL(null, Float.MIN_VALUE);
        this.startIndex = 0;
        }
    
    public OpenList(int inputMax)
        {
        this.maxsize = inputMax;
        this.Heap = new NodeOL[maxsize];
        this.size = 0 ;
        this.Heap[0] = new NodeOL(null, Float.MIN_VALUE);
        }
    
    private int leftchild(int pos) 
        {
        return 2*pos;
        }
    
    private int rightchild(int pos) 
        {
        return 2*pos + 1;
        }

    private int parent(int pos) 
        {
        return  pos / 2;
        }
    
    private boolean isleaf(int pos) 
        {
        return ((pos > size/2) && (pos <= size));
        }
    
    private void swap(int pos1, int pos2) 
        {
        NodeOL temp = Heap[pos1];
        Heap[pos1] = Heap[pos2];
        Heap[pos2] = temp;
        }
    
    public void insert(State elem, float priority) 
        {
        size++;
        int current = size;
        
        if(size >= Heap.length)
            resize();
        
        Heap[size] = new NodeOL(elem, priority);
        
        if(size > 1)
            {
            while(Heap[current].priority < Heap[parent(current)].priority && parent(current) != 0)
                {
                swap(current, parent(current));
                current = parent(current);
                }
            }
        }
    
    private void resize()
        {
        maxsize *= 2;
        NodeOL[] newHeap = new NodeOL[maxsize];
        for(int i = 0; i < Heap.length; i++)
            newHeap[i] = Heap[i];
        Heap = newHeap;
        }
    
    public void print() 
        {
        int i;
        for (i=1; i<=size;i++)
            System.out.print(Heap[i] + " ");
        System.out.println();
        }
    
    public NodeOL removemin() 
        {
        NodeOL temp = getMin();
        swap(1,size);
        size--;
        if (size != 0)
            pushdown(1);
        return Heap[size+1];
        }
    
    public boolean find(State val)
        {
        for(int i = 1; i < size; i++)
            {
            System.out.println(Heap[i]);
            if(Heap[i].equals(val))
                return true;
            }
        return false;
        }

   private void pushdown(int position) 
        {
        int smallestchild;
        while (!isleaf(position)) 
            {
            smallestchild = leftchild(position);
            if ((smallestchild < size) && (Heap[smallestchild].priority > Heap[smallestchild+1].priority))
                smallestchild = smallestchild + 1;
            if (Heap[position].priority <= Heap[smallestchild].priority) 
                return;
            swap(position,smallestchild);
            position = smallestchild;
            }
        }
   
    public boolean isEmpty()
        {
        return this.size == 0;
        }
    
    public NodeOL getMin()
        {
        return this.Heap[1];
        }
    
    
    }



