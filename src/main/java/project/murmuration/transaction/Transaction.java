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

    @Column(name = "transaction_title")
    private String transactionTitle;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    private int amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @OneToOne
    @JoinColumn (name = "sender_user_id", nullable = false)
    private User sender;

    @OneToOne
    @JoinColumn (name = "receiver_user_id", nullable = false)
    private User receiver;
}