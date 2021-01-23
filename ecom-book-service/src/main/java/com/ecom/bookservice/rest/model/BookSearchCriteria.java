package com.ecom.bookservice.rest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BookSearchCriteria {
    private String title;
    private String languageCode;

}
