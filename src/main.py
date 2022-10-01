from flask import Flask


app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def index():
    server_message = ''
    client_message = ''

    if request.method == 'POST':
        client_message = request.form.get('message')

    if client_message.lower() == 'hi':
        server_message = 'Hello'
    elif client_message != '':
        server_message = 'How are you?'

    return render_template(
        'index.html',
        message=server_message,
    )

@app.route('/<int:n>/')
def num(n):
    return str (n+1)