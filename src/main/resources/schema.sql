-- Create the 'attractions' table
CREATE TABLE attractions
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    city        VARCHAR(255) NOT NULL
);

-- Create the 'tags' table
CREATE TABLE tags
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Create the junction table 'attraction_tags'
CREATE TABLE attraction_tags
(
    attraction_id INT,
    tag_id        INT,
    PRIMARY KEY (attraction_id, tag_id),
    FOREIGN KEY (attraction_id) REFERENCES attractions (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE
);
