package project.murmuration.offer;

import jakarta.persistence.*;
import lombok.*;
import project.murmuration.category.Category;
import project.murmuration.user.User;

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

    @Column(name = "is_unique")
    private boolean isUnique;

    public Offer(String title, String description, Category category, int price, String location, User user, boolean isUnique) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.user = user;
        this.location = location;
        this.isUnique = isUnique;
    }
}