package org.kabbee.usermanagementservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String nickName;
    private String country;
    private LocalDate joinedDate;
    private LocalDate lastLoginDate;
    private Role role;
    @Embedded
    private NotificationSettings notificationSettings;

//    public Userser( String username, String email, String firstName, String lastName, String nickName,
//                   String country, LocalDate joinedDate, LocalDate lastLoginDate,Role role){
//             this.username = username;
//             this.email= email;
//              this.firstName= firstName;
//              this.lastName = lastName;
//              this. nickName = nickName;
//              this.country = country;
//              this.joinedDate = joinedDate;
//              this.lastLoginDate = lastLoginDate;
//               this.role = role;
//    }
}

