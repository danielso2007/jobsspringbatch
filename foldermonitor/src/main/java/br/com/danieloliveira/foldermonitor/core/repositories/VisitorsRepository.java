package br.com.danieloliveira.foldermonitor.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.danieloliveira.foldermonitor.core.domain.Visitors;

public interface VisitorsRepository extends JpaRepository<Visitors, Long> {
}
