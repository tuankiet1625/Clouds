-- Database Setup Script for manager_project
CREATE DATABASE IF NOT EXISTS manager_project;
USE manager_project;

-- 1. Contact Information Table
CREATE TABLE IF NOT EXISTS contact_infor (
    client_id VARCHAR(50) PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    day_of_birth VARCHAR(50),
    gender VARCHAR(50),
    email VARCHAR(255) UNIQUE,
    phonenumber VARCHAR(50),
    avata LONGBLOB
);

-- 2. Login Information Table
CREATE TABLE IF NOT EXISTS login_information (
    client_id VARCHAR(50) PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES contact_infor(client_id) ON DELETE CASCADE
);

-- 3. Request Contacts Table
CREATE TABLE IF NOT EXISTS requestcontacts (
    request_id VARCHAR(50) PRIMARY KEY,
    sender_id VARCHAR(50) NOT NULL,
    receive_id VARCHAR(50) NOT NULL,
    request_date DATETIME NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES contact_infor(client_id) ON DELETE CASCADE,
    FOREIGN KEY (receive_id) REFERENCES contact_infor(client_id) ON DELETE CASCADE,
    UNIQUE KEY unique_request (sender_id, receive_id)
);

-- 4. Contacts Table (Established friendships)
CREATE TABLE IF NOT EXISTS contacts (
    client_id1 VARCHAR(50) NOT NULL,
    client_id2 VARCHAR(50) NOT NULL,
    PRIMARY KEY (client_id1, client_id2),
    FOREIGN KEY (client_id1) REFERENCES contact_infor(client_id) ON DELETE CASCADE,
    FOREIGN KEY (client_id2) REFERENCES contact_infor(client_id) ON DELETE CASCADE
);

-- 5. Images Table for Messages
CREATE TABLE IF NOT EXISTS images (
    image_id VARCHAR(50) PRIMARY KEY,
    image_data LONGBLOB NOT NULL,
    file_image_size INT NOT NULL
);

-- 6. Files Table for Messages
CREATE TABLE IF NOT EXISTS files (
    file_id VARCHAR(50) PRIMARY KEY,
    file_data LONGBLOB NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_size INT NOT NULL
);

-- 7. Messages Table
CREATE TABLE IF NOT EXISTS messages (
    message_id VARCHAR(50) PRIMARY KEY,
    sender_id VARCHAR(50) NOT NULL,
    receive_id VARCHAR(50) NOT NULL,
    type_message VARCHAR(50) NOT NULL,
    message TEXT,
    image VARCHAR(50),
    file_id VARCHAR(50),
    day_send DATETIME NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES contact_infor(client_id) ON DELETE CASCADE,
    FOREIGN KEY (receive_id) REFERENCES contact_infor(client_id) ON DELETE CASCADE,
    FOREIGN KEY (image) REFERENCES images(image_id) ON DELETE SET NULL,
    FOREIGN KEY (file_id) REFERENCES files(file_id) ON DELETE SET NULL
);

-- 8. Projects Table
CREATE TABLE IF NOT EXISTS projects (
    project_id VARCHAR(50) PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    budget VARCHAR(255),
    creator VARCHAR(50) NOT NULL,
    manager VARCHAR(50) NOT NULL,
    FOREIGN KEY (creator) REFERENCES contact_infor(client_id) ON DELETE CASCADE,
    FOREIGN KEY (manager) REFERENCES contact_infor(client_id) ON DELETE CASCADE
);

