package GroupAssignment;

public class Customer {
    String cusID;
    String fName;
    String lName;
    String phone;

    public Customer(){};
    public Customer(String cusID, String fName, String lName, String phone) {
        this.cusID = cusID;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
    }

    public int getCode() {
        return Integer.parseInt(cusID.substring(3));
    }
}
