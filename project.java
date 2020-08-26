
// main method is inside AVLTree class
// Run instruction : - javac project.java -Xlint
                    // java AVLTree




import java.util.*;

// this class is used to implement pair in queue
class Entry{

    public int key;
    public int value;

    public Entry(int key, int value) {
        this.key = key;
        this.value = value;
    }

    
}

// class to override comparator method for class Entry in priority queue
class EntryComparator  implements Comparator<Entry>{
    public int compare(Entry s1, Entry s2) { 
                if (s1.value > s2.value) 
                    return 1; 
                else if (s1.value < s2.value) 
                    return -1;          
                else if(s1.key > s2.key)
                    return 1;
                else
                    return-1;
            }
} 


// Node class is structure of process
class Node 
{ 
    // here key is process_id
    // vertual_time hold the value of time which the process has been executed in CPU till now
    int key,vertual_time, height;

    // Tree left and right child  
    Node left, right; 

    Node(int d, int v) 
    { 
        key = d; 
        vertual_time=v;
        height = 1; 
    } 
} 

class AVLTree 
{ 
    Node root; 

    // A function to get height of the tree 
    int height(Node N) 
    { 
        if (N == null) 
            return 0; 
        return N.height; 
    } 

    // A function to get maximum of two integers 
    int max(int a, int b) 
    { 
        return (a > b) ? a : b; 
    } 

    // A function to right rotate subtree rooted with y 
   
    Node rightRotate(Node y) 
    { 
        Node x = y.left; 
        Node T2 = x.right; 

        // Perform rotation 
        x.right = y; 
        y.left = T2; 

        // Update heights 
        y.height = max(height(y.left), height(y.right)) + 1; 
        x.height = max(height(x.left), height(x.right)) + 1; 

        // Return new root 
        return x; 
    } 

    // A function to left rotate subtree rooted with x 
    Node leftRotate(Node x) 
    { 
        Node y = x.right; 
        Node T2 = y.left; 

        // Perform rotation 
        y.left = x; 
        x.right = T2; 

        // Update heights 
        x.height = max(height(x.left), height(x.right)) + 1; 
        y.height = max(height(y.left), height(y.right)) + 1; 

        // Return new root 
        return y; 
    } 

    // Get Balance factor of node N 
    int getBalance(Node N) 
    { 
        if (N == null) 
            return 0; 
        return height(N.left) - height(N.right); 
    } 

    Node insert(Node node, int key, int vertual_time) 
    { 
         // normal BST rotation 
        if (node == null) 
            return (new Node(key,vertual_time)); 

        if (vertual_time <= node.vertual_time) 
            node.left = insert(node.left, key, vertual_time); 
        else if (vertual_time > node.vertual_time) 
            node.right = insert(node.right, key, vertual_time); 
        else // Equal keys not allowed 
            return node; 

        // Updating height of this ancestor node 
        node.height = 1 + max(height(node.left), 
                            height(node.right)); 

       
        // node to check whether this node became 
        // unbalanced 
        int balance = getBalance(node); 

        // If this node becomes unbalanced, then 
        // there are 4 cases Left Left Case 
        if (balance > 1 && vertual_time <= node.left.vertual_time) 
            return rightRotate(node); 

        // Right Right Case 
        if (balance < -1 && vertual_time > node.right.vertual_time) 
            return leftRotate(node); 

        // Left Right Case 
        if (balance > 1 && vertual_time > node.left.vertual_time) 
        { 
            node.left = leftRotate(node.left); 
            return rightRotate(node); 
        } 

        // Right Left Case 
        if (balance < -1 && vertual_time <= node.right.vertual_time) 
        { 
            node.right = rightRotate(node.right); 
            return leftRotate(node); 
        } 

         // return the (unchanged) node pointer 
        return node; 
    } 

    // get minimum process
    Node minValueNode(Node node) 
    { 
        Node current = node; 

         // loop down to find the leftmost leaf 
        while (current.left != null) 
        current = current.left; 

        return current; 
    } 

