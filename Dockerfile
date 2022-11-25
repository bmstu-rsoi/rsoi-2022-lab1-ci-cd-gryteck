FROM python:3.9.6
WORKDIR /main
COPY src/ /main

COPY requirements.txt /main/requirements.txt

RUN pip install -r requirements.txt
CMD ["python3", "main.py"]