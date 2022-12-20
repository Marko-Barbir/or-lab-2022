package hr.fer.zari.or.backend.repository;

import hr.fer.zari.or.backend.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findAllByCollection_IdAndCollection_Artist_Id(Long collectionId, Long artistId);

    Optional<Track> findByIdAndCollection_IdAndCollection_Artist_Id(Long id, Long collectionId, Long artistId);
}