    Node deleteNode(Node root, int key,int vertual_time) 
    { 
        
        if (root == null) 
            return root; 

        // If the key to be deleted is smaller than 
        // the root's key, then it lies in left subtree 
        if (vertual_time <= root.vertual_time && root.key != key) 
            root.left = deleteNode(root.left, key, vertual_time); 

        // If the key to be deleted is greater than the 
        // root's key, then it lies in right subtree 
        else if (vertual_time > root.vertual_time) 
            root.right = deleteNode(root.right, key, vertual_time); 

        // if key is same as root's key, then this is the node 
        // to be deleted 
        else
        { 

            // node with only one child or no child 
            if ((root.left == null) || (root.right == null)) 
            { 
                Node temp = null; 
                if (temp == root.left) 
                    temp = root.right; 
                else
                    temp = root.left; 

                // No child case 
                if (temp == null) 
                { 
                    temp = root; 
                    root = null; 
                } 
                else // One child case 
                    root = temp; // Copy the contents of 
                                // the non-empty child 
            } 
            else
            { 

                // node with two children: Get the inorder 
                // successor (smallest in the right subtree) 
                Node temp = minValueNode(root.right); 

                // Copy the inorder successor's data to this node 
                root.key = temp.key; 

                // Delete the inorder successor 
                root.right = deleteNode(root.right, temp.key, temp.vertual_time); 
            } 
        } 

        // If the tree had only one node then return 
        if (root == null) 
            return root; 

        //get height
        root.height = max(height(root.left), height(root.right)) + 1; 

        // finding balance factor
        int balance = getBalance(root); 

        // If this node becomes unbalanced, then there are 4 cases 
        // Left Left Case 
        if (balance > 1 && getBalance(root.left) >= 0) 
            return rightRotate(root); 

        // Left Right Case 
        if (balance > 1 && getBalance(root.left) < 0) 
        { 
            root.left = leftRotate(root.left); 
            return rightRotate(root); 
        } 

        // Right Right Case 
        if (balance < -1 && getBalance(root.right) <= 0) 
            return leftRotate(root); 

        // Right Left Case 
        if (balance < -1 && getBalance(root.right) > 0) 
        { 
            root.right = rightRotate(root.right); 
            return leftRotate(root); 
        } 

        return root; 
    } 

    // A function to print Inorder traversal of 
    // the tree.
    void InOrder(Node node) 
    { 
        if (node != null) 
        { 
            
            InOrder(node.left); 
            System.out.print(node.key + "|" + node.vertual_time+"||"); 
            InOrder(node.right); 
        } 
    } 
    void preOrder(Node node) 
    { 
        if (node != null) 
        { 
            System.out.print(node.key + "|" + node.vertual_time+"|| ");
            preOrder(node.left); 
            preOrder(node.right); 
        } 
    } 

    static void print_stats(int[] bt, int[] ct, int[] at )
    {
        System.out.println("\nProcess scheduling statistics ...");
        System.out.println("pid | Arrival | burstTime | complition_time | TurnAround_Time | Waiting_Time |");
        System.out.println("------------------------------------------------------------------------------");
        
        double att=0,awt=0;
        for(int i=0;i<bt.length;i++){
            System.out.println(String.format("%c   |%5d    |%6d     |%10d       |%9d        |%7d       |",(char)i+'A',at[i],bt[i],ct[i],ct[i]-at[i],ct[i]-at[i]-bt[i]));
            att+=ct[i]-at[i];
            awt+=ct[i]-at[i]-bt[i];
        }
        int n = bt.length;
        att=att/n;
        awt=awt/n;

        System.out.println("Average TurnAround_Time : "+att);
        System.out.println("Average Waiting_Time : "+awt+"\n");



    }

