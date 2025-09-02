package project.murmuration.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import project.murmuration.offer.Offer;
import project.murmuration.security.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, length = 50)
    private String location;

    private int balance;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Offer> offers = new ArrayList<>();

    public User(String username, String name, String email, String password, Role role, String location) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.location = location;
    }
}