-- 9. Members Table (Project Team Members)
CREATE TABLE IF NOT EXISTS members (
    member_id VARCHAR(50) NOT NULL,
    project_id VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (member_id, project_id),
    FOREIGN KEY (member_id) REFERENCES contact_infor(client_id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE
);

-- 10. Products Table (Deliverables uploaded for tasks)
CREATE TABLE IF NOT EXISTS products (
    product_id VARCHAR(50) PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    file_data LONGBLOB NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_size INT NOT NULL,
    finish_day DATE NOT NULL,
    creator VARCHAR(50) NOT NULL,
    FOREIGN KEY (creator) REFERENCES contact_infor(client_id) ON DELETE CASCADE
);

-- 11. Tasks Table
CREATE TABLE IF NOT EXISTS tasks (
    task_id VARCHAR(50) PRIMARY KEY,
    classify VARCHAR(50) NOT NULL,
    project_id VARCHAR(50) NOT NULL,
    container_id VARCHAR(50),
    task_name VARCHAR(255) NOT NULL,
    job_requirements TEXT,
    undertaker VARCHAR(50),
    request_date DATE NOT NULL,
    deadline DATE NOT NULL,
    creator VARCHAR(50) NOT NULL,
    product_id VARCHAR(50),
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY (undertaker) REFERENCES contact_infor(client_id) ON DELETE SET NULL,
    FOREIGN KEY (creator) REFERENCES contact_infor(client_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE SET NULL
);

-- 12. Feedbacks Table
CREATE TABLE IF NOT EXISTS feedbacks (
    feedback_id VARCHAR(50) PRIMARY KEY,
    feedback_sender VARCHAR(50) NOT NULL,
    project_id VARCHAR(50) NOT NULL,
    task_id VARCHAR(50) NOT NULL,
    product_id VARCHAR(50) NOT NULL,
    feedback TEXT NOT NULL,
    feedback_date_send DATE NOT NULL,
    FOREIGN KEY (feedback_sender) REFERENCES contact_infor(client_id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY (task_id) REFERENCES tasks(task_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);


-- ==========================================
-- STORED PROCEDURES AND FUNCTIONS DEFINITION
-- ==========================================

DELIMITER $$

-- 1. Register Stored Procedure
DROP PROCEDURE IF EXISTS regester$$
CREATE PROCEDURE regester(
    IN username_in VARCHAR(255),
    IN password_in VARCHAR(255),
    IN fullname_in VARCHAR(255),
    IN day_of_birth_in VARCHAR(50),
    IN gender_in VARCHAR(50),
    IN email_in VARCHAR(255),
    IN phonenumber_in VARCHAR(50)
)
BEGIN
    DECLARE new_id VARCHAR(50);
    SET new_id = LEFT(UUID(), 8);
    INSERT INTO contact_infor(client_id, fullname, day_of_birth, gender, email, phonenumber, avata)
    VALUES (new_id, fullname_in, day_of_birth_in, gender_in, email_in, phonenumber_in, NULL);
    INSERT INTO login_information(client_id, username, password)
    VALUES (new_id, username_in, password_in);
END$$

-- 2. Login Stored Procedure
DROP PROCEDURE IF EXISTS login$$
CREATE PROCEDURE login(
    IN username_in VARCHAR(255),
    IN password_in VARCHAR(255)
)
BEGIN
    SELECT 
        c.client_id AS id,
        c.fullname,
        c.day_of_birth,
        c.gender,
        c.avata,
        c.email,
        c.phonenumber
    FROM login_information l
    JOIN contact_infor c ON c.client_id = l.client_id
    WHERE l.username = username_in AND l.password = password_in;
END$$

-- 3. Change Basic Info
DROP PROCEDURE IF EXISTS changeBasicInformation$$
CREATE PROCEDURE changeBasicInformation(
    IN client_id_in VARCHAR(50),
    IN avata_in LONGBLOB,
    IN fullname_in VARCHAR(255),
    IN day_of_birth_in VARCHAR(50),
    IN gender_in VARCHAR(50)
)
BEGIN
    UPDATE contact_infor
    SET 
        avata = avata_in,
        fullname = fullname_in,
        day_of_birth = day_of_birth_in,
        gender = gender_in
    WHERE client_id = client_id_in;
END$$

-- 4. Change Password
DROP PROCEDURE IF EXISTS changePassword$$
CREATE PROCEDURE changePassword(
    IN client_id_in VARCHAR(50),
    IN new_password_in VARCHAR(255)
)
BEGIN
    UPDATE login_information
    SET password = new_password_in
    WHERE client_id = client_id_in;
END$$

-- 5. Get Client By ID
DROP PROCEDURE IF EXISTS getClientById$$
CREATE PROCEDURE getClientById(
    IN client_id_in VARCHAR(50)
)
BEGIN
    SELECT 
        client_id AS id,
        fullname,
        avata,
        gender,
        day_of_birth,
        email,
        phonenumber
    FROM contact_infor
    WHERE client_id = client_id_in;
END$$

-- 6. Get Contact (Friend List)
DROP PROCEDURE IF EXISTS getContact$$
CREATE PROCEDURE getContact(
    IN client_id_in VARCHAR(50)
)
BEGIN
    SELECT 
        c.client_id AS id,
        c.fullname,
        c.avata,
        c.gender,
        c.day_of_birth,
        c.email,
        c.phonenumber
    FROM contacts con
    JOIN contact_infor c ON (c.client_id = con.client_id2 AND con.client_id1 = client_id_in)
                         OR (c.client_id = con.client_id1 AND con.client_id2 = client_id_in);
END$$

-- 7. Get Client By Email
DROP PROCEDURE IF EXISTS getClientByEmail$$
CREATE PROCEDURE getClientByEmail(
    IN email_in VARCHAR(255)
)
BEGIN
    SELECT 
        client_id AS id,
        fullname,
        avata,
        gender,
        day_of_birth,
        email,
        phonenumber
    FROM contact_infor
    WHERE email = email_in;
END$$

-- 8. Get Interactors (Chat history active list)
DROP PROCEDURE IF EXISTS getInteractor$$
CREATE PROCEDURE getInteractor(
    IN client_id_in VARCHAR(50)
)
BEGIN
    SELECT DISTINCT
        c.client_id AS id,
        c.fullname,
        c.avata,
        c.gender,
        c.day_of_birth,
        c.email,
        c.phonenumber
    FROM (
        SELECT receive_id AS interactor_id FROM messages WHERE sender_id = client_id_in
        UNION
        SELECT sender_id AS interactor_id FROM messages WHERE receive_id = client_id_in
    ) m
    JOIN contact_infor c ON c.client_id = m.interactor_id;
END$$

-- 9. Contact Request Function
DROP FUNCTION IF EXISTS contactRequest$$
CREATE FUNCTION contactRequest(
    sender_id_in VARCHAR(50),
    receive_id_in VARCHAR(50)
)
RETURNS VARCHAR(50)
DETERMINISTIC
BEGIN
    DECLARE new_req_id VARCHAR(50);
    SET new_req_id = LEFT(UUID(), 8);
    INSERT INTO requestcontacts(request_id, sender_id, receive_id, request_date)
    VALUES (new_req_id, sender_id_in, receive_id_in, NOW());
    RETURN new_req_id;
END$$

-- 10. Get Request Contact Details
DROP PROCEDURE IF EXISTS getRequestContact$$
CREATE PROCEDURE getRequestContact(
    IN request_id_in VARCHAR(50)
)
BEGIN
    SELECT 
        r.request_id,
        r.request_date,
        c.client_id,
        c.fullname,
        c.avata,
        c.gender,
        c.day_of_birth,
        c.email,
        c.phonenumber
    FROM requestcontacts r
    JOIN contact_infor c ON c.client_id = r.sender_id
    WHERE r.request_id = request_id_in;
END$$

-- 11. Get Incoming Contact Requests
DROP PROCEDURE IF EXISTS getRequestAddContactPreview$$
CREATE PROCEDURE getRequestAddContactPreview(
    IN client_id_receiver VARCHAR(50)
)
BEGIN
    SELECT 
        r.request_id,
        r.request_date,
        c.client_id,
        c.fullname,
        c.avata,
        c.gender,
        c.day_of_birth,
        c.email,
        c.phonenumber
    FROM requestcontacts r
    JOIN contact_infor c ON c.client_id = r.sender_id
    WHERE r.receive_id = client_id_receiver;
END$$

-- 12. Accept Contact
DROP PROCEDURE IF EXISTS connectContact$$
CREATE PROCEDURE connectContact(
    IN client_id1 VARCHAR(50),
    IN client_id2 VARCHAR(50)
)
BEGIN
    INSERT IGNORE INTO contacts(client_id1, client_id2)
    VALUES (client_id1, client_id2);
END$$

-- 13. Delete Contact Request
DROP PROCEDURE IF EXISTS deleteRequest$$
CREATE PROCEDURE deleteRequest(
    IN request_id_in VARCHAR(50)
)
BEGIN
    DELETE FROM requestcontacts WHERE request_id = request_id_in;
END$$

-- 14. Message Upload Function
DROP FUNCTION IF EXISTS messageUpload$$
CREATE FUNCTION messageUpload(
    sender_id_in VARCHAR(50),
    receiver_id_in VARCHAR(50),
    type_message_in VARCHAR(50),
    message_in TEXT,
    image_data_in LONGBLOB,
    file_data_in LONGBLOB,
    file_name_in VARCHAR(255),
    file_size_in INT
)
RETURNS VARCHAR(50)
DETERMINISTIC
BEGIN
    DECLARE new_msg_id VARCHAR(50);
    DECLARE new_img_id VARCHAR(50) DEFAULT NULL;
    DECLARE new_file_id VARCHAR(50) DEFAULT NULL;
    SET new_msg_id = LEFT(UUID(), 8);
    
    IF type_message_in = 'Image' AND image_data_in IS NOT NULL THEN
        SET new_img_id = LEFT(UUID(), 8);
        INSERT INTO images(image_id, image_data, file_image_size)
        VALUES (new_img_id, image_data_in, OCTET_LENGTH(image_data_in));
    END IF;
    
    IF type_message_in = 'File' AND file_data_in IS NOT NULL THEN
        SET new_file_id = LEFT(UUID(), 8);
        INSERT INTO files(file_id, file_data, file_name, file_size)
        VALUES (new_file_id, file_data_in, file_name_in, file_size_in);
    END IF;
    
    INSERT INTO messages(message_id, sender_id, receive_id, type_message, message, image, file_id, day_send)
    VALUES (new_msg_id, sender_id_in, receiver_id_in, type_message_in, message_in, new_img_id, new_file_id, NOW());
    
    RETURN new_msg_id;
END$$

-- 15. Get Messages between users
DROP PROCEDURE IF EXISTS getMessages$$
CREATE PROCEDURE getMessages(
    IN client_id_in VARCHAR(50),
    IN interactor_id_in VARCHAR(50)
)
BEGIN
    SELECT 
        message_id,
        sender_id,
        receive_id,
        type_message,
        message,
        image,
        file_id,
        day_send
    FROM messages
    WHERE (sender_id = client_id_in AND receive_id = interactor_id_in)
       OR (sender_id = interactor_id_in AND receive_id = client_id_in)
    ORDER BY day_send ASC;
END$$

-- 16. Get Image Details
DROP PROCEDURE IF EXISTS getImage$$
CREATE PROCEDURE getImage(
    IN img_id VARCHAR(50)
)
BEGIN
    SELECT image_id, image_data, file_image_size FROM images WHERE image_id = img_id;
END$$

-- 17. Get File Details
DROP PROCEDURE IF EXISTS getFile$$
CREATE PROCEDURE getFile(
    IN file_id_in VARCHAR(50)
)
BEGIN
    SELECT file_id, file_data, file_name, file_size FROM files WHERE file_id = file_id_in;
END$$

-- 18. Create Project Function
DROP FUNCTION IF EXISTS createProject$$
CREATE FUNCTION createProject(
    name_project_in VARCHAR(255),
    describe_in TEXT,
    expected_end_in DATE,
    budget_in VARCHAR(255),
    creator_in VARCHAR(50)
)
RETURNS VARCHAR(50)
DETERMINISTIC
BEGIN
    DECLARE new_proj_id VARCHAR(50);
    SET new_proj_id = LEFT(UUID(), 8);
    
    INSERT INTO projects(project_id, project_name, description, start_date, end_date, status, budget, creator, manager)
    VALUES (new_proj_id, name_project_in, describe_in, CURDATE(), expected_end_in, 'Planning', budget_in, creator_in, creator_in);
    
    INSERT INTO members(member_id, project_id, role)
    VALUES (creator_in, new_proj_id, 'Manager');
    
    RETURN new_proj_id;
END$$

-- 19. Get Preview Projects (Project Dashboard List)
DROP PROCEDURE IF EXISTS getPreviewProjects$$
CREATE PROCEDURE getPreviewProjects(
    IN client_id_in VARCHAR(50)
)
BEGIN
    SELECT 
        m.role AS member_role,
        p.project_id,
        p.project_name,
        p.start_date,
        p.end_date,
        p.status,
        p.budget,
        p.creator,
        c_creator.fullname AS creator_name,
        p.manager,
        c_manager.fullname AS manager_name,
        (SELECT COUNT(*) FROM members WHERE members.project_id = p.project_id) AS num_members,
        (SELECT COUNT(*) FROM tasks WHERE tasks.project_id = p.project_id) AS num_tasks
    FROM members m
    JOIN projects p ON p.project_id = m.project_id
    JOIN contact_infor c_creator ON c_creator.client_id = p.creator
    JOIN contact_infor c_manager ON c_manager.client_id = p.manager
    WHERE m.member_id = client_id_in;
END$$

-- 20. Create Task Function
DROP FUNCTION IF EXISTS createTask$$
CREATE FUNCTION createTask(
    classify_in VARCHAR(50),
    project_id_in VARCHAR(50),
    container_id_in VARCHAR(50),
    task_name_in VARCHAR(255),
    job_requirements_in TEXT,
    undertaker_in VARCHAR(50),
    deadline_in DATE,
    creator_in VARCHAR(50),
    product_id_in VARCHAR(50)
)
RETURNS VARCHAR(50)
DETERMINISTIC
BEGIN
    DECLARE new_task_id VARCHAR(50);
    SET new_task_id = LEFT(UUID(), 8);
    
    INSERT INTO tasks(task_id, classify, project_id, container_id, task_name, job_requirements, undertaker, request_date, deadline, creator, product_id)
    VALUES (new_task_id, classify_in, project_id_in, container_id_in, task_name_in, job_requirements_in, undertaker_in, CURDATE(), deadline_in, creator_in, product_id_in);
    
    RETURN new_task_id;
END$$

-- 21. Get Preview Tasks (Project Tasks List)
DROP PROCEDURE IF EXISTS getPreviewTasks$$
CREATE PROCEDURE getPreviewTasks(
    IN project_id_in VARCHAR(50)
)
BEGIN
    SELECT 
        task_id,
        classify,
        project_id,
        container_id,
        task_name
    FROM tasks
    WHERE project_id = project_id_in;
END$$

-- 22. Get Full Task Details
DROP PROCEDURE IF EXISTS getTask$$
CREATE PROCEDURE getTask(
    IN task_id_in VARCHAR(50)
)
BEGIN
    SELECT 
        task_id,
        classify,
        project_id,
        container_id,
        task_name,
        job_requirements,
        undertaker,
        request_date,
        deadline,
        creator,
        product_id
    FROM tasks
    WHERE task_id = task_id_in;
END$$

-- 23. Get Project Members
DROP PROCEDURE IF EXISTS getMembers$$
CREATE PROCEDURE getMembers(
    IN project_id_in VARCHAR(50)
)
BEGIN
    SELECT 
        m.member_id,
        c.fullname,
        c.email,
        c.phonenumber,
        c.avata,
        m.role
    FROM members m
    JOIN contact_infor c ON c.client_id = m.member_id
    WHERE m.project_id = project_id_in;
END$$

-- 24. Create Product (Upload task work/deliverable)
DROP FUNCTION IF EXISTS createProduct$$
CREATE FUNCTION createProduct(
    task_id_in VARCHAR(50),
    product_name_in VARCHAR(255),
    file_data_in LONGBLOB,
    file_name_in VARCHAR(255),
    file_size_in INT,
    creator_in VARCHAR(50)
)
RETURNS VARCHAR(50)
DETERMINISTIC
BEGIN
    DECLARE new_prod_id VARCHAR(50);
    SET new_prod_id = LEFT(UUID(), 8);
    
    INSERT INTO products(product_id, product_name, file_data, file_name, file_size, finish_day, creator)
    VALUES (new_prod_id, product_name_in, file_data_in, file_name_in, file_size_in, CURDATE(), creator_in);
    
    UPDATE tasks 
    SET product_id = new_prod_id
    WHERE task_id = task_id_in;
    
    RETURN new_prod_id;
END$$

-- 25. Get Product Preview
DROP PROCEDURE IF EXISTS getProductPreview$$
CREATE PROCEDURE getProductPreview(
    IN product_id_in VARCHAR(50)
)
BEGIN
    SELECT 
        product_name,
        file_name,
        file_size,
        finish_day,
        creator
    FROM products
    WHERE product_id = product_id_in;
END$$

-- 26. Add Project Member
DROP PROCEDURE IF EXISTS newMember$$
CREATE PROCEDURE newMember(
    IN client_id_in VARCHAR(50),
    IN project_id_in VARCHAR(50)
)
BEGIN
    INSERT IGNORE INTO members(member_id, project_id, role)
    VALUES (client_id_in, project_id_in, 'Developer');
END$$

-- 27. Get Feedbacks for user
DROP PROCEDURE IF EXISTS getFeedBacks$$
CREATE PROCEDURE getFeedBacks(
    IN client_id_receive VARCHAR(50)
)
BEGIN
    SELECT 
        c.client_id,
        c.fullname,
        c.avata,
        c.gender,
        c.day_of_birth,
        c.email,
        c.phonenumber,
        f.feedback_id,
        f.project_id,
        f.task_id,
        f.product_id,
        prod.creator AS product_creator,
        p.project_name,
        t.task_name,
        f.feedback,
        f.feedback_date_send
    FROM feedbacks f
    JOIN contact_infor c ON c.client_id = f.feedback_sender
    JOIN projects p ON p.project_id = f.project_id
    JOIN tasks t ON t.task_id = f.task_id
    JOIN products prod ON prod.product_id = f.product_id
    WHERE prod.creator = client_id_receive OR p.manager = client_id_receive;
END$$

-- 28. New Feedback Function
DROP FUNCTION IF EXISTS newFeedBack$$
CREATE FUNCTION newFeedBack(
    feedback_sender_in VARCHAR(50),
    project_id_in VARCHAR(50),
    task_id_in VARCHAR(50),
    product_id_in VARCHAR(50),
    feedback_in TEXT
)
RETURNS VARCHAR(50)
DETERMINISTIC
BEGIN
    DECLARE new_fb_id VARCHAR(50);
    SET new_fb_id = LEFT(UUID(), 8);
    
    INSERT INTO feedbacks(feedback_id, feedback_sender, project_id, task_id, product_id, feedback, feedback_date_send)
    VALUES (new_fb_id, feedback_sender_in, project_id_in, task_id_in, product_id_in, feedback_in, CURDATE());
    
    RETURN new_fb_id;
END$$

-- 29. Get Single Feedback
DROP PROCEDURE IF EXISTS getFeedBack$$
CREATE PROCEDURE getFeedBack(
    IN feedback_id_in VARCHAR(50)
)
BEGIN
    SELECT 
        c.client_id,
        c.fullname,
        c.avata,
        c.gender,
        c.day_of_birth,
        c.email,
        c.phonenumber,
        f.feedback_id,
        f.project_id,
        f.task_id,
        f.product_id,
        prod.creator AS product_creator,
        p.project_name,
        t.task_name,
        f.feedback,
        f.feedback_date_send
    FROM feedbacks f
    JOIN contact_infor c ON c.client_id = f.feedback_sender
    JOIN projects p ON p.project_id = f.project_id
    JOIN tasks t ON t.task_id = f.task_id
    JOIN products prod ON prod.product_id = f.product_id
    WHERE f.feedback_id = feedback_id_in;
END$$

DELIMITER ;
