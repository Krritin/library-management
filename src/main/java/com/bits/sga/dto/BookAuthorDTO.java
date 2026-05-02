package com.bits.sga.dto;

public class BookAuthorDTO {

    private Long bookId;
    private String title;
    private String isbn;
    private Double price;
    private Integer publishedYear;
    private Long authorId;
    private String authorName;
    private String authorNationality;

    public BookAuthorDTO(Long bookId, String title, String isbn, Double price,
                        Integer publishedYear, Long authorId, String authorName,
                        String authorNationality) {
        this.bookId = bookId;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.publishedYear = publishedYear;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorNationality = authorNationality;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorNationality() {
        return authorNationality;
    }
}
