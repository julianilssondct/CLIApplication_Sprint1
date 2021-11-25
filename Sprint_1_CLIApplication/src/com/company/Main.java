package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String input = "";
        JDBC jdbc = new JDBC();
        List<User> userList = new ArrayList<User>();
        while(!input.equals("x")) {
            System.out.println("Please choose an option below:");
            System.out.println("    1. View all users");
            System.out.println("    2. Find user by first name");
            System.out.println("    3. Find user by email");
            System.out.println("    4. Create user and add to database");
            System.out.println("    5. Delete user");
            System.out.println("    6. Update user");
            System.out.println("    x. Exit");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            switch(input) {
                case "1":
                    userList = jdbc.getUsers("select * from users u");
                    printUsers(userList);
                    //Console.ReadKey(); Console.Clear();
                    break;
                case "2":
                    System.out.print("Enter name: ");
                    String firstName = scanner.next();
                    userList = jdbc.getUsers("select * from users u where u.first_name ='"+firstName+"'");
                    printUsers(userList);
                    break;
                case "3":
                    System.out.print("Enter letters email should contain: ");
                    String email = scanner.next();
                    userList = jdbc.getUsers("select * from users u where u.email like '%"+email+"%'");
                    printUsers(userList);
                    break;
                case "4":
                    System.out.print("Enter email: ");
                    email = scanner.next();
                    System.out.print("Enter first name: ");
                    firstName = scanner.next();
                    System.out.print("Enter last name: ");
                    String lastName = scanner.next();
                    User user = new User();
                    user.setEmail(email);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    int affectedRows = jdbc.addUser(user);
                    if(affectedRows > 0) {
                        System.out.println("User added to database");
                    } else {
                        System.out.println("Something happened and no user was added. Try again");
                    }
                    break;
                case "5":
                    System.out.print("Enter id: ");
                    String id = scanner.next();
                    affectedRows = jdbc.deleteUser(id);
                    if(affectedRows > 0) {
                        System.out.println("User deleted from database");
                    } else {
                        System.out.println("Something happened and nothing was deleted. Try again");
                    }
                    break;
                case "6":
                    System.out.print("Enter id:");
                    id = scanner.next();
                    user = new User();
                    System.out.println("Please update information below");
                    System.out.print("Email: ");
                    user.setEmail(scanner.next());
                    System.out.print("First name: ");
                    user.setFirstName(scanner.next());
                    System.out.print("Last name: ");
                    user.setLastName(scanner.next());
                    affectedRows = jdbc.updateUser(id, user);
                    if(affectedRows > 0) {
                        System.out.println("User updated from database");
                    } else {
                        System.out.println("Something happened and nothing was deleted. Try again");
                    }
                    break;
                case "x":
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Option not available");
                    break;
            }
            System.out.println("\n\n");
        }
    }
    public static void printUsers(List<User> userList) {
        for(User user: userList) {
            System.out.println(user.getId() + ", " + user.getEmail() + ", " + user.getFirstName()+ ", " + user.getLastName());
        }
    }

}
