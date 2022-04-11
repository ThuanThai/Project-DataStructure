package GroupAssignment;

class RBCustomerNode {
    RBCustomerNode leftChild, rightChild;
    Customer customer;
    int color;

    //constructor to set the value of a node having no left and right child
    public RBCustomerNode(Customer customer)
    {
        this.customer = customer;
        this.leftChild = null;
        this.rightChild = null;
    }

    public RBCustomerNode(Customer customer, RBCustomerNode left, RBCustomerNode right) {
        this.customer = customer;
        this.leftChild = left;
        this.rightChild = right;
        this.color = 1;
    }

    public RBCustomerNode(){};
}
public class RBTree {
    private static RBCustomerNode nullNode;   //define null node
    private RBCustomerNode current;   //define current node
    private RBCustomerNode parent;    //define parent node
    private RBCustomerNode header;   // define header node
    private RBCustomerNode grand; //define grand node
    private RBCustomerNode great; //define great node

    static final int RED   = 0;
    static final int BLACK = 1;

    static
    {
        nullNode = new RBCustomerNode(null);
        nullNode.leftChild = nullNode;
        nullNode.rightChild = nullNode;
    }

    public RBTree()
    {
        this.header = new RBCustomerNode();
        this.header.leftChild = nullNode;
        this.header.rightChild = nullNode;
    }

    public void insertNewNode(Customer newCustomer) {
        if(header.customer == null) {
            header = new RBCustomerNode(newCustomer, nullNode, nullNode);
            return;
        }
        current = parent = grand = header;      //set header value to current, parent, and grand node
        nullNode.customer = newCustomer;          //set newElement to the element of the null node
        //repeat statements until the element of the current node will not equal to the value of the newElement
        while (current.customer != newCustomer)
        {
            great = grand;
            grand = parent;
            parent = current;
            //if the value of the newElement is lesser than the current node element, the current node will point to the current left child else point to the current right child.
            current = newCustomer.getCode() < current.customer.getCode() ? current.leftChild : current.rightChild;
            // Check whether both the children are RED or NOT. If both the children are RED change them by using handleColors() method
            if (current.leftChild.color == RED && current.rightChild.color == RED)
                handleColors(newCustomer);
        }

        // insertion of the new node will be fail if will already present in the tree
        if (current != nullNode)
            return;

        current = new RBCustomerNode(newCustomer, nullNode, nullNode);

        //connect the current node with the parent
        if (newCustomer.getCode() < parent.customer.getCode())
            parent.leftChild = current;
        else
            parent.rightChild = current;
        handleColors(newCustomer);
    }

    private void handleColors(Customer newCustomer)
    {
        // flip the colors of the node
        current.color = RED;    //make current node RED
        current.leftChild.color = BLACK;    //make leftChild BLACK
        current.rightChild.color = BLACK;   //make rightChild BLACK

        //check the color of the parent node
        if (parent.color == RED)
        {
            // perform rotation in case when the color of parent node is RED
            grand.color = RED;

            if (newCustomer.getCode() < grand.customer.getCode()
                    && grand.customer != newCustomer
                    && newCustomer.getCode() < parent.customer.getCode())
                parent = performRotation( newCustomer, grand );  // Start dbl rotate
            current = performRotation(newCustomer, great );
            current.color = BLACK;
        }
        // change the color of the root node with BLACK
        header.rightChild.color = BLACK;
    }

    private RBCustomerNode performRotation(Customer newCustomer, RBCustomerNode parent) {
        //check whether the value of the newElement is lesser than the element of the parent node or not
        if(newCustomer.getCode() < parent.customer.getCode())
            //if true, perform the rotation with the left child and right child based on condition and set return value to the left child of the parent node
            return parent.leftChild = newCustomer.getCode()< parent.leftChild.customer.getCode() ? rotationWithLeftChild(parent.leftChild) : rotationWithRightChild(parent.leftChild) ;
        else
            //if false, perform the rotation with the left child and right child based on condition and set return value to the right child of the parent node
            return parent.rightChild = newCustomer.getCode() < parent.rightChild.customer.getCode()? rotationWithLeftChild(parent.rightChild) : rotationWithRightChild(parent.rightChild);
    }

    //create rotationWithLeftChild() method  for rotating binary tree node with left child
    private RBCustomerNode rotationWithLeftChild(RBCustomerNode node2)
    {
        RBCustomerNode node1 = node2.leftChild;
        node2.leftChild = node1.rightChild;
        node1.rightChild = node2;
        return node1;
    }

    // create rotationWithRightChild() method for rotating binary tree node with right child
    private RBCustomerNode rotationWithRightChild(RBCustomerNode node1)
    {
        RBCustomerNode node2 = node1.rightChild;
        node1.rightChild = node2.leftChild;
        node2.leftChild = node1.leftChild;
        return node2;
    }

    // create nodesInTree() method for getting total number of nodes in a tree
    public int nodesInTree()
    {
        return nodesInTree(header);
    }
    private int nodesInTree(RBCustomerNode node)
    {
        if (node == nullNode)
            return 0;
        else
        {
            int size = 1;
            size = size + nodesInTree(node.leftChild);
            size = size + nodesInTree(node.rightChild);
            return size;
        }
    }

    public void print() {
        if(header.customer != null) {
            System.out.println(header.customer.cusID + " " + header.customer.fName + " " + header.customer.lName + " " + header.customer.phone);
        }
    }

    public static void main(String[] args) {
        Customer anh = new Customer("ICP3081160","Antonietta","Philoo" ,"13472448036");
        Customer anh1 = new Customer("ICP30811602","Antonietta","Philoo" ,"13472448036");
        Customer anh2 = new Customer("ICP30832602","Antonietta","Philoo" ,"13472448036");
        Customer anh3 = new Customer("ICP3081122","Antonietta","Philoo" ,"13472448036");
        RBTree testTree = new RBTree();
        testTree.insertNewNode(anh1);
        testTree.insertNewNode(anh2);
        testTree.insertNewNode(anh3);
        System.out.println(testTree.header.leftChild.customer.cusID);
    }
}
