package demo.rest.domain.cars;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
public class CarEntity {

    @Id
    String id;
    String registration;
    @Enumerated(EnumType.STRING)
    CarModel model;
    String userId;

}
