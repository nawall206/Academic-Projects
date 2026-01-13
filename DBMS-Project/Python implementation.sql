/* MySQL file for Python Implementation  */

-- create python file 
nano con1.py
import mysql.connector
try:
    connection = mysql.connector.connect(
        host='localhost',
        user='root',
        password='nawal123',
        database='Nadim_22866899',
        ssl_disabled=True
    )

    if connection.is_connected():
        print("Connected Successfully\n")

    cursor = connection.cursor(dictionary=True)

    q1 = "SELECT Title, ReleaseYear FROM FILM LIMIT 5"
    cursor.execute(q1)
    result = cursor.fetchall()

    print("First 5 films:")
    for row in result:
        print(row)

    q2 = "SELECT * FROM NOMINATION WHERE Title = %(t)s"
    cursor.execute(q2, {"t": "Oppenheimer"})
    rows = cursor.fetchall()

    print("\nNominations for Oppenheimer:")
    for r in rows:
        print(r)

    print("\nInsert a test film + nomination")

    cursor.execute(
        "INSERT INTO FILM (Title, ReleaseYear) VALUES (%s, %s)",
        ("Python Demo Film", 2024)
    )

    cursor.execute(
        """INSERT INTO NOMINATION (Year, categoryName, Title, IsWinner)
           VALUES (%s, %s, %s, %s)""",
        (2024, "BEST PICTURE", "Python Demo Film", 0)
    )

    connection.commit()
   
-- Run python file in terminal 
python3 con1.py
