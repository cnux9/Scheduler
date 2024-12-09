package com.tistory.cnux9.scheduler.lv4.repository;

import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.entity.Task;
import com.tistory.cnux9.scheduler.lv4.resource.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class JdbcTemplateTaskRepository implements TaskRepository {
    private final String SELECT_CLAUSE = "SELECT * FROM tasks AS t LEFT JOIN users AS u ON t.user_id = u.user_id";
    private final String ORDER_BY_CLAUSE = " ORDER BY t.updated_date_time DESC";

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
        parameters.put("user_id", task.getUserId() );
        parameters.put("password", task.getPassword());
        parameters.put("created_date_time", task.getCreatedDateTime());
        parameters.put("updated_date_time", task.getUpdatedDateTime());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        Long taskId = key.longValue();
        return new TaskResponseDto(findTaskById(taskId).orElseThrow(() -> new ResourceNotFoundException(taskId)));
    }

    @Override
    public Optional<Task> findTaskById(Long taskId) {
        List<Task> result = jdbcTemplate.query(SELECT_CLAUSE + " WHERE t.task_id = ?;", taskRowMapper(), taskId);
        return result.stream().findAny();
    }

/**
 * 확장 편이성이 높은 다건 조건 검색<br>
 */
    @Override
    public Slice<TaskResponseDto> findTasks(Pageable pageable, MultiValueMap<String, String> conditionsMap) {
        // SELECT ~ FROM ~
        StringBuilder sb = new StringBuilder(SELECT_CLAUSE);
        // WHERE ~
        if (!conditionsMap.isEmpty()) {
            sb.append(getWhereClause(conditionsMap));
        }
        // ORDER BY ~
        sb.append(ORDER_BY_CLAUSE);
        // LIMIT ~ OFFSET ~
        if (pageable!=null) {
            sb.append(getLimitClause(pageable));
        }
        sb.append(";");

        List<TaskResponseDto> results = jdbcTemplate.query(sb.toString(), taskResponseDtoRowMapper());
        return new SliceImpl<>(results);
    }

/**
 * 동일 속성에 여러가지 조건이 있으면 Separator = OR 로 join한 후에<br>
 * 다른 속성 끼리는 Separator = AND 로 join
 */
    private String getWhereClause(MultiValueMap<String, String> conditionsMap) {
        List<String> andConditions = new ArrayList<>();
        for (Map.Entry<String, List<String>> pair : conditionsMap.entrySet()) {
            String key = pair.getKey();
            String condition;
            switch (key) {
                case "name":
                    condition = "u.name = ";
                    break;
                case "email":
                    condition = "u.email = ";
                    break;
                case "date":
                    condition = "DATE(t.updated_date_time) = ";
                    break;
                case "page", "size":
                    continue;
                default :
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Distinct parameter: " + key);
            }
            List<String> values = pair.getValue();
            String orJoinedCondition = "( " + values.stream().map(s -> condition + "\""+ s +"\"").collect(Collectors.joining(" OR ")) + " )";
            andConditions.add(orJoinedCondition);
        }
        return " WHERE " + String.join(" AND ", andConditions);
    }

    private String getLimitClause(Pageable pageable) {
        return " LIMIT " + pageable.getPageSize() +" OFFSET " + pageable.getOffset();
    }

    @Override
    public int updateTask(Long taskId, Task task) {
        String query = "UPDATE tasks SET content = ?, updated_date_time = ? WHERE task_id = ?;";
        int updatedRowNum = jdbcTemplate.update(query, task.getContent(), task.getUpdatedDateTime(), taskId);
        return updatedRowNum;
    }

    @Override
    public int updateUser(Long userId, String name) {
        String query = "UPDATE users SET user_name = ? WHERE user_id = ?;";
        int updatedRowNum = jdbcTemplate.update(query, name==null ? "" : name, userId);
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
                        rs.getString("u.user_name"),
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
                        rs.getString("u.user_name"),
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
