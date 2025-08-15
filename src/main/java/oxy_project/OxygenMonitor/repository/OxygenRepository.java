package oxy_project.OxygenMonitor.repository;

import oxy_project.OxygenMonitor.model.OxygenData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OxygenRepository extends JpaRepository<OxygenData, Long> {
    List<OxygenData> findByCity(String city);
}
