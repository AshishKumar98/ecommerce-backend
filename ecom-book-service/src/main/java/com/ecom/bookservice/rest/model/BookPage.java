package com.ecom.bookservice.rest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@ToString
@Getter
@Setter
public class BookPage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "title";

}
