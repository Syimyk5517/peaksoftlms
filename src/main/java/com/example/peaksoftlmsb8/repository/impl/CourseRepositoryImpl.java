package com.example.peaksoftlmsb8.repository.impl;

import com.example.peaksoftlmsb8.db.entity.Group;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.dto.response.course.CoursePaginationResponse;
import com.example.peaksoftlmsb8.dto.response.course.CourseResponse;
import com.example.peaksoftlmsb8.exception.NotFoundException;
import com.example.peaksoftlmsb8.repository.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepo {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public CoursePaginationResponse getAllCourses(User user, int size, int page) {
        int offset = (page - 1) * size;
        if (user.getStudent().getGroup() == null) {
            throw new NotFoundException("В это студенте пока что курсы нету");
        } else {
            Group group = user.getStudent().getGroup();
            String sql = """
                    SELECT c.id AS course_id,
                           c.name AS course_name,
                           c.description AS course_description,
                           c.created_at AS course_created_at,
                           c.finish_date AS course_finish_date
                    FROM courses c
                    JOIN courses_groups gc ON c.id = gc.courses_id
                    WHERE gc.groups_id = ?
                    LIMIT ? OFFSET ?;
                    """;
            List<CourseResponse> courseResponses = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
                CourseResponse courseResponse = new CourseResponse();
                courseResponse.setId(resultSet.getLong("course_id"));
                courseResponse.setName(resultSet.getString("course_name"));
                courseResponse.setDescription(resultSet.getString("course_description"));
                courseResponse.setCreatedAt(resultSet.getDate("course_created_at").toLocalDate());
                courseResponse.setFinishDate(resultSet.getDate("course_finish_date").toLocalDate());
                return courseResponse;
            }, group.getId(), size, offset);

            return CoursePaginationResponse.builder()
                    .courseResponses(courseResponses)
                    .currentPage(page)
                    .pageSize(size)
                    .build();
        }
    }
}
