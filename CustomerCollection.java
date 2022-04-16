package GroupAssignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomerCollection {
    CustomerTree[][][] map = new CustomerTree[26][26][26];

    public CustomerCollection(){
        for(int i = 0; i < 26;i++) {
            for(int j = 0; j < 26; j++) {
                for( int k = 0; k < 26; k++){
                    map[i][j][k] = new CustomerTree();
                }
            }
        }
    }

    public int hashC(char c) {
        return c - 'A';
    }

    public boolean put(Customer newCustomer) {
        // coordinator
        int x = hashC(newCustomer.cusID.charAt(0));
        int y = hashC(newCustomer.cusID.charAt(1));
        int z = hashC(newCustomer.cusID.charAt(2));
        if ( map[x][y][z].head == null) {
            map[x][y][z].head = new CustomerNode(newCustomer);
        }
        else
            map[x][y][z].head = map[x][y][z].putInTree(map[x][y][z].head, newCustomer);
        return true;
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
                try{
                    put(newCustomer);
                } catch (ArrayIndexOutOfBoundsException e) {
                    // avoid fisrt line
                }

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
    public CustomerNode search(String id) {
        int x = hashC(id.charAt(0));
        int y = hashC(id.charAt(1));
        int z = hashC(id.charAt(2));
        return map[x][y][z].searchInTree(map[x][y][z].head, id);
    }

    public static void main(String[] args) {
        String myFile = "customer.csv";
        CustomerCollection col = new CustomerCollection();
        long start = System.currentTimeMillis();
        col.readFile(myFile);
        System.out.println(col.search("NAO1931262").getCus().fName);
//        System.out.println(col.search("NAO1931162").getCus().fName);
//        System.out.println(col.search("NAO1933232").getCus().fName);
//        System.out.println(col.search("NAO1421234").getCus().fName);
//        System.out.println(col.search("NAO1423234").getCus().fName);
    }
}
