package demo.rest.domain.users;

import demo.rest.domain.cars.CarModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@Table(name = "performance")
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceEntity {

    @Id
    String id;
    String type;
    String period;
    double rating;
    String userId;

}
