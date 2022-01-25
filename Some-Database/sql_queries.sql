
--1
SELECT Nazwisko FROM Uczniowie, Oceny
WHERE Oceny.ocena >= ( SELECT SUM(ocena)/count(ocena) FROM Oceny);


--2
SELECT Nazwisko FROM Uczniowie, Oceny a
WHERE a.ocena < ( SELECT AVG(ocena) FROM Oceny b WHERE a.ocena <= b.ocena);

--3
SELECT ocena FROM Oceny
GROUP BY ocena
HAVING ocena > (SELECT AVG(ocena) FROM Oceny WHERE Przedmiot = 'Matematyka');

--4
SELECT ocena AS ocenki FROM Oceny a
GROUP BY ocena
HAVING a.ocena >= (SELECT MAX(ocena) FROM Oceny b WHERE a.ocena > b.ocena);

--5
SELECT Nazwisko FROM Nauczyciele 
WHERE EXISTS (SELECT Klasa FROM Klasa WHERE Klasa = '2b');



--1
CREATE PROCEDURE dodaj_ucznia 
   @Imie VARCHAR(40),
   @Nazwisko VARCHAR(40),
   @Klasa INT 
AS BEGIN 
   DECLARE @id INT
   SELECT @id = ISNULL(MAX(empno), 0) + 1 FROM Uczniowie
   INSERT INTO Uczniowie (id, Imie, Nazwisko, Klasa_Klasa)
   VALUES (@id, @Imie, @Nazwisko, @Klasa)
END

--2
DECLARE kursor CURSOR FOR
SELECT Nazwisko, Imie FROM Uczniowie, Oceny
WHERE Ocena > 3
DECLARE @nazwisko VARCHAR(40), @ocena INT
PRINT 'Uczniowie z ocenami wiêkszymi ni¿ 3:'
OPEN kursor 
FETCH NEXT FROM kursor INTO @nazwisko, @ocena 
WHILE @@FETCH_STATUS = 0
   BEGIN 
      PRINT @nazwisko + ' ' + Cast(@ocena As Varchar)
      FETCH NEXT FROM kursor INTO @nazwisko, @ocena 
   END 
CLOSE kursor 
DEALLOCATE kursor




--1
CREATE TRIGGER wyzwalacz ON Oceny
FOR INSERT
AS
DECLARE @Ocena INT
SELECT @Ocena = Ocena FROM Oceny
IF @Ocena <= 0 OR @Ocena >= 6
BEGIN
	ROLLBACK
	RAISERROR('Nie mo¿na wstawiæ takiej oceny',0,0)
END


--2
CREATE TRIGGER wyzwalacz ON Klasa
FOR UPDATE
AS
SELECT @Data_tworzenia = Data_tworzenia FROM Klasa
SELECT @Data_Zakonczenia = Data_zakonczenia FROM Klasa
IF @Data_tworzenia = @Data_Zakonczenia
	UPDATE Klasa SET @Data_Zakonczenia = NULL WHERE Klasa = @id
