package grp1.touristguide.repository;

import grp1.touristguide.model.Tag;
import grp1.touristguide.model.TouristAttraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class TouristRepository {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;

    // ArrayList to store TouristAttraction objects
    private final List<TouristAttraction> attractions = new ArrayList<>();

    // Read: Get all TouristAttractions
    public List<TouristAttraction> findAll() {
        String sql = "SELECT a.id, a.name, a.description, a.city, t.name AS tag_name "
                + "FROM attractions a "
                + "LEFT JOIN attraction_tags at ON a.id = at.attraction_id "
                + "LEFT JOIN tags t ON at.tag_id = t.id";

        Map<Integer, TouristAttraction> attractionMap = new HashMap<>();

        try (Connection con = DriverManager.getConnection(url, username, pwd);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Iterate over the ResultSet and map it to TouristAttraction objects
            while (rs.next()) {
                int attractionId = rs.getInt("id");

                // If the attraction doesn't exist in the map, create it
                TouristAttraction attraction = attractionMap.get(attractionId);
                if (attraction == null) {
                    attraction = new TouristAttraction();
                    attraction.setId(attractionId);
                    attraction.setName(rs.getString("name"));
                    attraction.setDescription(rs.getString("description"));
                    attraction.setCity(rs.getString("city"));
                    attraction.setTags(new ArrayList<>());  // Initialize the tags list
                    attractionMap.put(attractionId, attraction);
                }

                // Add tags to the attraction
                String tagName = rs.getString("tag_name");  // We refer to "tag_name" because of the AS alias
                if (tagName != null) {
                    Tag tag = new Tag();
                    tag.setName(tagName);
                    attraction.getTags().add(tag);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(attractionMap.values());
    }

    // Read: Get a TouristAttraction by name
    public TouristAttraction findByName(String name) {
        String sql = "SELECT a.id, a.name, a.description, a.city, t.name AS tag_name "
                + "FROM attractions a "
                + "LEFT JOIN attraction_tags at ON a.id = at.attraction_id "
                + "LEFT JOIN tags t ON at.tag_id = t.id "
                + "WHERE a.name = ?";

        TouristAttraction attraction = null;

        try (Connection con = DriverManager.getConnection(url, username, pwd);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, name);  // Set the name parameter in the SQL query

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (attraction == null) {
                        attraction = new TouristAttraction();
                        attraction.setId(rs.getInt("id"));
                        attraction.setName(rs.getString("name"));
                        attraction.setDescription(rs.getString("description"));
                        attraction.setCity(rs.getString("city"));
                        attraction.setTags(new ArrayList<>());
                    }

                    // Add tags to the attraction
                    String tagName = rs.getString("tag_name");
                    if (tagName != null) {
                        Tag tag = new Tag();
                        tag.setName(tagName);
                        attraction.getTags().add(tag);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attraction;
    }

    // Read: Get a TouristAttraction by ID
    public TouristAttraction findById(int id) {
        String sql = "SELECT a.id, a.name, a.description, a.city, t.name AS tag_name "
                + "FROM attractions a "
                + "LEFT JOIN attraction_tags at ON a.id = at.attraction_id "
                + "LEFT JOIN tags t ON at.tag_id = t.id "
                + "WHERE a.id = ?";

        TouristAttraction attraction = null;

        try (Connection con = DriverManager.getConnection(url, username, pwd);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);  // Set the ID parameter in the SQL query

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (attraction == null) {
                        attraction = new TouristAttraction();
                        attraction.setId(rs.getInt("id"));
                        attraction.setName(rs.getString("name"));
                        attraction.setDescription(rs.getString("description"));
                        attraction.setCity(rs.getString("city"));
                        attraction.setTags(new ArrayList<>());
                    }

                    // Add tags to the attraction
                    String tagName = rs.getString("tag_name");
                    if (tagName != null) {
                        Tag tag = new Tag();
                        tag.setName(tagName);
                        attraction.getTags().add(tag);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attraction;
    }

    // Method to save a new attraction
    public void save(TouristAttraction attraction) {
        String insertAttractionSql = "INSERT INTO attractions (name, description, city) VALUES (?, ?, ?)";
        String insertTagSql = "INSERT INTO tags (name) VALUES (?) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id)";
        String insertAttractionTagSql = "INSERT INTO attraction_tags (attraction_id, tag_id) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, username, pwd)) {
            // Start transaction
            con.setAutoCommit(false);

            // Insert the attraction and get the generated ID
            try (PreparedStatement stmtAttraction = con.prepareStatement(insertAttractionSql, Statement.RETURN_GENERATED_KEYS)) {
                stmtAttraction.setString(1, attraction.getName());
                stmtAttraction.setString(2, attraction.getDescription());
                stmtAttraction.setString(3, attraction.getCity());
                stmtAttraction.executeUpdate();

                // Get the generated attraction ID
                ResultSet rs = stmtAttraction.getGeneratedKeys();
                int attractionId = 0;
                if (rs.next()) {
                    attractionId = rs.getInt(1);
                }

                // Insert the tags and establish relationships in the attraction_tags table
                for (Tag tag : attraction.getTags()) {
                    // Insert or get the tag's ID
                    int tagId = 0;
                    try (PreparedStatement stmtTag = con.prepareStatement(insertTagSql, Statement.RETURN_GENERATED_KEYS)) {
                        stmtTag.setString(1, tag.getName());
                        stmtTag.executeUpdate();

                        ResultSet tagRs = stmtTag.getGeneratedKeys();
                        if (tagRs.next()) {
                            tagId = tagRs.getInt(1);
                        }
                    }

                    // Insert the relation between attraction and tag
                    try (PreparedStatement stmtAttractionTag = con.prepareStatement(insertAttractionTagSql)) {
                        stmtAttractionTag.setInt(1, attractionId);
                        stmtAttractionTag.setInt(2, tagId);
                        stmtAttractionTag.executeUpdate();
                    }
                }

                // Commit transaction
                con.commit();
            } catch (SQLException e) {
                con.rollback(); // Rollback if anything goes wrong
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an existing attraction
    public void update(TouristAttraction attraction) {
        String updateAttractionSql = "UPDATE attractions SET name = ?, description = ?, city = ? WHERE id = ?";
        String insertTagSql = "INSERT INTO tags (name) VALUES (?) ON DUPLICATE KEY UPDATE id = LAST_INSERT_ID(id)";
        String deleteAttractionTagsSql = "DELETE FROM attraction_tags WHERE attraction_id = ?";
        String insertAttractionTagSql = "INSERT INTO attraction_tags (attraction_id, tag_id) VALUES (?, ?)";

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, username, pwd);
            con.setAutoCommit(false); // Start transaction

            // Update the attraction's main details
            try (PreparedStatement stmtAttraction = con.prepareStatement(updateAttractionSql)) {
                stmtAttraction.setString(1, attraction.getName());
                stmtAttraction.setString(2, attraction.getDescription());
                stmtAttraction.setString(3, attraction.getCity());
                stmtAttraction.setInt(4, attraction.getId());  // Assuming ID is set in the object
                stmtAttraction.executeUpdate();
            }

            // Remove existing tag relationships for the attraction
            try (PreparedStatement stmtDeleteTags = con.prepareStatement(deleteAttractionTagsSql)) {
                stmtDeleteTags.setInt(1, attraction.getId());
                stmtDeleteTags.executeUpdate();
            }

            // Re-insert the new tags and relationships
            for (Tag tag : attraction.getTags()) {
                int tagId = 0;

                // Insert or retrieve the tag's ID
                try (PreparedStatement stmtTag = con.prepareStatement(insertTagSql, Statement.RETURN_GENERATED_KEYS)) {
                    stmtTag.setString(1, tag.getName());
                    stmtTag.executeUpdate();

                    ResultSet rs = stmtTag.getGeneratedKeys();
                    if (rs.next()) {
                        tagId = rs.getInt(1);
                    }
                }

                // Insert the new relationship between the attraction and the tag
                try (PreparedStatement stmtAttractionTag = con.prepareStatement(insertAttractionTagSql)) {
                    stmtAttractionTag.setInt(1, attraction.getId());
                    stmtAttractionTag.setInt(2, tagId);
                    stmtAttractionTag.executeUpdate();
                }
            }

            con.commit(); // Commit transaction

        } catch (SQLException e) {
            // Rollback in case of an error
            try {
                if (con != null) {
                    con.rollback();  // Rollback if anything goes wrong
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Close the connection if it was opened
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    // Method to delete an existing attraction by ID
    public void deleteById(int id) {
        String deleteAttractionTagsSql = "DELETE FROM attraction_tags WHERE attraction_id = ?";
        String deleteAttractionSql = "DELETE FROM attractions WHERE id = ?";

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, username, pwd);
            con.setAutoCommit(false); // Start transaction

            // Delete the relationships between the attraction and tags
            try (PreparedStatement stmtDeleteTags = con.prepareStatement(deleteAttractionTagsSql)) {
                stmtDeleteTags.setInt(1, id);
                stmtDeleteTags.executeUpdate();
            }

            // Delete the attraction itself
            try (PreparedStatement stmtDeleteAttraction = con.prepareStatement(deleteAttractionSql)) {
                stmtDeleteAttraction.setInt(1, id);
                stmtDeleteAttraction.executeUpdate();
            }

            con.commit(); // Commit transaction

        } catch (SQLException e) {
            // Rollback in case of error
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Close the connection if it was opened
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    // Method to list all tags
    public List<Tag> findAllTags() {
        String sql = "SELECT id, name FROM tags";
        List<Tag> tags = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, username, pwd);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Iterate over the ResultSet and map it to Tag objects
            while (rs.next()) {
                Tag tag = new Tag();
                tag.setId(rs.getInt("id"));  // Assuming Tag has an ID field
                tag.setName(rs.getString("name"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }
}

