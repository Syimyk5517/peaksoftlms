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
        int offset = size * page;
        if (user.getStudent().getGroup() == null) {
            throw new NotFoundException("В это студенте пока что курсы нету");
        } else {
            Group group = user.getStudent().getGroup();
            String sql = """
                    select c.id as course_id,
                           c.name as course_name,
                           c.description as course_description,
                           c.created_at as course_created_at,
                           c.finish_date as course_finish_date
                    from courses c join groups_courses gc on c.id = gc.courses_id where gc.group_id =:? limit ? offset ?;
                    """;
            List<CourseResponse> courseResponses = jdbcTemplate.query(sql, (resultSet, i) -> {
                CourseResponse courseResponse = new CourseResponse();
                courseResponse.setId(resultSet.getLong("course_id"));
                courseResponse.setName(resultSet.getString("course_name"));
                courseResponse.setDescription(resultSet.getString("course_description"));
                courseResponse.setCreatedAt(resultSet.getDate("course_create_date").toLocalDate());
                courseResponse.setFinishDate(resultSet.getDate("course_finish_date").toLocalDate());
                return courseResponse;

            }, group.getId(), size, offset);
            return CoursePaginationResponse.builder().
                    courseResponses(courseResponses)
                    .currentPage(page)
                    .pageSize(size).
                    build();
        }
    }
}
