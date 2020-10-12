create table `usertab` (
	`usr_id_col` int (11),
	`usr_active_col` int (11),
	`usr_email_col` varchar (765),
	`usr_name_col` varchar (765),
	`usr_pwd_col` varchar (765)
); 
insert into `usertab` (`usr_id_col`, `usr_active_col`, `usr_email_col`, `usr_name_col`, `usr_pwd_col`) values('1','1','admin@gmail.com','ADMIN','$2a$10$HV4fS/z0..npj1T6Zxz2uu6NmJJB/LzeiNUVr4Cl4J3apekIUALY2');

create table `user_roles_tab` (
	`id_join_col` int (11),
	`usr_role_col` varchar (765)
); 
insert into `user_roles_tab` (`id_join_col`, `usr_role_col`) values('1','EMPLOYEE');
insert into `user_roles_tab` (`id_join_col`, `usr_role_col`) values('1','ADMIN');
