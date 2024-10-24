-- Insert sample data into 'attractions'
INSERT INTO attractions (name, description, city)
VALUES
    ('Tivoli', 'Amusement park in the middle of Copenhagen city center.', 'Copenhagen'),
    ('The Little Mermaid', 'Iconic bronze statue based on the fairy tale by Hans Christian Andersen, located by the waterfront in Copenhagen.', 'Copenhagen');

-- Insert sample data into 'tags'
INSERT INTO tags (name)
VALUES
    ('Family Friendly'),
    ('Historic'),
    ('Cultural'),
    ('Nature'),
    ('Relaxing');

-- Insert data into 'attraction_tags' to link attractions and tags
-- Linking tags for Tivoli (id=1)
INSERT INTO attraction_tags (attraction_id, tag_id)
VALUES
    (1, 1);  -- Family Friendly

-- Linking tags for The Little Mermaid (id=2)
INSERT INTO attraction_tags (attraction_id, tag_id)
VALUES
    (2, 2),  -- Historic
    (2, 3),  -- Cultural
    (2, 4),  -- Nature
    (2, 5);  -- Relaxing
