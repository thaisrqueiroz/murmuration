package project.murmuration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.murmuration.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}