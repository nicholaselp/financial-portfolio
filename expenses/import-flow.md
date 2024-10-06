### Import Flow

- API endpoint -> /v1/expense/import/bulk
- templates for csv files are located in "upload_csv_templates" folder

#### How the flow works
- When calling the API all the rows in the file are processed and 1 importRequest entry in the database is created
- That importRequestId is returned to the API response
- Per each row an importRequestLine entity in the database is created 
- Kafka message is sent for each importRequestLine and each importRequestLine is proccessed
  - CreateExpense service is called per each importRequestLine attempting to create the expense
  - if the expense is created succesfully the importRequestLine is updated with status success and a reference to the expense entity is present in the table
  - if the expense is not created the status of the importRequestLine will be failed and the reason of error is stored in the error column of the table