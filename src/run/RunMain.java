package run;

import controller.ContactManager;
import model.Contact;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class RunMain {
    Scanner scanner = new Scanner(System.in);
    ContactManager contactManager = new ContactManager();

    public RunMain() {
    }

    public void menuOfSystem() {
        try {
            do {
                menuContact();
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        contactManager.displayAll();
                        break;
                    case 2:
                        contactManager.addContact();
                        break;
                    case 3:
                        System.out.println("Enter your number to be changed VD: (+00)-00000000:");
                        String phoneEdit = scanner.nextLine();
                        if (phoneEdit.equals("")) {
                            menuOfSystem();
                        } else {
                            contactManager.updateContact(phoneEdit);
                        }
                        break;
                    case 4:
                        System.out.println("Enter your number to be deleted VD: (+84)-095638425:");
                        String phoneDelete = scanner.nextLine();
                        if (phoneDelete.equals("")) {
                            menuOfSystem();
                        } else {
                            contactManager.deleteContact(phoneDelete);
                        }
                        break;
                    case 5:
                        System.out.println("Enter key word:");
                        String keyword = scanner.nextLine();
                        contactManager.searchContactByNameOrPhone(keyword);
                        break;
                    case 6:
                        ArrayList<Contact> contactArrayList = contactManager.readFile(ContactManager.PATH_NAME_CONTACT);
                        contactArrayList.forEach(Contact::display);
                        System.out.println("Read file successfully !");
                        System.out.println("--------------------");
                        break;
                    case 7:
                        contactManager.writeFile(contactManager.getContactList(), ContactManager.PATH_NAME_CONTACT);
                        break;
                    case 8:
                        System.exit(0);
                    default:
                        System.out.println("Choice does not exist, please enter again !!!");
                        System.out.println("--------------------");
                        break;
                }
            } while (true);
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Wrong data type, please enter again!!!");
            System.out.println("--------------------");
            menuOfSystem();
        }
    }

    private void menuContact() {
        System.out.println("----Contact Manager System----");
        System.out.println("Enter number to continue:");
        System.out.println("1. Display list");
        System.out.println("2. Add new contact");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        System.out.println("5. Find");
//        System.out.println("6. Read file");
//        System.out.println("7. Write into file");
        System.out.println("8. Exit");
        System.out.println("Select your number:");
    }
}
