-- insert into flights.user 
-- --(bdate, email, fname, lname, psw, usr, role, remaining_flights)
-- VALUES (1,"2001-01-01", "mr1@mr.com", "mar", "rist", "123asd",2, "Admin", "mr_mr"),
-- (2,"2002-01-01", "mr2@mr.com", "mar", "rist", "123asd",2, "Admin", "mr_mr"),
-- (3,"2003-01-01", "mr3@mr.com", "mar", "rist", "123asd",2, "Admin", "mr_mr");





insert into flights.flight 
(flight_id, departure, destination, departure_time, departur_date, airline)
VALUES 
(2,"BEG", 'TIA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY),  concat ('air ',FLOOR(RAND()*7 )+1) ),
(3,"TIA", 'BUD', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY),  concat ('air ',FLOOR(RAND()*7 )+1) ),
(4,"BUD", 'TIA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY),  concat ('air ',FLOOR(RAND()*7 )+1) ),
(5,"TIA", 'VIE', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY),  concat ('air ',FLOOR(RAND()*7 )+1) ),
(6,"VIE", 'TIA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY),  concat ('air ',FLOOR(RAND()*7 )+1) ),
(7,"TIA", 'TSF', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY),  concat ('air ',FLOOR(RAND()*7 )+1) ),
(8,"TSF", 'TIA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY),  concat ('air ',FLOOR(RAND()*7 )+1) ),
(9,"TIA", 'BLQ', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY),  concat ('air ',FLOOR(RAND()*7 )+1) ),
(10,"BLQ", 'TIA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(11,"TIA", 'IST', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(12,"IST", 'TIA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(13,"TIA", 'NCE', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(14,"TIA", 'LYS', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(15,"TIA", 'BGY', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(16,"BGY", 'TIA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(17,"BGY", 'FCO', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(18,"FCO", 'BGY', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(19,"BGY", 'LYS', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(20,"BGY", 'MRS', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(21,"BGY", 'CAG', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(22,"BGY", 'NAP', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(23,"BGY", 'BEG', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(24,"BGY", 'VIE', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(25,"BGY", 'SKG', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(26,"BGY", 'ATH', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(27,"BGY", 'BUD', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(28,"CDG", 'HLR', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(29,"BSL", 'RMA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(30,"MRS", 'RMB', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(31,"IBZ", 'RMC', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(32,"PRM", 'RMD', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(33,"CRL", 'KMA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(34,"LNT", 'KMB', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(35,"ORK", 'RMC', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(36,"WAW", 'RBD', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(37,"MAH", 'RAA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(38,"PMI", 'ARB', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(39,"MLA", 'RAA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(40,"MLA", 'CAG', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(41,"MLA", 'TUN', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(42,"MLA", 'CTA', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(43,"MLA", 'CHQ', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(44,"MLA", 'ATH', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY), concat ('air ',FLOOR(RAND()*7 )+1)  ),
(45,"MLA", 'SKG', TIME( now() + interval floor((23*59*59)*rand()) second) , ADDDATE(CURRENT_DATE , INTERVAL FLOOR(RAND() * 400) DAY),  concat ('air ',FLOOR(RAND()*7 )+1)	);
