package GroupAssignment;

import java.util.InputMismatchException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        CustomerCollection database = new CustomerCollection();
        database.readFile("customer.csv");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while(choice != 5) {
            printMenu();
            choice = getChoice();
            processChoice(choice,database);
        }
    }

    private static void processChoice(int choice, CustomerCollection database) {
        Scanner scanner = new Scanner(System.in);
        switch (choice) {
            case 1:
                // user create new customer.
                System.out.println("====== CREATING A NEW CUSTOMER ======");

                // get all customer information
                System.out.print("Enter customer Id: ");
                String id = scanner.nextLine();

                System.out.print("Enter customer first name: ");
                String fName = scanner.nextLine();
                System.out.print("Enter customer last name: ");
                String lName = scanner.nextLine();
                System.out.print("Enter customer phone number: ");
                String phone = scanner.nextLine();

                // put the customer in the database
                try{
                    database.put(new Customer(id,fName,lName,phone));
                }  catch (StringIndexOutOfBoundsException ex) {
                    System.out.println("Unable to add new customer due to INVALID INPUT!!!");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Unable to add new customer due to INVALID INPUT!!!");
                }
                break;
            case 2:
                System.out.println("====== UPDATING A CUSTOMER ======");
                System.out.print("Enter customer ID you want to update: ");
                id = scanner.nextLine();
                updateCustomer(id,database);
                break;
            case 3:
                // user search for a customer.
                System.out.println("====== SEARCHING FOR A CUSTOMER ======");
                System.out.print("Enter customer Id you want to search: ");
                id = scanner.nextLine();
                Customer searchedCustomer = new Customer();
                try{
                    // find the customer
                    if(database.search(id) == null) System.out.println("Customer not found!!!"); // cannot find the customer in database
                    else {
                        // print the customer found
                        searchedCustomer = database.search(id).cus;
                        System.out.printf("%-20s%-20s%-20s%-20s", "ID","FIRST NAME","LAST NAME","PHONE NUMBER");
                        System.out.println();
                        System.out.printf("%-20s%-20s%-20s%-20s",
                                searchedCustomer.cusID,searchedCustomer.fName,
                                searchedCustomer.lName,searchedCustomer.phone);
                        System.out.println();
                    }
                } catch (StringIndexOutOfBoundsException ex) {
                    System.out.println("Invalid input!!!");
                    System.out.print("Enter customer Id you want to search: ");
                    id = scanner.nextLine();
                    database.searchPartial(id);
                }
                break;
            case 4:
                // user search for a list of customers.
                System.out.println("====== SEARCHING FOR A lIST OF CUSTOMERS ======");
                System.out.print("Enter customer Id you want to search: ");
                id = scanner.nextLine();
                try {
                    database.searchPartial(id);
                } catch (StringIndexOutOfBoundsException ex) {
                    System.out.println("Invalid Input!!!");
                    break;
                }
                break;
            default:
                break;
        }
        System.out.println("==================================");
    }

    public static void printMenu(){
        System.out.println("1.Add a new customer.");
        System.out.println("2.Update a customer.");
        System.out.println("3.Search for a customer.");
        System.out.println("4.Search for a list of customers.");
        System.out.println("5.EXIT.");
    }

    public static int getChoice() {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while(choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5) {
            System.out.print("Enter your command here: ");
            try{
                choice = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Please only enter an integer!!!");
            }
            scanner.nextLine();
        }
        return choice;
    }

    public static void updateCustomer(String id, CustomerCollection database) {
        Customer updatedCustomer = new Customer();
        if(database.search(id) == null) {
            System.out.println("Customer not found!!!");
            return;
        }
        updatedCustomer = database.search(id).cus;
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while(choice != 4) {
            System.out.println("1.Update first name.");
            System.out.println("2.Update last name.");
            System.out.println("3.Update phone number.");
            System.out.println("4.BACK.");
            choice = getChoice();
            switch (choice) {
                case 1:
                    System.out.println("Current first name:" + updatedCustomer.fName);
                    System.out.print("Enter new first name: ");
                    String newFName = scanner.nextLine();
                    try{
                        updatedCustomer.fName = newFName;
                    } catch (StringIndexOutOfBoundsException ex) {
                        System.out.println("Invalid Input!!!");
                    }
                    break;
                case 2:
                    System.out.println("Current last name:" + updatedCustomer.lName);
                    System.out.print("Enter new last name: ");
                    String newLName = scanner.nextLine();
                    try{
                        updatedCustomer.lName = newLName;
                    } catch (StringIndexOutOfBoundsException ex) {
                        System.out.println("Invalid Input!!!");
                    }
                    break;
                case 3:
                    System.out.println("Current phone number:" + updatedCustomer.phone);
                    System.out.print("Enter new phone number: ");
                    String newPNumber = scanner.nextLine();
                    try{
                        updatedCustomer.phone = newPNumber;
                    } catch (StringIndexOutOfBoundsException ex) {
                        System.out.println("Invalid Input!!!");
                    }
                    break;
            }
        }
    }
}
