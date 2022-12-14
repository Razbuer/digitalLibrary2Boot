package ru.rusyaevmax.digitalLibrary2Boot.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Person {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "ФИО не должно быть пустым")
    @Column(name = "full_name", unique = true, nullable = false)
    private String fullName;

    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    @Max(value = 2022, message = "Год рождения должен быть меньше 2022")
    @Column(name = "year_of_birth", nullable = false)
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Book> books;
}
