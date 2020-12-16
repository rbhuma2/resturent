package com.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Paging {
    private int totalHits;
    private int totalPages;
    private int pageSize;
    private int firstPage;
    private int lastPage;
    private int currentPage;
    private int nextPage;
    private int previousPage;

    @JsonCreator
    public Paging(@JsonProperty("totalHits") int totalHits, @JsonProperty("totalPages") int totalPages,
            @JsonProperty("pageSize") int pageSize, @JsonProperty("firstPage") int firstPage,
            @JsonProperty("lastPage") int lastPage, @JsonProperty("currentPage") int currentPage,
            @JsonProperty("nextPage") int nextPage, @JsonProperty("previousPage") int previousPage) {
        super();
        this.totalHits = totalHits;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
        this.previousPage = previousPage;
    }

    public Paging() {
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

}
