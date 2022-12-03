package demo.rest.domain.users;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PerformanceRepository extends CrudRepository<PerformanceEntity, String> {

    List<PerformanceEntity> findAllByUserIdAndType(String userId, String type);

}
