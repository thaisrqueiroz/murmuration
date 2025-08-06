package project.murmuration.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

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

    private int amount;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @OneToOne
    @JoinColumn (name = "sender_user_id")
    private User sender;

    @OneToOne
    @JoinColumn (name = "receiver_user_id")
    private User receiver;
}