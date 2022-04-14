package GroupAssignment;

public class CustomerTree {
    CustomerNode head;
    public CustomerTree(){
        this.head = null;
    }
    public int getHeight(CustomerNode node) {
        if (node == null) {
            return 0;
        }
        return node.getHeight();
    }
    private CustomerNode rightRotation(CustomerNode y) {
        CustomerNode x = y.getLeft();
        CustomerNode sucX = x.getRight();

        x.setRight(y);
        y.setLeft(sucX);

        //Updating height
        x.setHeight(1 + Math.max(getHeight(x.getLeft()), getHeight(x.getRight())) );
        y.setHeight(1 + Math.max(getHeight(y.getLeft()), getHeight(y.getRight())));
        return x;
    }
    private CustomerNode leftRotation(CustomerNode x) {
        CustomerNode y = x.getRight();
        CustomerNode sucY = y.getLeft();

        y.setLeft(x);
        x.setRight(sucY);

        //Updating height
        x.setHeight(1 + Math.max(getHeight(x.getLeft()), getHeight(x.getRight())));
        y.setHeight(1 + Math.max(getHeight(y.getLeft()), getHeight(y.getRight())));
        return y;
    }
    private int getBalance(CustomerNode node) {
        if (node == null)
            return 0;
        return (getHeight(node.getLeft()) - getHeight(node.getRight()));
    }

    public CustomerNode putInTree(CustomerNode node, Customer newCustomer) {
        if (node == null) {
            return new CustomerNode(newCustomer);
        }
        if (newCustomer.cusID.compareTo(node.getCus().cusID) < 0)
            node.setLeft(putInTree(node.getLeft(), newCustomer));
        else if (newCustomer.cusID.compareTo(node.getCus().cusID) > 0)
            node.setRight(putInTree(node.getRight(), newCustomer));
        else
            return node;

        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
        int balance = getBalance(node);

        if (balance > 1 && newCustomer.cusID.compareTo(node.getLeft().getCus().cusID) < 0) {
            return rightRotation(node);
        }
        if (balance > 1 && newCustomer.cusID.compareTo(node.getLeft().getCus().cusID) > 0) {
            node.setLeft(leftRotation(node.getLeft()));
            return rightRotation(node);
        }
        if (balance < -1 && newCustomer.cusID.compareTo(node.getLeft().getCus().cusID) > 0) {
            return leftRotation(node);
        }
        if (balance < -1 && newCustomer.cusID.compareTo(node.getLeft().getCus().cusID) < 0) {
            node.setRight(rightRotation(node.getRight()));
            return leftRotation(node);
        }
        return node;
    }

    public CustomerNode searchInTree(CustomerNode node, String id) {
        System.out.println("step");
        if(node == null || node.getCus().cusID.equals(id))
            return node;
        if(id.compareTo(node.getCus().cusID) < 0)
            return searchInTree(node.getLeft(), id);
        else
            return searchInTree(node.getRight(), id);
    }
}
