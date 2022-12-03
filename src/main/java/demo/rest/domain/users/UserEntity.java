package demo.rest.domain.users;

import demo.rest.domain.cars.CarEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    String id;
    String name;
    String job;
    double salary;
    LocalDateTime hiredOn;
    @OneToMany
    @JoinColumn(name="userId")
    List<CarEntity> cars;
}
