package com.core.utils;

import java.util.Collections;
import java.util.List;

import com.core.model.Paging;

public class PageNavigation {

    private PageNavigation() {
        throw new IllegalStateException("PageNavigation class");
    }

    public static Paging derivePageAttributes(int totalHits, int pageSize, int currentPage) {
        Paging page = new Paging();
        if (totalHits == 0) {
            return page;
        }
        page.setTotalHits(totalHits);
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        /*
         * Identify First Page
         */
        if (totalHits < pageSize) {
            page.setFirstPage(1);
            page.setLastPage(1);
            page.setNextPage(0);
            page.setPreviousPage(0);
            page.setCurrentPage(1);
            page.setTotalPages(1);
            return page;
        }
        page.setFirstPage(1);

        /*
         * Identify Last and Total Page
         */
        int totalPages = totalHits % pageSize;
        if (totalPages != 0) {
            totalPages = totalHits / pageSize + 1;
        } else {
            totalPages = totalHits / pageSize;
        }

        page.setLastPage(totalPages);
        page.setTotalPages(totalPages);

        /*
         * Identify Next Page
         */

        if (currentPage >= totalPages) {
            page.setNextPage(0);
        } else {
            page.setNextPage(currentPage + 1);
        }

        /*
         * Identify Previous Page
         */
        if (currentPage == page.getFirstPage() || totalPages == 1) {
            page.setPreviousPage(0);
        } else {
            page.setPreviousPage(currentPage - 1);
        }

        return page;

    }

    public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
        if (pageSize <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (page - 1) * pageSize;
        if (sourceList == null || sourceList.size() < fromIndex) {
            return Collections.emptyList();
        }

        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }

}