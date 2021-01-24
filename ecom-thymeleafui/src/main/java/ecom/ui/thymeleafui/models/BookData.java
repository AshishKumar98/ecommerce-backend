package ecom.ui.thymeleafui.models;

import lombok.*;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookData {


    private int bookID;
    private String title;
    private String authors;
    private double average_rating;
    private String isbn;
    private String language_code;
    private int ratings_count;
    private double price;
    private String url;

}
