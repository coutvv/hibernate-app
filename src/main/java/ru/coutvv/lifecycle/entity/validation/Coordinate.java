package ru.coutvv.lifecycle.entity.validation;

import com.sun.istack.internal.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by coutvv on 25.01.2017.
 */
@Entity
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@NoQuadrantIII
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    Integer id;
    @Getter
    @Setter
    @NotNull
    Integer x;
    @Getter
    @Setter
    @NotNull
    Integer y;

}
