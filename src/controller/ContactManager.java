package controller;

import model.Contact;
import run.RunMain;
import validate.Validate;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager {
    private final ArrayList<Contact> contactList;
    private final Scanner scanner = new Scanner(System.in);
    private final Validate validate = new Validate();
    public static final String PATH_NAME_CONTACT = "data/contacts.csv";

    public ContactManager() {
        if (new File(PATH_NAME_CONTACT).length() == 0) {
            this.contactList = new ArrayList<>();
        } else {
            this.contactList = readFile(PATH_NAME_CONTACT);
        }
    }

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public String getGender(int choice) {
        String sex = "";
        switch (choice) {
            case 1:
                sex = "Male";
                break;
            case 2:
                sex = "Female";
                break;
            case 3:
                sex = "Other";
                break;
        }
        return sex;
    }

    public void addContact() {
        System.out.println("Enter your info");
        String phoneNumber = enterPhoneNumber();
        System.out.println("Enter your group");
        String group = scanner.nextLine();
        System.out.println("Enter your name");
        String name = scanner.nextLine();
        System.out.println("Enter your sex");
        System.out.println("1. Male");
        System.out.println("2. Female");
        System.out.println("3. Other");
        int gender = Integer.parseInt(scanner.nextLine());
        if (getGender(gender).equals("")) {
            System.out.println("Enter wrong sex, please enter again");
            System.out.println("--------------------");
            return;
        }
        System.out.println("Enter address");
        String address = scanner.nextLine();
        System.out.println("Enter birthday(dd-mm-yyyy):");
        String date = scanner.nextLine();
        LocalDate dateOfBirth = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-LL-yyyy"));
        String email = enterEmail();
        for (Contact phone : contactList) {
            if (phone.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("The number is identical to the existing contact, please enter again!");
                System.out.println("");
                return;
            }
        }
        Contact contact = new Contact(phoneNumber, group, name, getGender(gender), address, dateOfBirth, email);
        contactList.add(contact);
        System.out.println("Add " + phoneNumber + " to the contact successfully !");
        System.out.println("");
    }

    public void updateContact(String phoneNumber) {
        Contact editContact = null;
        for (Contact contact : contactList) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                editContact = contact;
            }
        }
        if (editContact != null) {
            int index = contactList.indexOf(editContact);
            System.out.println("Enter new group");
            editContact.setGroup(scanner.nextLine());
            System.out.println("Enter new name");
            editContact.setName(scanner.nextLine());
            System.out.println("Enter new sex");
            System.out.println("1. Male 2. Female 3. Other");

            int gender = Integer.parseInt(scanner.nextLine());
            editContact.setGender(getGender(gender));
            System.out.println("Enter new address");
            editContact.setAddress(scanner.nextLine());
            System.out.println("Enter new birthday");
            String date = scanner.nextLine();
            LocalDate dateOfBirth = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-LL-yyyy"));
            editContact.setDateOfBirth(dateOfBirth);
            editContact.setEmail(enterEmail());
            contactList.set(index, editContact);
            System.out.println("Update successfully!");
            System.out.println("--------------------");
        } else {
            System.out.println("Cannot find the number in contact!");
            System.out.println("--------------------");
        }
    }

    public void deleteContact(String phoneNumber) {
        Contact deleteContact = null;
        for (Contact contact : contactList) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                deleteContact = contact;
            }
        }
        if (deleteContact != null) {
            System.out.println("Confirm");
            System.out.println("Y:delete");
            System.out.println("Other symbol: thoat");
            String confirm = scanner.next();
            if (confirm.equalsIgnoreCase("y")) {
                contactList.remove(deleteContact);
                System.out.println("Deleted " + phoneNumber + " successfully !");
                System.out.println("--------------------");
            }
        } else {
            System.out.println("Cannot find the number in contact !");
            System.out.println("--------------------");
        }
    }

    public void searchContactByNameOrPhone(String keyword) {
        ArrayList<Contact> contacts = new ArrayList<>();
        for (Contact contact : contactList) {
            if (validate.validatePhoneOrName(keyword, contact.getPhoneNumber()) || validate.validatePhoneOrName(keyword, contact.getName())) {
                contacts.add(contact);
            }
        }
        if (contacts.isEmpty()) {
            System.out.println("Cannot find the number in contact!");
            System.out.println("--------------------");
        } else {
            System.out.println("Contact:");
            contacts.forEach(System.out::println);
            System.out.println("--------------------");
        }
    }

    public void displayAll() {
        System.out.println("");
        System.out.printf("| %-15s| %-10s| %-15s| %-10s| %-10s|\n", "Phone", "Group", "Full name", "sex", "address");
        System.out.println("");
        for (Contact contact : contactList) {
            contact.display();
            System.out.println("");
        }
    }

    public String enterPhoneNumber() {
        int count = 0;
        String phoneNumber;
        while (true) {
            System.out.print("Enter number ");
            String phone = scanner.nextLine();
            if (!validate.validatePhone(phone)) {
                System.err.println("Wrong data type for number");
                System.out.println(">[Attention]: number must contain  10 digit (0 - 9) follow this format: (+00)-00000000");
                System.out.println("");
                count++;
                if(count >= 4) {
                    System.err.println("Enter too much time|| system turn back automatically");
                    new RunMain().menuOfSystem();
                }
            } else {
                phoneNumber = phone;
                break;
            }
        }
        return phoneNumber;
    }

    private String enterEmail() {
        int count = 0;
        String email;
        while (true) {
            System.out.print("Enter email: ");
            String inputEmail = scanner.nextLine();
            if (!validate.validateEmail(inputEmail)) {
                System.out.println("Email not in the right format");
                System.out.println("Email must be in format: abc@gmail.com");
                System.out.println("");
                count++;
                if(count >= 4) {
                    System.err.println("Enter wrong too much time!!");
                    new RunMain().menuOfSystem();
                }
            } else {
                email = inputEmail;
                break;
            }
        }
        return email;
    }

    public void writeFile(ArrayList<Contact> contactList, String path) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            for (Contact contact : contactList) {
                bufferedWriter.write(contact.getPhoneNumber() + "," + contact.getGroup() + "," + contact.getName() + "," + contact.getGender() + ","
                + contact.getAddress() + "," + contact.getDateOfBirth() + "," + contact.getEmail() + "\n");
            }
            bufferedWriter.close();
            System.out.println("Write file successfully !");
            System.out.println("");
        } catch (IOException e) {
            System.out.println("Writing file exception" + e.getMessage());
        }
    }

    public ArrayList<Contact> readFile(String path) {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(",");
                contacts.add(new Contact(strings[0], strings[1], strings[2], strings[3], strings[4], LocalDate.parse(strings[5], DateTimeFormatter.ISO_LOCAL_DATE), strings[6]));
            }
        } catch (IOException e) {
            System.err.println("Reading file exception"+ e.getMessage());
        }
        return contacts;
    }
}
