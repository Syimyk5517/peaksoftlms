package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Presentation;
import com.example.peaksoftlmsb8.dto.response.PresentationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Long> {
    @Query("select new com.example.peaksoftlmsb8.dto.response.PresentationResponse(p.id,p.name,p.description,p.formatPPT,p.lesson.id) from Presentation p where p.lesson.id=:lessonId")
    List<PresentationResponse> getAllPresentationsByLessonId(Long lessonId);

    @Query("select new com.example.peaksoftlmsb8.dto.response.PresentationResponse(p.id,p.name,p.description,p.formatPPT,p.lesson.id) from Presentation p where p.id=:presentationId")
    Optional<PresentationResponse> getPresentationById(Long presentationId);

    Boolean existsPresentationsByFormatPPT(String formatPPT);
}