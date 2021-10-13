package com.sofkau.library.dtos;

import java.util.Date;

public class ResourceDTO {

    private String id;
    private String name;
    private String type;
    private String genre;
    private Date loanDate;
    private int borrowedCopies;
    private int totalCopies;

    public ResourceDTO(String id, String name, String type, String genre, Date loanDate, int borrowedCopies, int totalCopies) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.genre = genre;
        this.loanDate = loanDate;
        this.borrowedCopies = borrowedCopies;
        this.totalCopies = totalCopies;
    }

    public ResourceDTO() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public int getBorrowedCopies() {
        return borrowedCopies;
    }

    public void setBorrowedCopies(int borrowedCopies) {
        this.borrowedCopies = borrowedCopies;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }
}