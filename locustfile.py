from locust import HttpUser, task
import uuid
from faker import Faker
from faker.providers import address
from faker.providers import phone_number
from faker.providers import ssn
from faker.providers import profile
from faker.providers import job
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
                   "phoneNumber": fake.phone_number().replace(".",""), 
                   "dateOfBirth": fake.date_of_birth().isoformat(), 
                   "title":fake.job(), "sin":fake.ssn().replace("-","")
                   }
        print(request)
        self.client.put("/employee", json=request,headers={"Request-Id": str(uuid.uuid4())})