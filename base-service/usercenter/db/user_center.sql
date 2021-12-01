CREATE DATABASE user_center;

USE user_center;

CREATE TABLE `user` (
                        `id` bigint NOT NULL,
                        `username` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
                        `password` varchar(128) COLLATE utf8mb4_general_ci NOT NULL,
                        `dept_id` bigint DEFAULT NULL,
                        `status` int DEFAULT '0',
                        `admin_flag` int DEFAULT '0',
                        `del_flag` int DEFAULT '0',
                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                        `create_user` bigint DEFAULT NULL,
                        `login_time` datetime DEFAULT NULL,
                        `login_ip` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `role` (
                        `id` bigint NOT NULL,
                        `role_name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
                        `role_code` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
                        `permissible_url` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
                        `status` int DEFAULT '0',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `dept` (
                        `id` bigint NOT NULL,
                        `dept_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
                        `parent_id` bigint NOT NULL DEFAULT '1',
                        `status` int DEFAULT '0',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `user_role` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `role_id` bigint NOT NULL,
                             `user_id` bigint NOT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;