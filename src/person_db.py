import psycopg2


class DataBase:
    def __init__(self):
        self.DB_URL = "postgres://xdhoxdcbsgxlxx:f4dfbfb50de63f82e758615d34aac4b999f7f6e3914347c3eeb5e8dc1d324e7e@ec2-54-194-180-51.eu-west-1.compute.amazonaws.com:5432/db3120dunkqj65"
        if not self.check_existing_persons_table():
            self.create_table()

    def check_existing_persons_table(self):
        connection = psycopg2.connect(self.DB_URL, sslmode="require")
        cursor = connection.cursor()
        cursor.execute("""SELECT table_name FROM information_schema.tables
               WHERE table_schema = 'public'""")
        for table in cursor.fetchall():
            if table[0] == "persons":
                cursor.close()
                return True
        cursor.close()
        connection.close()
        return False

    def create_table(self):
        table = '''
                    CREATE TABLE persons
                    (
                    id serial not null,
                       name varchar,
                       address varchar,
                       work varchar,
                       age int
                    );
                    '''
        connection = psycopg2.connect(self.DB_URL, sslmode="require")
        cursor = connection.cursor()
        cursor.execute('DROP TABLE IF EXISTS persons;')
        cursor.execute(table)
        cursor.execute('INSERT INTO persons (name, address, work, age)'
            'VALUES (%s, %s, %s, %s)',
            ('Alibek',
             'Moscow, Izmaylovo',
             'Qasqyr',
             22)
            )

        cursor.execute('INSERT INTO persons (name, address, work, age)'
            'VALUES (%s, %s, %s, %s)',
            ('Kuban',
             'Osh',
             'Boeing',
             35)
            )
        connection.commit()
        cursor.close()
        connection.close()



    def db_get(self):
        result = list()
        connection = psycopg2.connect(self.DB_URL, sslmode="require")
        cursor = connection.cursor()
        cursor.execute("SELECT id, name, address, work, age FROM persons")
        record = cursor.fetchall()
        for i in record:
            i = list(i)
            result.append({"id": i[0], "name": i[1], "address": i[2], "work": i[3], "age": i[4]})
        return result



    def db_post(self, person_created):
        result = False
        connection = psycopg2.connect(self.DB_URL, sslmode="require")
        cursor = connection.cursor()
        insert_query = """ INSERT INTO persons (id, name, age, address, work) VALUES (%s, %s, %s, %s, %s) """
        cursor.execute(insert_query, (person_created['id'], person_created['name'], person_created['age'], person_created['address'], person_created['work']))
        connection.commit()
        result = True
        return result



    def db_patch(self, person_updated):
        result = False
        connection = psycopg2.connect(self.DB_URL, sslmode="require")
        cursor = connection.cursor()
        if 'name' in person_updated:
            insert_query = """ UPDATE persons SET name = %s WHERE id = %s"""
            cursor.execute(insert_query, (person_updated['name'], person_updated['id']))
        if 'age' in person_updated:
            insert_query = """ UPDATE persons SET age = %s WHERE id = %s"""
            cursor.execute(insert_query, (person_updated['age'], person_updated['id']))
        if 'address' in person_updated:
            insert_query = """ UPDATE persons SET address = %s WHERE id = %s"""
            cursor.execute(insert_query, (person_updated['address'], person_updated['id']))
        if 'work' in person_updated:
            insert_query = """ UPDATE persons SET work = %s WHERE id = %s"""
            cursor.execute(insert_query, (person_updated['work'], person_updated['id']))
        connection.commit()
        result = True
        return result



    def db_delete(self, person_id):
        result = False
        connection = psycopg2.connect(self.DB_URL, sslmode="require")
        cursor = connection.cursor()
        insert_query = """ DELETE FROM persons WHERE id = '%s' """
        cursor.execute(insert_query, (person_id))
        connection.commit()
        result = True
        return result