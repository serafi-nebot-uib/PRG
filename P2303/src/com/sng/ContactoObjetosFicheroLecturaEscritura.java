package com.sng;

import java.io.*;

public class ContactoObjetosFicheroLecturaEscritura implements Closeable {
    private static final String DEFAULT_MODE = "rw";
    private static final int INTEGER_SIZE = 4;

    private final RandomAccessFile file;

    public ContactoObjetosFicheroLecturaEscritura(String path, String mode) throws FileNotFoundException {
        this.file = new RandomAccessFile(path, mode);
    }

    public ContactoObjetosFicheroLecturaEscritura(String path) throws FileNotFoundException {
        this(path, DEFAULT_MODE);
    }

    @Override
    public void close() throws IOException {
        this.file.close();
    }

    /**
     * Get size in bytes for a given string.
     *
     * @param str string to calculate size for
     * @return size in number of bytes
     */
    public int getUTFSize(String str) {
        return str == null ? 0 : str.length() + 1;
    }

    /**
     * Get contact size in bytes.
     *
     * @param contact contact to calculate size for
     * @return size in number of bytes
     */
    public int getContactSize(Contact contact) {
        if (contact == null)
            return 0;

        return
                INTEGER_SIZE +
                getUTFSize(contact.getName()) +
                getUTFSize(contact.getAddress()) +
                getUTFSize(contact.getPhone()) +
                getUTFSize(contact.getEmail()) +
                INTEGER_SIZE;
    }

    /**
     * Write contact to file.
     *
     * @param contact Contact to write
     * @throws IOException in the event of a write error
     * @throws IllegalArgumentException if contact is null
     */
    public void write(Contact contact) throws IOException, IllegalArgumentException {
        if (contact == null)
            throw new IllegalArgumentException("contact cannot be null");

        this.file.writeInt(contact.getCode());
        this.file.writeUTF(contact.getName());
        this.file.writeUTF(contact.getAddress());
        this.file.writeUTF(contact.getPhone());
        this.file.writeUTF(contact.getEmail());
        this.file.writeInt(contact.getAge());
    }

    /**
     * Write contact to file.
     *
     * @param contact Contact to write
     * @param offset offset to write to
     * @throws IOException in the event of a write error
     * @throws IllegalArgumentException if contact is null
     */
    public void write(Contact contact, long offset) throws IOException, IllegalArgumentException {
        if (contact == null)
            throw new IllegalArgumentException("contact cannot be null");
        this.file.seek(offset);
        write(contact);
    }

    /**
     * Write End-Of-File sequence to file.
     *
     * @param offset offset to write to
     * @throws IOException in the event of a write error
     */
    public void writeEOF(long offset) throws IOException {
        write(Contact.SENTINEL, offset);
    }

    /**
     * Write End-Of-File sequence to file.
     *
     * @throws IOException in the event of a write error
     */
    public void writeEOF() throws IOException {
        write(Contact.SENTINEL);
    }

    /**
     * Read Contact from file.
     *
     * @return read Contact object, null if EOF has been reached
     * @throws IOException in the event of a read error
     */
    public Contact read() throws IOException {
        Contact contact = new Contact();
        try {
            contact.setCode(this.file.readInt());
            contact.setName(this.file.readUTF());
            contact.setAddress(this.file.readUTF());
            contact.setPhone(this.file.readUTF());
            contact.setEmail(this.file.readUTF());
            contact.setAge(this.file.readInt());

            if (contact.isSentinel())
                contact = null;
        } catch (EOFException ignored) {
            contact = null;
        }

        return contact;
    }

    /**
     * Read Contact from file.
     *
     * @param offset offset to read from
     * @return read Contact object, null if EOF has been reached
     * @throws IOException in the event of a read error
     */
    public Contact read(long offset) throws IOException {
        this.file.seek(offset);
        return read();
    }
}
