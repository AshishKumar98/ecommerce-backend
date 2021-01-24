package ecom.ui.thymeleafui.models;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartData {
    private String itemIDs;
    private String email;
}
