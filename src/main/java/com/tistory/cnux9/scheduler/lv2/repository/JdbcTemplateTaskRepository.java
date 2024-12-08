package com.tistory.cnux9.scheduler.lv2.repository;

import com.tistory.cnux9.scheduler.lv2.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv2.entity.Task;
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
    public Task findTaskById(Long id) {
        List<Task> result = jdbcTemplate.query("SELECT * FROM tasks WHERE id = ?;", taskRowMapper(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public List<TaskResponseDto> findAllTasks() {
        return jdbcTemplate.query("SELECT * FROM tasks;", taskResponseDtoRowMapper());
    }

    @Override
    public List<TaskResponseDto> findTasksByName(String name) {
        String query = "SELECT * FROM tasks WHERE user_name = ?;";
        return jdbcTemplate.query(query, taskResponseDtoRowMapper(), name);
    }

    @Override
    public List<TaskResponseDto> findTasksByDate(LocalDate date) {
        String query = "SELECT * FROM tasks WHERE DATE(updated_date_time) = ? ORDER BY updated_date_time DESC;";
        return jdbcTemplate.query(query, taskResponseDtoRowMapper(), date);
    }

    @Override
    public List<TaskResponseDto> findTasksByNameAndDate(String name, LocalDate date) {
        String query = "SELECT * FROM tasks WHERE user_name = ? AND DATE(updated_date_time) = ? ORDER BY updated_date_time DESC;";
        return jdbcTemplate.query(query, taskResponseDtoRowMapper(), name, date);
    }

    @Override
    public int updateTask(Long id, Task task) {
        String query = "UPDATE tasks SET content = ?, user_name = ?, updated_date_time = ? WHERE id = ?;";
        int updatedRowNum = jdbcTemplate.update(query, task.getContent(), task.getName(), task.getUpdatedDateTime(), id);

        return updatedRowNum;
    }

    @Override
    public void deleteTask(Long id) {
        String query = "DELETE FROM tasks WHERE id = ?;";
        jdbcTemplate.update(query, id);
    }

    private RowMapper<TaskResponseDto> taskResponseDtoRowMapper() {
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

    private RowMapper<Task> taskRowMapper() {
        return new RowMapper<Task>() {
            @Override
            public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Task(
                        rs.getLong("id"),
                        rs.getString("content"),
                        rs.getString("user_name"),
                        rs.getString("password"),
                        rs.getTimestamp("created_date_time").toLocalDateTime(),
                        rs.getTimestamp("updated_date_time").toLocalDateTime()
                );
            }
        };
    }
}
