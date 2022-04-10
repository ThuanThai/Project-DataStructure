package GroupAssignment;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomerMap {

    RBTree[][][] map = new RBTree[26][26][26];

    public CustomerMap(){
        Customer nullCustomer = new Customer("000000000","","","");
        for(int i = 0; i < 26;i++) {
            for(int j = 0; j < 26; j++) {
                for( int k = 0; k < 26; k++){
                    map[i][j][k] = new RBTree(nullCustomer);
                }
            }
        }
    }

    public int hashC(char c) {
        return c - 'A';
    }

    public void hashPut(Customer newCustomer) {
        // hash coordinate
        int x = hashC(newCustomer.cusID.charAt(0));
        int y = hashC(newCustomer.cusID.charAt(1));
        int z = hashC(newCustomer.cusID.charAt(2));

        // put the new customer in the customerCollection at x,y,z coordinate.
        try {
            map[x][y][z].insertNewNode(newCustomer);
        } catch (ArrayIndexOutOfBoundsException e) {
            // skip first line because "customer_id" cause index out of bound.
        }
    }

    public Customer searchFull(String Id) {
        // hash coordinate
        int x = hashC(Id.charAt(0));
        int y = hashC(Id.charAt(1));
        int z = hashC(Id.charAt(2));

        if(map[x][y][z] == null)  {
            // no customer found
            return null;
        } else {
            // not yet develop
        }
        // cannot find the customer
        return null;
    }

    public void readFile(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = "";
            while ((line = reader.readLine()) != null) {
                Customer newCustomer = new Customer();
                String[] column = line.split(",");
                newCustomer.cusID = column[0];
                newCustomer.fName = column[1];
                newCustomer.lName = column[2];
                newCustomer.phone = column[3];
                hashPut(newCustomer);
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
        long startInit = System.currentTimeMillis();
        CustomerMap col = new CustomerMap();
        long endInit = System.currentTimeMillis();
        System.out.println("Init time: " + (endInit - startInit));
        long startRead = System.currentTimeMillis();
        col.readFile(myFile);
        long endRead = System.currentTimeMillis();
        System.out.println("Read file time: " + (endRead - startRead));
    }
}
