/*
 * Syed Ammar Waseem
 * Online website
 * SDNE(Software Development Network Engineering)
 *
 */
package ca.ammar.website.application.database;

import ca.ammar.website.application.beans.Course;
import ca.ammar.website.application.beans.Evaluation;
import ca.ammar.website.application.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class DatabaseAccess {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private void namedParameterJdbcTemplate (NamedParameterJdbcTemplate jdbc){
       this.jdbc = jdbc;
    }

    public User findUserAccount(String email){
        String sql = "SELECT * FROM users WHERE username=:email;";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);
        ArrayList<User> users = (ArrayList<User>) jdbc.query
                (sql, params, new BeanPropertyRowMapper<User>(User.class));
        if (users.size() > 0){
            return users.get(0);
        }else
            return null;
    }

    public List<String> getRolesByID(long userId){
        String sql = "SELECT user_role.userid, roles.rolename FROM user_role, roles WHERE user_role.roleid = roles.roleid AND user_role.userid=:userid;";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userid", userId);
        ArrayList<String> roles = new ArrayList<String>();
        List<Map<String, Object>> rows = jdbc.queryForList(sql, params);
        for(Map<String, Object> row : rows){
            roles.add((String)row.get("roleName"));
        }
        return roles;
    }

    public long getUserIdByName(String name){
        String sql = "Select userId from users where username=:name;";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        long id = 0;
        List<Map<String, Object>> rows = jdbc.queryForList(sql, params);
        for(Map<String, Object> row : rows){
            id = (int) row.get("userId");
        }
        return id;
    }

    // adds the given Evaluation object to the database
    public Integer addEvaluation(Evaluation eval){
        // builds the string "code instruction for sql" to add to the data base
        String sql = "INSERT INTO evaluations (`title`, `course`, `grade`, `max`," +
                " `weight`, `duedate`) VALUES (:title, :course, :grade, " +
                ":max, :weight, :duedate);";

        // map params to the Given table
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", eval.getTitle())
                .addValue("course", eval.getCourse())
                .addValue("grade", eval.getGrade())
                .addValue("max", eval.getMax())
                .addValue("weight", eval.getWeight())
                .addValue("duedate", eval.getDueDate());

        // holds the int id value for eval id
        KeyHolder key = new GeneratedKeyHolder();

        // executes the query and returns the number of lines affected/added
        jdbc.update(sql, params, key);
        // returns the generated id of evaluation
        return key.getKey().intValue();
    }

    // Deletes a Evaluation object from the database with the given id
    public void deleteEvaluation(int id){
        // builds the string
        String sql = "DELETE from evaluations where id=:id;";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",id);
        jdbc.update(sql, params);
    }

    /* Takes a Evaluation object and updates from evaluations where the id
     * is the same returns number of fields affected in our case either 1 or 0
     */
    public int editEvaluation(Evaluation eval){
        // builds the string "code instruction for sql" to add to the data base
        String sql = "UPDATE evaluations SET title=:title, course=:course, " +
                "grade=:grade, max=:max, weight=:weight, duedate=:duedate" +
                " WHERE id=:id;";

        // map params to the Given table
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", eval.getTitle())
                .addValue("course", eval.getCourse())
                .addValue("grade", eval.getGrade())
                .addValue("max", eval.getMax())
                .addValue("weight", eval.getWeight())
                .addValue("duedate", eval.getDueDate())
                .addValue("id", eval.getId());

        // executes the query and returns the number of lines affected/added
        return jdbc.update(sql,params);
    }

    /* takes an id and searches from the database for the same id
     * and if it succeeds it returns the Evaluation Objects and we return the
     */
    public Evaluation getEvaluation(int id){
        // builds the string "code instruction for sql" to add to the data base
        String sql = "SELECT * FROM evaluations WHERE id=:id;";

        // map params to the Given table
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        // stores the retrieved Evaluation object into an array
        ArrayList<Evaluation> evaluationArrayList = (ArrayList<Evaluation>)
                jdbc.query(sql, params,
                        new BeanPropertyRowMapper<>(Evaluation.class));

        // returns the retrieved Evaluation object if it has 1
        if (evaluationArrayList.size() > 0) {
            return evaluationArrayList.get(0);
        } // else returns null
        return null;
    }

    public List<Course> getCoursesByStudentId(int id){
        // sql string to get courses from database
        String sql = "SELECT * FROM courses WHERE studentId=:id;";

        // map params to the Given table
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbc.query(sql, params, new BeanPropertyRowMapper<>(Course.class));
    }

    /* take the code and searches from the database with the same code
     * and if it succeeds it returns the Course Objects and we return the
     * object found
     */
    public Course getCourse(String code){
        // builds the string "code instruction for sql" to add to the data base
        String sql = "SELECT * FROM courses WHERE code=:code;";
        // map params to the Given table
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", code);
        // stores the retrieved Course object into an array
        ArrayList<Course> courseArrayList = (ArrayList<Course>)
                jdbc.query(sql, params,
                        new BeanPropertyRowMapper<Course>(Course.class));

        // returns the retrieved Course object
        return courseArrayList.get(0);
    }


    /* no parameters just returns all evaluations currently in the database
     * in an Arraylist as Evaluation objects
     */
    public ArrayList<Evaluation> getEvaluations(){
        // builds the sql to get evaluations from the database
        String sql = "SELECT * FROM evaluations ORDER BY duedate;";

        // returns Evaluation objects in an array
        return (ArrayList<Evaluation>)jdbc.query
                (sql, new BeanPropertyRowMapper<>(Evaluation.class));
    }

    /* no parameters just returns all courses currently in the database
     * in an Arraylist as Course objects
     */
    public ArrayList<Course> getCourses(){
        // builds the string to get all the courses from the database order by title
        String sql = "SELECT * FROM courses ORDER BY title;";
        // stores the result from the query to the array and returns it
        return (ArrayList<Course>)jdbc.query(sql,
                new BeanPropertyRowMapper<>(Course.class));
    }


    /* Adds the given Course object to the database and returns number of rows
     * affected/added.
     */
    public int addCourse(Course course) {
        // builds the sql query to add a Course object to the database
        String sql = "INSERT INTO courses (code, title, credits, complete" +
                ", term, finalGrade) VALUES" +
                " (:code, :title, :credits, :complete, :term, :finalGrade);";
        // maps the parameters to the correct place
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", course.getCode())
                .addValue("title", course.getTitle())
                .addValue("credits", course.getCredits())
                .addValue("complete", course.isComplete())
                .addValue("term", course.getTerm())
                .addValue("finalGrade", course.getFinalGrade());

        // executes the jdbc update and returns the number of fields added
        return jdbc.update(sql, params);
    }
}
