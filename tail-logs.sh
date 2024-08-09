date=$(date '+%Y-%m-%d')
LOG_FILE="./log/log-${date}.log"
tail -n0 -F "$LOG_FILE" | nc -c localhost 50000