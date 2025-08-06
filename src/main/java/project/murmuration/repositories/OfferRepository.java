package project.murmuration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.murmuration.models.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}