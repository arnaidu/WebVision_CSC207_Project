package com.csc.spring.models;

import org.apache.commons.io.IOUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.print.Doc;
import java.io.IOException;
import java.io.InputStream;

/**
 * Content is a byte array obtained by reading the InputStream object from document upload
 */
@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "document_name")
    private String name;

    @Column(name = "document_type")
    private String type;

    @Column(name = "contents", length = 5000000)
    private byte[] contents;

    public Document() {
    }

    /**
     * @param name        The document name
     * @param inputStream The file's data read from uploading
     * @throws IOException this should never happen
     */
    public Document(String name, InputStream inputStream) throws IOException {
        this.name = name;
        //this.contents = inputStream.readAllBytes(); // Use IOUtils.toByteArray for JDK <= 8
        this.contents = IOUtils.toByteArray(inputStream);     // uncomment this line if line above has error
    }

    /* Getters and Setters */
    public String getName() {
        return this.name;
    }
    public String getType() {
        return this.type;
    }
    public byte[] getContents() {
        return this.contents;
    }
    /**
     * Assumes fileName is nonempty
     *
     * @return the file type of the first file
     */
    public String getFileType() {
        String[] fileName = this.name.split(".");
        return fileName[fileName.length - 1];
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setContents(byte[] contents) {
        this.contents = contents;
    }
}
