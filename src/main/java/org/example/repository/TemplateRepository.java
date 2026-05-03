package org.example.repository;

import org.hibernate.sql.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    // כאן לא צריך לכתוב כלום, Spring עושה הכל לבד!
}
