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
    public boolean put(Customer newCustomer) {
        int idx = hash(newCustomer.cusID);
        if (table[idx] == null) {
            table[idx] = new CustomerNode(newCustomer);
            return true;
        }
        if (newCustomer.cusID.compareTo(table[idx].cus.cusID) < 0)
            table[idx].left = new CustomerNode(newCustomer);
        else if (newCustomer.cusID.compareTo(table[idx].cus.cusID) > 0)
            table[idx].right = new CustomerNode(newCustomer);
        else
           return false;

        table[idx].height = 1 + Math.max(getHeight(table[idx].left), getHeight(table[idx].right));
        int balance = getBalance(table[idx]);

        if (balance > 1 && newCustomer.cusID.compareTo(table[idx].left.cus.cusID) < 0) {
            table[idx] = rightRotation(table[idx]);
        }
        else if (balance > 1 && newCustomer.cusID.compareTo(table[idx].left.cus.cusID) > 0) {
            table[idx].left = leftRotation(table[idx].left);
            table[idx] = rightRotation(table[idx]);
        }
        else if (balance < -1 && newCustomer.cusID.compareTo(table[idx].left.cus.cusID) > 0) {
            table[idx] = leftRotation(table[idx]);
        }
        else if (balance < -1 && newCustomer.cusID.compareTo(table[idx].left.cus.cusID) < 0) {
            table[idx].right = rightRotation(table[idx].right);
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
        col.readFile(myFile);

        for (int  i = 0; i < MAX; i++) {
            System.out.println(col.table[i].cus.fName);
        }
    }
}
