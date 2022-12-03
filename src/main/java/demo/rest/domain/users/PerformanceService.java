package demo.rest.domain.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceMapper performanceMapper;
    private final PerformanceRepository performanceRepository;

    public Performance createPerformance(String type, String userId, String period, double rating) {
        var performance = Performance.create(type, period, rating, userId);
        var toSave = performanceMapper.mapToEntity(performance);
        var saved = performanceRepository.save(toSave);
        return performanceMapper.map(saved);
    }

    @Transactional
    public List<Performance> getPerformances(String userId, String type) {
        return performanceRepository.findAllByUserIdAndType(userId, type).stream()
                .map(performanceMapper::map)
                .toList();
    }
}
