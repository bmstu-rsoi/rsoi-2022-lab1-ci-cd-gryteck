FROM python:3.10.4
WORKDIR /main
COPY src/ /main
COPY requirements.txt /main/requirements.txt
RUN pip install -r requirements.txt
CMD ["python3", "main.py"]