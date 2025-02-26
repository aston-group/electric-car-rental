package ru.astongroup.usermanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "tokenvault")
public class TokenArchive {

    @Id
    @Column(name = "id", columnDefinition = "bigserial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "token")
    private String token;

    @Column(name = "userid")
    private long userid;

    @Column(name = "created")
    private Date created;

    @Column(name = "expired")
    private Date expired;

    @Column(name = "username")
    private String username;

    @Override
    public String toString() {
        return "TokenArchive{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", userid=" + userid +
                ", created=" + created +
                ", expired=" + expired +
                ", username='" + username + '\'' +
                '}';
    }
}
