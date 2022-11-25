package ru.rusyaevmax.digitalLibrary2Boot.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Имя книги не должно быть пустым")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "Имя книги не должно быть пустым")
    @Column(name = "author", nullable = false)
    private String author;

    @Min(value = 0, message = "Год не может быть меньше нуля")
    @Max(value = 2022, message = "Год не может быть больше 2022")
    @Column(name = "year", nullable = false)
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Person owner;

    @Column(name = "taken_from")
    private LocalDateTime takenFrom;

    @Transient
    private boolean overdue;
}
