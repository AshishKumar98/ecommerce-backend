package ecom.ui.thymeleafui.models;


import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLogin {
    private String email;
    private String password;
}
