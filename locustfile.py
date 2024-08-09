from locust import HttpUser, task
import uuid
from faker import Faker
from faker.providers import address
from faker.providers import phone_number
from faker.providers import ssn
from faker.providers import profile
from faker.providers import job
import re
fake = Faker()
fake.add_provider(address)
fake.add_provider(phone_number)
fake.add_provider(ssn)
fake.add_provider(profile)
fake.add_provider(job)

class EmployeeServiceTest(HttpUser):
    @task
    def create_employee(self):
        request = {"name": fake.name(), 
                   "address": str(fake.address()), 
                   "phoneNumber": re.sub("\D","",fake.basic_phone_number()), 
                   "dateOfBirth": fake.date_of_birth().isoformat(), 
                   "title":fake.job(), "sin":re.sub("\D","",fake.ssn())
                   }
        self.client.put("/employee", json=request,headers={"Request-Id": str(uuid.uuid4())})
        
    @task
    def find_paginated_employee(self):
        self.client.get("/employee/paginated?offset=3&pageSize=10", headers={"Request-Id": str(uuid.uuid4())})

    @task
    def delete_employee(self):
        self.client.get("/employee/paginated?offset=3&pageSize=10", headers={"Request-Id": str(uuid.uuid4())})

