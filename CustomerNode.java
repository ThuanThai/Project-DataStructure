package GroupAssignment;

public class CustomerNode {
    private Customer cus;
    private int height;
    private CustomerNode left;
    private CustomerNode right;

    public Customer getCus() {
        return cus;
    }
    public CustomerNode getLeft() {
        return left;
    }
    public CustomerNode getRight() {
        return right;
    }
    public int getHeight() {
        return height;
    }

    public void setCus(Customer cus) {
        this.cus = cus;
    }

    public void setLeft(CustomerNode left) {
        this.left = left;
    }

    public void setRight(CustomerNode right) {
        this.right = right;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public CustomerNode(Customer cus) {
        this.cus = cus;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}
