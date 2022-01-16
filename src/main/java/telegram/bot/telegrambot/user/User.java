package telegram.bot.telegrambot.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "telegrambot")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "userid")
    private long userid;
    @Column(name = "username")
    private String name;
    @Column(name = "userage")
    private int age;
    @Column(name = "usergender")
    private String gender;
    @Column(name = "realusername")
    private String realUserName;
    @Column(name = "realfirstname")
    private String realFirstName;
    @Column(name = "reallastname")
    private String realLastName;

    public User() {
    }

    public User(long userid, String name, int age, String gender, String realUserName, String realFirstName, String realLastName) {
        this.userid = userid;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.realUserName = realUserName;
        this.realFirstName = realFirstName;
        this.realLastName = realLastName;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

    public String toString() {
        return "Имя: " + this.getName() + "\n" +
                "Возраст: " + this.getAge() + "\n" +
                "Пол: " + this.getGender() + "\n";
    }

}
