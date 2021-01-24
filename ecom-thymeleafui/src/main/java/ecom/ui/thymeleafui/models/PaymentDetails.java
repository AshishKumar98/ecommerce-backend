package ecom.ui.thymeleafui.models;


import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {
    private String payment_id;
    private String payment_status;
    private String id;
    private String transaction_id;
}
