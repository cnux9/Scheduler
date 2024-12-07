package com.tistory.cnux9.scheduler.lv1.repository;

import com.tistory.cnux9.scheduler.lv1.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv1.entity.Task;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateTaskRepository implements TaskRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTaskRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TaskResponseDto saveTask(Task task) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("tasks").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("content", task.getContent());
        parameters.put("user_name", task.getName());
        parameters.put("password", task.getPassword());
        parameters.put("created_date_time", task.getCreatedDateTime());
        parameters.put("updated_date_time", task.getUpdatedDateTime());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new TaskResponseDto(
                key.longValue(),
                task.getContent(),
                task.getName(),
                task.getCreatedDateTime(),
                task.getUpdatedDateTime()
        );
    }

    @Override
    public TaskResponseDto findTaskById(Long id) {
        List<TaskResponseDto> result = jdbcTemplate.query("SELECT * FROM tasks WHERE id = ?;", taskRowMapper(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public List<TaskResponseDto> findAllTasks() {
        return jdbcTemplate.query("SELECT * FROM tasks;", taskRowMapper());
    }

    @Override
    public List<TaskResponseDto> findTasksByName(String name) {
        String query = "SELECT * FROM tasks WHERE user_name = ?;";
        return jdbcTemplate.query(query, taskRowMapper(), name);
    }

    @Override
    public List<TaskResponseDto> findTasksByDate(LocalDate date) {
        String query = "SELECT * FROM tasks WHERE DATE(updated_date_time) = ? ORDER BY updated_date_time DESC;";
        return jdbcTemplate.query(query, taskRowMapper(), date);
    }

    @Override
    public List<TaskResponseDto> findTasksByNameAndDate(String name, LocalDate date) {
        String query = "SELECT * FROM tasks WHERE user_name = ? AND DATE(updated_date_time) = ? ORDER BY updated_date_time DESC;";
        return jdbcTemplate.query(query, taskRowMapper(), name, date);
    }

    private RowMapper<TaskResponseDto> taskRowMapper() {
        return new RowMapper<TaskResponseDto>() {
            @Override
            public TaskResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TaskResponseDto(
                        rs.getLong("id"),
                        rs.getString("content"),
                        rs.getString("user_name"),
                        rs.getTimestamp("created_date_time").toLocalDateTime(),
                        rs.getTimestamp("updated_date_time").toLocalDateTime()
                );
            }
        };
    }
}
