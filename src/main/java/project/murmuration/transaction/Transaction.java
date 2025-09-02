package project.murmuration.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.murmuration.offer.Offer;
import project.murmuration.user.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn (name = "customer_user_id", nullable = false)
    private User userCustomer;

    @ManyToOne
    @JoinColumn (name = "receiver_user_id", nullable = false)
    private User userReceiver;

    public Transaction(Offer offer, LocalDateTime transactionDate, User userCustomer, User userReceiver) {
        this.offer = offer;
        this.transactionDate = transactionDate;
        this.userCustomer = userCustomer;
        this.userReceiver = userReceiver;
    }
}