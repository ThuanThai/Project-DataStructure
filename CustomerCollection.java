package GroupAssignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CustomerCollection {
    final static int MAX = 113;
    CustomerNode[] table = new CustomerNode[MAX];

    static class CustomerNode {
        Customer cus;
        int height;
        CustomerNode left;
        CustomerNode right;

        public CustomerNode(Customer cus) {
            this.cus = cus;
            this.height = 1;
            this.left = null;
            this.right = null;
        }

    }
    public int getHeight(CustomerNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }
    private CustomerNode rightRotation(CustomerNode y) {
        CustomerNode x = y.left;
        CustomerNode sucX = x.right;

        x.right = y;
        y.left = sucX;

        //Updating height
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        return x;
    }
    private CustomerNode leftRotation(CustomerNode x) {
        CustomerNode y = x.right;
        CustomerNode sucY = y.left;

       y.left = x;
       x.right = sucY;

        //Updating height
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        return y;
    }
    private int getBalance(CustomerNode node) {
        if (node == null)
            return 0;
        return (getHeight(node.left) - getHeight(node.right));
    }
    public int hash(String id) {
       return Math.abs(id.hashCode()) % MAX;
    }
    public CustomerNode putInTree(CustomerNode node, Customer newCustomer) {
        if (node == null)
            return new CustomerNode(newCustomer);
        if (newCustomer.cusID.compareTo(node.cus.cusID) < 0)
            node.left = putInTree(node.left, newCustomer);
        else if (newCustomer.cusID.compareTo(node.cus.cusID) > 0)
            node.right = putInTree(node.right, newCustomer);
        else
            return node;

       node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getBalance(node);

        if (balance > 1 && newCustomer.cusID.compareTo(node.left.cus.cusID) < 0) {
            return rightRotation(node);
        }
        else if (balance > 1 && newCustomer.cusID.compareTo(node.left.cus.cusID) > 0) {
            node.left = leftRotation(node.left);
            node = rightRotation(node);
        }
        else if (balance < -1 && newCustomer.cusID.compareTo(node.right.cus.cusID) > 0) {
            return leftRotation(node);
        }
        else if (balance < -1 && newCustomer.cusID.compareTo(node.right.cus.cusID) < 0) {
           node.right = rightRotation(node.right);
           return leftRotation(node);
        }
        return node;
    }
    public void put(Customer newCustomer) {
        int idx = hash(newCustomer.cusID);
        if (table[idx] == null) {
            table[idx] = new CustomerNode(newCustomer);
            return;
        }
        putInTree(table[idx], newCustomer);
    }
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
    private CustomerNode searchInTree(CustomerNode node, String id) {
        if (id.equals(node.cus.cusID) || node == null)
            return node;
        if (id.compareTo(node.cus.cusID) < 0)
            return searchInTree(node.left, id);
        else
            return searchInTree(node.right, id);
    }
    public CustomerNode search(String id) {
        int idx = hash(id);
        if (table[idx].cus.cusID.equals(id))
            return table[idx];
        else
            return searchInTree(table[idx], id);
    }


    public static void main(String[] args) {
        String myFile = "customer.csv";
        CustomerCollection col = new CustomerCollection();
        col.readFile(myFile);
        System.out.println(col.search("NAO1931162").cus.fName);
        System.out.println(col.search("NAO1941162").cus.fName);
        System.out.println(col.search("NAO1921162").cus.fName);
    }
}
