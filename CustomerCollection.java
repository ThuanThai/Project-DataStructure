package GroupAssignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomerCollection {
    final static int MAX = 113;
    CustomerNode[] table = new CustomerNode[MAX];


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
    public int hash(String id) {
       return Math.abs(id.hashCode()) % MAX;
    }
    public boolean put(Customer newCustomer) {
        int idx = hash(newCustomer.cusID);
        if (table[idx] == null) {
            table[idx] = new CustomerNode(newCustomer);
            return true;
        }
        if (newCustomer.cusID.compareTo(table[idx].getCus().cusID) < 0)
            table[idx].setLeft(new CustomerNode(newCustomer));
        else if (newCustomer.cusID.compareTo(table[idx].getCus().cusID) > 0)
            table[idx].setRight(new CustomerNode(newCustomer));
        else
           return false;

        table[idx].setHeight(1 + Math.max(getHeight(table[idx].getLeft()), getHeight(table[idx].getRight())));
        int balance = getBalance(table[idx]);

        if (balance > 1 && newCustomer.cusID.compareTo(table[idx].getLeft().getCus().cusID) < 0) {
            table[idx] = rightRotation(table[idx]);
        }
        else if (balance > 1 && newCustomer.cusID.compareTo(table[idx].getLeft().getCus().cusID) > 0) {
            table[idx].setLeft(leftRotation(table[idx].getLeft()));
            table[idx] = rightRotation(table[idx]);
        }
        else if (balance < -1 && newCustomer.cusID.compareTo(table[idx].getLeft().getCus().cusID) > 0) {
            table[idx] = leftRotation(table[idx]);
        }
        else if (balance < -1 && newCustomer.cusID.compareTo(table[idx].getLeft().getCus().cusID) < 0) {
            table[idx].setRight(rightRotation(table[idx].getRight()));
            table[idx] = leftRotation(table[idx]);
        }
        return true;
    }
//    private CustomerNode insertTree(CustomerNode node, Customer newCustomer) {
//        if (newCustomer.cusID.compareTo(node.cus.cusID) < 0)
//            node.left = new CustomerNode(newCustomer);
//        else if (newCustomer.cusID.compareTo(node.cus.cusID) > 0)
//            node.right = new CustomerNode(newCustomer);
//        else
//            return node;
//        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
//        int balance = getBalance(node);
//
//        if (balance > 1 && newCustomer.cusID.compareTo(node.left.cus.cusID) < 0) {
//            return rightRotation(node);
//        }
//        else if (balance > 1 && newCustomer.cusID.compareTo(node.left.cus.cusID) > 0) {
//            node.left = leftRotation(node.left);
//            return rightRotation(node);
//        }
//        else if (balance < -1 && newCustomer.cusID.compareTo(node.left.cus.cusID) > 0) {
//            return leftRotation(node);
//        }
//        else if (balance < -1 && newCustomer.cusID.compareTo(node.left.cus.cusID) < 0) {
//            node.right = rightRotation(node.right);
//            return leftRotation(node);
//        }
//        return node;
//    }
<<<<<<< Updated upstream

=======
    public void readFile(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = "";
            while ((line = reader.readLine()) != null) {
                Customer newCustomer = new Customer();
                String[] row = line.split(",");
                newCustomer.cusID = row[0];
                newCustomer.fName = row[1];
                newCustomer.lName = row[2];
                newCustomer.phone = row[3];
                put(newCustomer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String myFile = "customer.csv";
        CustomerCollection col = new CustomerCollection();
        long start = System.currentTimeMillis();
        col.readFile(myFile);
        System.out.println(System.currentTimeMillis() - start);
    }
>>>>>>> Stashed changes
}
