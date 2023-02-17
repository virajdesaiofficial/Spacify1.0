package org.uci.spacify.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "User", schema = "corespacify")
public class User {
    @Id
    @Column(name="user_id")
    private String userId;

    @Column(name="email")
    @NonNull
    private String email;

    @Column(name="first_name")
    @NotNull
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="access_level", columnDefinition = "VARCHAR(20)")
    private AccessLevel accessLevel;

    public User(String userId, String email, String firstName, String lastName, AccessLevel accessLevel) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accessLevel = accessLevel;
    }

    public User() {

    }
}
