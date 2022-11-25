import os
from flask import Flask, request, Response
from flask import make_response, jsonify
from person_db import DataBase
from flask import abort

app = Flask(__name__)



@app.route('/api/v1/persons', methods=['GET'])
def get_persons():
    db = DataBase()
    result = db.db_get()
    return result, 200



@app.route('/api/v1/persons/<int:person_id>', methods=['GET'])
def get_person(person_id):
    db = DataBase()
    persons = db.db_get()
    person = list(filter(lambda t: t['id'] == person_id, persons))
    if len(person) == 0:
        Response(status=404)
    return make_response(jsonify(person[0]), 200)



@app.route('/api/v1/persons', methods=['POST'])
def post_person():
    db = DataBase()
    persons = db.db_get()
    if not request.json:
        return Response(status=404)
    person_id = 0
    if len(persons) == 0:
        person_id = 1
    else:
        for i in persons:
            if i['id'] > person_id:
                person_id = i['id']
        person_id = person_id + 1
    person_created = {
        'id': person_id,
        'name': request.json['name'],
        'age': request.json['age'],
        'address': request.json['address'],
        'work': request.json['work']
    }
    db.db_post(person_created)
    return jsonify({}), 201, {"Location": "/api/v1/persons/" + str(person_id)}



@app.route('/api/v1/persons/<int:person_id>', methods=['PATCH'])
def patch_person(person_id):
    if not request.json:
        Response(status=404)
    db = DataBase()
    persons = db.db_get()
    person = list(filter(lambda t: t['id'] == person_id, persons))
    if len(person) == 0:
        return Response(status=404)
    patched_person = {
        'id': person_id
    }
    request_data = request.get_json()
    if 'name' in request_data:
        patched_person['name'] = request.json['name']
    if 'age' in request_data:
        patched_person['age'] = request.json['age']
    if 'address' in request_data:
        patched_person['address'] = request.json['address']
    if 'work' in request_data:
        patched_person['work'] = request.json['work']
    if db.db_patch(patched_person):
        persons_updateted = db.db_get()
        updated_person = list(filter(lambda t: t['id'] == person_id, persons_updateted))
        if len(updated_person) == 0:
            return Response(status=404)
        return make_response(jsonify(updated_person[0]), 200)
    else:
        return Response(status=404)



@app.route('/api/v1/persons/<int:person_id>', methods=['DELETE'])
def delete_person(person_id):
    db = DataBase()
    persons = db.db_get()
    for p, person in enumerate(persons):
        if persons[p]['id'] == person_id:
            db.db_delete([person_id])
            break
        if (len(persons)) - 1 == p:
            return Response(status=404)
    return jsonify({}), 204



port = 8080
herokuPort = os.environ.get('PORT')
if herokuPort != None:
    port = herokuPort
if len(sys.argv) > 1:
    port = int(sys.argv[1])


if __name__ == '__main__':
    app.run(host="0.0.0.0", debug=True, port=int(port))