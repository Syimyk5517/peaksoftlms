package com.example.peaksoftlmsb8.repository;

import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.dto.response.group.GroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Boolean existsGroupByName(String name);

    @Query("select new com.example.peaksoftlmsb8.dto.response.group.GroupResponse(" +
            "g.id,g.name,g.description,g.image,g.finishDate,g.createdAt) " +
            "from Group g  order  by g.id desc ")
    Page<GroupResponse> getAllGroups(Pageable pageable);

    @Query("select new com.example.peaksoftlmsb8.dto.response.group.GroupResponse(g.id,g.name,g.description,g.image,g.finishDate,g.createdAt) from Group g where g.id=:groupId")
    Optional<GroupResponse> getGroupById(Long groupId);
}