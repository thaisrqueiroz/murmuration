package project.murmuration.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private int price;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @Column(length = 50)
    private String location;
}