package ma.tr.docnearme.util.dto;

import lombok.Getter;

@Getter
public class PaginationMeta {
    private int currentPage;
    private int totalPages;
    private long totalElements;

    public PaginationMeta(int currentPage, int totalPages, long totalElements) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }


}
