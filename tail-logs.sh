LOG_FILE="/Users/bharathkumar/Downloads/employee-service/log/log-2024-08-09.log"
tail -n0 -F "$LOG_FILE" | nc -c localhost 50000