package com.sng;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class P2303 {
    private static final String FILE_NAME = "contacts.dat";
    private static final String TMP_FILE_NAME = "contacts.tmp.dat";
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        try {
            boolean end = false;
            while (!end) {
                switch (menu()) {
                    case 1 -> createContact();
                    case 2 -> readContact();
                    case 3 -> updateContact();
                    case 4 -> deleteContact();
                    case 5 -> listContacts();
                    case 6 -> end = true;
                }
            }
        } catch (Exception e) {
            System.out.printf("CRITICAL ERROR: %s\n", e.getMessage());
        }
    }

    private static int menu() throws IOException {
        Integer choice = null;
        System.out.println("CONTACT MENU\n");
        System.out.println("\t(1) Create");
        System.out.println("\t(2) Read");
        System.out.println("\t(3) Update");
        System.out.println("\t(4) Delete");
        System.out.println("\t(5) List");
        System.out.println("\t(6) Quit");

        while (choice == null) {
            System.out.print("\n> ");
            try {
                choice = Integer.parseInt(reader.readLine());
                if (choice < 1 || choice > 6) {
                    choice = null;
                    System.out.println("Invalid option: must be a value between 1 and 6");
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return choice;
    }

    private static Integer readInt(String name) throws IOException {
        Integer number = null;
        while (number == null) {
            try {
                System.out.printf("%s: ", name);
                number = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException ignored) {
                System.out.printf("%s must be a number\n", name);
            }
        }
        return number;
    }

    private static Contact createContactFromUserInput() throws IOException {
        Contact contact = new Contact();
        contact.setCode(readInt("Code"));
        System.out.print("Name: ");
        contact.setName(reader.readLine());
        System.out.print("Address: ");
        contact.setAddress(reader.readLine());
        System.out.print("Phone: ");
        contact.setPhone(reader.readLine());
        System.out.print("Email: ");
        contact.setEmail(reader.readLine());
        contact.setAge(readInt("Age"));
        return contact;
    }

    private static void createContact() throws IOException {
        System.out.println("CREATE CONTACT\n");
        Contact contact = createContactFromUserInput();
        ContactoObjetosFicheroLecturaEscritura master = null;
        ContactoObjetosFicheroLecturaEscritura tmp = null;

        File masterFile = new File(FILE_NAME);
        File tmpFile = new File(TMP_FILE_NAME);
        try {
            master = new ContactoObjetosFicheroLecturaEscritura(FILE_NAME);
            tmp = new ContactoObjetosFicheroLecturaEscritura(TMP_FILE_NAME);

            boolean write_done = false;
            Contact cnt = null;
            while ((cnt = master.read()) != null) {
                if (cnt.getCode() == contact.getCode()) {
                    throw new IllegalArgumentException(String.format("contact with code %d already exists\n", contact.getCode()));
                } else if (cnt.getCode() > contact.getCode() && !write_done) {
                    tmp.write(contact);
                    write_done = true;
                }
                tmp.write(cnt);
            }
            if (!write_done)
                tmp.write(contact);
            tmp.writeEOF();

            if (masterFile.delete())
                if (!tmpFile.renameTo(masterFile))
                    System.out.printf("failed to rename file from \"%s\" to \"%s\"", TMP_FILE_NAME, FILE_NAME);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            if (!tmpFile.delete())
                System.out.printf("failed to delete file %s\n", TMP_FILE_NAME);
        } catch (IOException e) {
            System.out.printf("ERROR: failed to read/write from file \"%s\": %s\n", FILE_NAME, e.getMessage());
        } finally {
            try {
                if (master != null)
                    master.close();
            } catch (IOException e) {
                System.out.printf("failed to close file: %s\n", e.getMessage());
            }

            try {
                if (tmp != null)
                    tmp.close();
            } catch (IOException e) {
                System.out.printf("failed to close file: %s\n", e.getMessage());
            }
        }
    }

    private static void readContact() {
        System.out.println("READ CONTACT");
        ContactoObjetosFicheroLecturaEscritura master = null;
        try {
            Integer code = readInt("Code");

            master = new ContactoObjetosFicheroLecturaEscritura(FILE_NAME);

            boolean found = false;
            Contact contact = null;
            while ((contact = master.read()) != null && !found) {
                if (contact.getCode() == code) {
                    System.out.println(contact);
                    found = true;
                }
            }
            if (!found)
                System.out.printf("no contact with code %d found\n", code);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (master != null)
                    master.close();
            } catch (IOException e) {
                System.out.printf("failed to close file: %s\n", e.getMessage());
            }
        }
    }

    private static void updateContact() {
        System.out.println("UPDATE CONTACT");
        ContactoObjetosFicheroLecturaEscritura master = null;
        ContactoObjetosFicheroLecturaEscritura tmp = null;
        File masterFile = new File(FILE_NAME);
        File tmpFile = new File(TMP_FILE_NAME);
        try {
            Contact contact = createContactFromUserInput();
            master = new ContactoObjetosFicheroLecturaEscritura(FILE_NAME);
            tmp = new ContactoObjetosFicheroLecturaEscritura(TMP_FILE_NAME);

            boolean write_done = false;
            Contact cnt = null;
            while ((cnt = master.read()) != null) {
                if (cnt.getCode() == contact.getCode()) {
                    tmp.write(contact);
                    write_done = true;
                } else {
                    tmp.write(cnt);
                }
            }
            if (!write_done)
                System.out.printf("no contact with code %d found\n", contact.getCode());
            tmp.writeEOF();

            if (masterFile.delete())
                if (!tmpFile.renameTo(masterFile))
                    System.out.printf("failed to rename file from \"%s\" to \"%s\"", TMP_FILE_NAME, FILE_NAME);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            if (!tmpFile.delete())
                System.out.printf("failed to delete file %s\n", TMP_FILE_NAME);
        } catch (IOException e) {
            System.out.printf("ERROR: failed to read/write from file \"%s\": %s\n", FILE_NAME, e.getMessage());
        } finally {
            try {
                if (master != null)
                    master.close();
            } catch (IOException e) {
                System.out.printf("failed to close file: %s\n", e.getMessage());
            }

            try {
                if (tmp != null)
                    tmp.close();
            } catch (IOException e) {
                System.out.printf("failed to close file: %s\n", e.getMessage());
            }
        }
    }

    private static void deleteContact() {
        System.out.println("DELETE CONTACT");
        ContactoObjetosFicheroLecturaEscritura master = null;
        ContactoObjetosFicheroLecturaEscritura tmp = null;
        File masterFile = new File(FILE_NAME);
        File tmpFile = new File(TMP_FILE_NAME);
        try {
            Integer code = readInt("Code");

            master = new ContactoObjetosFicheroLecturaEscritura(FILE_NAME);
            tmp = new ContactoObjetosFicheroLecturaEscritura(TMP_FILE_NAME);

            Contact contact = null;
            while ((contact = master.read()) != null)
                if (contact.getCode() != code)
                    tmp.write(contact);
            tmp.writeEOF();

            if (masterFile.delete())
                if (!tmpFile.renameTo(masterFile))
                    System.out.printf("failed to rename file from \"%s\" to \"%s\"", TMP_FILE_NAME, FILE_NAME);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (master != null)
                    master.close();
            } catch (IOException e) {
                System.out.printf("failed to close file: %s\n", e.getMessage());
            }

            try {
                if (tmp != null)
                    tmp.close();
            } catch (IOException e) {
                System.out.printf("failed to close file: %s\n", e.getMessage());
            }
        }
    }

    private static void listContacts() {
        System.out.println("LIST CONTACTS");
        ContactoObjetosFicheroLecturaEscritura io = null;
        try {
            io = new ContactoObjetosFicheroLecturaEscritura(FILE_NAME);

            Contact contact = null;
            while ((contact = io.read()) != null)
                System.out.println(contact);
        } catch (IOException e) {
            System.out.printf("failed to read/write from file \"%s\": %s", FILE_NAME, e.getMessage());
        } finally {
            try {
                if (io != null)
                    io.close();
            } catch (IOException e) {
                System.out.printf("failed to close file: %s\n", e.getMessage());
            }
        }
    }
}