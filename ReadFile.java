package GroupAssignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class ReadFile {
    BufferedReader reader;
    String line;
    LinkedList<Customer> list;

    public void read() {
        try {
            String myFile = "customer.csv";
            reader = new BufferedReader(new FileReader(myFile));
            line = "";
            list = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                Customer newCus = new Customer();
                newCus.cusID = row[0];
                newCus.fName = row[1];
                newCus.lName = row[2];
                newCus.phone = row[3];
                list.add(newCus);
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
        ReadFile r = new ReadFile();
        r.read();
        HashMap<Integer, Integer> map = new HashMap<>();

        for (Customer x : r.list) {
//            System.out.printf("%10s%10s%10s\t%s\n", x.cusID, x.fName, x.lName, x.phone);
            int key = Math.abs(x.cusID.hashCode()) % 1300;
            if (map.containsKey(key))
                map.put(key, map.get(key) + 1);
            else {
                map.put(key, 1);
            }
//            System.out.println(Math.abs(x.cusID.hashCode()) + " " + Math.abs(x.cusID.hashCode()) % 1300);
        }

        for (Map.Entry<Integer, Integer> m : map.entrySet()) {
            if (m.getValue() > 1) {
                System.out.println(m.getKey() + " " + m.getValue());
            }
        }
    }
}
