class MyCircularQueue {
    private int[] data;
    private int head;
    private int tail;
    private int size;
    private int cap;

    public MyCircularQueue(int k) {
        data = new int[k];
        head = 0;
        tail = -1;
        size = 0;
        cap = k;
    }
    
    public boolean enQueue(int value) {
        if(isFull()){
            return false;
        }
        tail = (tail + 1) % cap;
        data[tail] = value;
        size++;
        return true;
    }
    
    public boolean deQueue() {
        if(isEmpty()){
            return false;
        }
        head = (head + 1) % cap;
        size--;
        return true;
    }
    
    public int Front() {
        if(isEmpty()) return -1;
        return data[head];
    }
    
    public int Rear() {
        if(isEmpty()) return -1;
        return data[tail];
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public boolean isFull() {
        return size == cap;
    }
}

/**
 * Your MyCircularQueue object will be instantiated and called as such:
 * MyCircularQueue obj = new MyCircularQueue(k);
 * boolean param_1 = obj.enQueue(value);
 * boolean param_2 = obj.deQueue();
 * int param_3 = obj.Front();
 * int param_4 = obj.Rear();
 * boolean param_5 = obj.isEmpty();
 * boolean param_6 = obj.isFull();
 */