    public static void main(String[] args) 
    { 
        
        Scanner in = new Scanner(System.in);

        while(true){

            String s;
            int max=50,i,j,n,limit=0;
            int time_quantom=1;

            //Enter target_latency
            System.out.println("Enter the target latency : ");
            int target_latency = in.nextInt();


            // Enter number of process
            System.out.println("Enter the number of process  :");
            n = in.nextInt();

            time_quantom = target_latency/n;

            if( time_quantom == 0 ){
                time_quantom=1;
            }

            int[] burstTime = new int[n];
            int[] priority = new int[n];
            int[] arrival = new int[n];
            int complition_time[] = new int[n];
            int bt[] = new int[n];
            // kind of hashing to track arrival time of process
            LinkedList<Integer> p[] = new LinkedList[100];
            // intialising
            for(i=0;i<100;i++){
                p[i] = new LinkedList<>();
            }
            

            // taking input
            for(i=0;i<n;i++){
                System.out.println("Enter the details of process "+(i+1)+" : ");
                
                System.out.println("Arrival Time : ");
                int a = in.nextInt();
                p[a].add(i);
                arrival[i]=a;
                if(limit<a)limit=a;

                System.out.println("Burst Time  : ");
                int b = in.nextInt();
                burstTime[i] = b;
                bt[i]=b;

                System.out.println("priority : ");
                int pri = in.nextInt();
                priority[i] = pri;
            }

            
            AVLTree tree = new AVLTree();

            System.out.println("1. with balanced BST\n2. Min Heap(priority queue)\nEnter your choice ...");
            int l = in.nextInt();
            System.out.println("scheduling Timeline : ");
            System.out.println("Time  |||  Process ");
            if(l==1)      
            {
                // loop that shows each second 
                for(i=0;i<max;i++){
                    
                    // adding process that arrive at given time.
                    for(j=0;j<p[i].size();j++){
        
                        tree.root = tree.insert(tree.root,p[i].get(j),0);
                    }
                    
                    //CPU is ideal if there is no process in tree currently
                    if(tree.root==null)
                    {
                        if(i>limit)break;
                        s=String.format("%2ds   |||  ideal",i);
                        System.out.println(s);

                                try{
                                    Thread.sleep(1000);
                                }
                                catch(Exception e){}
                    }

                    // take a process from tree from which has minimum vertual_time
                    // that process will be at leftest node of tree
                    else{

                        //getting min node
                        Node curr = tree.minValueNode(tree.root);

                        // Delete that node form tree
                        tree.root = tree.deleteNode(tree.root, curr.key, curr.vertual_time);
                        char c = (char)('A'+curr.key);
                        int counter=time_quantom;

                        // loop for time which process should execute before getting prempt
                        while(counter!=0){

                            counter--;
                            burstTime[curr.key]-=1;

                            s=String.format("%2ds   |||  %c",i,c);
                            System.out.println(s);

                                try{
                                    Thread.sleep(1000);
                                }
                                catch(Exception e){}

                            i++;
                            if(burstTime[curr.key]<=0){
                                break;
                            }
                            
                            // adding process that arrive at given time(i).
                            if( counter != 0 ){
                                for(j=0;j<p[i].size();j++){  
                                    tree.root = tree.insert(tree.root,p[i].get(j),0);
                                }
                            }
                        }
                        i--;


                        // Now only add that process if its execution is not done yet
                        if(burstTime[curr.key]>0){
                            tree.root = tree.insert(tree.root, curr.key, curr.vertual_time+time_quantom*priority[curr.key]);
                        } 

                        // Else note the comletion time of process
                        else{
                            complition_time[curr.key]=i;
                        }                       
                    } 
                } 
            }
            else if(l==2){

                // this is priority queue which is internally work by min heap
                PriorityQueue<Entry> q = new PriorityQueue<>(100, new EntryComparator());
                for(i=0;i<max;i++){
                    
                    // adding process that arrive at given time(i).
                    for(j=0;j<p[i].size();j++){
                        q.add(new Entry(p[i].get(j),0));
                    }

                    //CPU is ideal if there is no process in queue currently
                    if(q.size()==0){

                        if(i>limit)break;
                        s=String.format("%2ds   |||  ideal",i);
                        System.out.println(s);

                                try{
                                    Thread.sleep(1000);
                                }
                                catch(Exception e){}
                    } 

                    // take a process from tree from which has minimum vertual_time
                    // that process will be first which is taken by poll function
                    else{


                        Entry e = q.poll();
                        char c = (char)('A'+e.key);
                        int counter=time_quantom;

                        // loop for time which process should execute before getting prempt
                        while(counter!=0){ 
                            
                            counter--;
                            burstTime[e.key]-=1;

                            s=String.format("%2ds   |||  %c",i,c);
                            System.out.println(s);
                            
                                try{
                                    Thread.sleep(1000);
                                }
                                catch(Exception y){}

                            i++;
                            if(burstTime[e.key]<=0){
                                break;
                            }


                            // adding process that arrive at given time(i).
                            if( counter != 0 ){
                                for(j=0;j<p[i].size();j++){
                                    q.add(new Entry(p[i].get(j),0));
                                }
                            }
                        }
                        i--;

                        // Now only add that process into the queue if its execution is not done yet
                        if(burstTime[e.key]>0){
                            q.add(new Entry(e.key,e.value+time_quantom*priority[e.key]));
                        }

                        // Else note the comletion time of process
                        else{
                            complition_time[e.key]=i;
                        }
                        
                    }
                       
         
                }   

            }
            else{
                System.out.println("Wrong Entry!!\nEnter again :)");
            }

                print_stats(bt,complition_time,arrival);
                System.out.println("Do you want to continue [y/n]?");
                char c = in.next().charAt(0);
                if(c=='n'){
                    break;
                }
            }
  
    } 
} 
