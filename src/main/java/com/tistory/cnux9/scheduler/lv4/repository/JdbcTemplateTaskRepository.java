package com.tistory.cnux9.scheduler.lv4.repository;

import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.entity.Task;
import com.tistory.cnux9.scheduler.lv4.resource.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
        Long taskId = key.longValue();
        return new TaskResponseDto(findTaskById(taskId).orElseThrow(() -> new ResourceNotFoundException(taskId)));
    }

    @Override
    public Optional<Task> findTaskById(Long taskId) {
        List<Task> result = jdbcTemplate.query(SELECT_PREFIX + " WHERE t.task_id = ?;", taskRowMapper(), taskId);
        return result.stream().findAny();
    }

    // 확장 편이성이 높은 다건 조건 검색
    @Override
    public List<TaskResponseDto> findTasks(MultiValueMap<String, Object> conditionsMap) {
        log.info("JdbcTemplateTaskRepository.findTasks() is called.");
        if (!conditionsMap.isEmpty()) {
            int length = conditionsMap.size();
            String[] conditions = new String[length];
            Object[] args = new Object[conditionsMap.values().stream().mapToInt(List::size).sum()];
            int keyIndex = 0;
            int argsIndex = 0;
            for (String key : conditionsMap.keySet()) {
                List<Object> values = conditionsMap.get(key);
                String[] orJoin = new String[values.size()];
                int orIndex = 0;
                for (Object value : values) {
                    log.info(key + ": " + value);
                    orJoin[orIndex++] = switch (key) {
                        case "email" -> "u.email = ?";
                        case "date" -> "DATE(t.updated_date_time) = ?";
                        default -> throw new IllegalStateException("Unexpected value: " + key);
                    };
                    args[argsIndex++] = value;
                }
                conditions[keyIndex++] = String.join(" OR ", orJoin);
            }
            String whereClause = " WHERE " + String.join(" AND ", conditions);
            String query = SELECT_PREFIX + whereClause + ORDER_BY_SUFFIX;
            return jdbcTemplate.query(query, taskResponseDtoRowMapper(), args);
        }
        String query = SELECT_PREFIX + ORDER_BY_SUFFIX;
        return jdbcTemplate.query(query, taskResponseDtoRowMapper());
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
