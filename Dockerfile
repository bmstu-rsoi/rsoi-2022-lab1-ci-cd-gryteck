FROM python:3.9.6

COPY src/ /main
WORKDIR /main

RUN pip install -r requirements.txt
CMD ["python3", "main.py"]