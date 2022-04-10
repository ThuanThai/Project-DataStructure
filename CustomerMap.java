package GroupAssignment;

class CustomerNode {
    Customer data;
    CustomerNode next;

    public CustomerNode() {};
    public CustomerNode(Customer cus) {
        this.data = cus;
        this.next = null;
    }
}

public class CustomerMap {

    CustomerNode[][][] map = new CustomerNode[25][25][25];

    public CustomerMap(){
        for(int i = 0 ; i < 25; i++) {
            for(int j = 0; j < 25; j++) {
                for(int k = 0; k < 25; k++) {
                    map[i][j][k] = null;
                }
            }
        }
    }


    public int hashC(char c) {
        return c - 'A';
    }

    public boolean hashPut(Customer customer) {
        // hash coordinate
        int x = hashC(customer.cusID.charAt(0));
        int y = hashC(customer.cusID.charAt(1));
        int z = hashC(customer.cusID.charAt(2));

        CustomerNode newCustomer = new CustomerNode(customer);

        // if the slot is empty put the customer in.
        if(map[x][y][z] == null)  {
            map[x][y][z] = newCustomer;
            return true;
        }

        // if the slot is occupied:
        if(map[x][y][z] != null && map[x][y][z].data != customer) {
            CustomerNode p = new CustomerNode();
            p = map[x][y][z];
            // travel to the end of the list
            while(p != null) {
                if(p.data == customer) {
                    // the customer already exists
                    return false;
                } else p = p.next;
            }
            // add the new customer
            p.next = newCustomer;
        }
        return true;
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
            // check the list of customer
            CustomerNode p = new CustomerNode();
            p = map[x][y][z];
            // travel and check to the end of the list
            while(p != null) {
                if(p.data.cusID == Id) {
                    // the customer already exists
                    return p.data;
                } else p = p.next;
            }
        }
        // cannot find the customer
        return null;
    }

    public static void main(String[] args) {
        CustomerMap testMap = new CustomerMap();
        Customer a1 = new Customer("NAO1931162","Riannon","Septima","61420364080");
        System.out.println(testMap.hashPut(a1));
    }
}
