import psycopg2

conn = psycopg2.connect(dbname="orlab", user="orlab", password="orlab123",
                        host="localhost", port=49153)

with conn:
    with conn.cursor() as cur:
        cur.execute("""
        SELECT current_schema();
        """)
        print(cur.fetchone())

conn.close()
