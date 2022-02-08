insert into albums(artiestId, naam) values
((select id from artiesten where naam = 'testartiest'), 'testalbum');