CREATE TABLE permissions (
  id SMALLINT NOT NULL, 
  description VARCHAR(255), 
  name VARCHAR(255) NOT NULL, 
  CONSTRAINT permissions_pkey PRIMARY KEY (id), 
  CONSTRAINT uk_ppermissions_name UNIQUE (name)
);

CREATE TABLE role (
  id SMALLINT NOT NULL, 
  description VARCHAR(255), 
  is_default BOOL NOT NULL, 
  name VARCHAR(255) NOT NULL, 
  CONSTRAINT role_pkey PRIMARY KEY (id), 
  CONSTRAINT uk_role_name UNIQUE (name)
);

CREATE TABLE role_permissions (
  role_id BIGINT NOT NULL, permission_id SMALLINT NOT NULL
);

CREATE TABLE users (
  id SMALLINT NOT NULL, 
  email_id VARCHAR(255) NOT NULL, 
  first_name VARCHAR(255), 
  last_name VARCHAR(255), 
  password VARCHAR(255) NOT NULL, 
  CONSTRAINT users_pkey PRIMARY KEY (id), 
  CONSTRAINT uk_users_email_id UNIQUE (email_id)
);

CREATE TABLE users_roles (
  user_id BIGINT NOT NULL, role_id BIGINT NOT NULL
);

ALTER TABLE 
  role_permissions 
ADD 
  CONSTRAINT role_permissions_pkey PRIMARY KEY (role_id, permission_id);
  
  
ALTER TABLE 
  users_roles 
ADD 
  CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);
  
  
ALTER TABLE 
  role_permissions 
ADD 
  CONSTRAINT fkegdk29eiy7mdtefy5c7eirr6e FOREIGN KEY (permission_id) REFERENCES permissions (id) ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE 
  role_permissions 
ADD 
  CONSTRAINT fklodb7xh4a2xjv39gc3lsop95n FOREIGN KEY (role_id) REFERENCES role (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE 
  users_roles 
ADD 
  CONSTRAINT fkt4v0rrweyk393bdgt107vdx0x FOREIGN KEY (role_id) REFERENCES role (id) ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE 
  users_roles 
ADD 
  CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
