package ecom.ui.thymeleafui.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SearchFields {
    private String pageNumber = "0";
    private String pageSize = "10";
    private String order= "ASC";
    private String sortBy = "title";
    private String title;
    private String languageCode= "en";
    private String authors;

}