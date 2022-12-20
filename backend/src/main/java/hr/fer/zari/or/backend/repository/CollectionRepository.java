package hr.fer.zari.or.backend.repository;

import hr.fer.zari.or.backend.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findAllByArtist_Id(Long artistId);
    Optional<Collection> findCollectionByIdAndArtist_Id(Long id, Long artistId);
    boolean existsByIdAndArtist_Id(Long id, Long artistId);
}
