package com.tistory.cnux9.scheduler.lv4.repository;

import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.entity.Task;
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
import java.util.Optional;

@Repository
public class JdbcTemplateTaskRepository implements TaskRepository {
    private final String SELECT_PREFIX = "SELECT * FROM tasks AS t INNER JOIN users AS u ON t.user_id = u.user_id";
    private final String ORDER_BY_SUFFIX = " ORDER BY t.updated_date_time DESC;";
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTaskRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TaskResponseDto saveTask(Task task) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("tasks").usingGeneratedKeyColumns("task_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("content", task.getContent());
        parameters.put("user_id", task.getUserId());
        parameters.put("password", task.getPassword());
        parameters.put("created_date_time", task.getCreatedDateTime());
        parameters.put("updated_date_time", task.getUpdatedDateTime());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new TaskResponseDto(findTaskById(key.longValue()));
    }

    @Override
    public Optional<Task> findTaskById(Long taskId) {
        List<Task> result = jdbcTemplate.query(SELECT_PREFIX + " WHERE t.task_id = ?;", taskRowMapper(), taskId);
        return result.stream().findAny();
    }

    @Override
    public List<TaskResponseDto> findAllTasks() {
        return jdbcTemplate.query(SELECT_PREFIX + ORDER_BY_SUFFIX, taskResponseDtoRowMapper());
    }

    @Override
    public List<TaskResponseDto> findTasksByEmail(String email) {
        String query = SELECT_PREFIX + " WHERE u.email = ?" + ORDER_BY_SUFFIX;
        return jdbcTemplate.query(query, taskResponseDtoRowMapper(), email);
    }

    @Override
    public List<TaskResponseDto> findTasksByDate(LocalDate date) {
        String query = SELECT_PREFIX + " WHERE DATE(t.updated_date_time) = ?" + ORDER_BY_SUFFIX;
        return jdbcTemplate.query(query, taskResponseDtoRowMapper(), date);
    }

    @Override
    public List<TaskResponseDto> findTasksByEmailAndDate(String email, LocalDate date) {
        String query = SELECT_PREFIX + " WHERE u.email = ? AND DATE(t.updated_date_time) = ?" + ORDER_BY_SUFFIX;
        return jdbcTemplate.query(query, taskResponseDtoRowMapper(), email, date);
    }

    @Override
    public int updateTask(Long taskId, Task task) {
        String query = "UPDATE tasks SET user_id = ?, content = ?, updated_date_time = ? WHERE task_id = ?;";
        int updatedRowNum = jdbcTemplate.update(query, task.getUserId(), task.getContent(), task.getUpdatedDateTime(), taskId);
        return updatedRowNum;
    }

    @Override
    public void deleteTask(Long taskId) {
        String query = "DELETE FROM tasks WHERE task_id = ?;";
        jdbcTemplate.update(query, taskId);
    }

    private RowMapper<TaskResponseDto> taskResponseDtoRowMapper() {
        return new RowMapper<TaskResponseDto>() {
            @Override
            public TaskResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TaskResponseDto(
                        rs.getLong("t.task_id"),
                        rs.getLong("t.user_id"),
                        rs.getString("u.email"),
                        rs.getString("t.content"),
                        rs.getTimestamp("t.created_date_time").toLocalDateTime(),
                        rs.getTimestamp("t.updated_date_time").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Task> taskRowMapper() {
        return new RowMapper<Task>() {
            @Override
            public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Task(
                        rs.getLong("t.task_id"),
                        rs.getLong("t.user_id"),
                        rs.getString("u.email"),
                        rs.getString("t.password"),
                        rs.getString("t.content"),
                        rs.getTimestamp("t.created_date_time").toLocalDateTime(),
                        rs.getTimestamp("t.updated_date_time").toLocalDateTime()
                );
            }
        };
    }
}
