#!/bin/sh
set -e

# Usage: wait-for-db.sh <host> [--timeout <seconds>] <command...>
host="$1"
shift

# Default timeout
timeout=30

# Check if user provided --timeout
if [ "$1" = "--timeout" ]; then
  shift
  timeout="$1"
  shift
fi

# Environment variables for DB login (set in docker-compose)
user="${SPRING_DATASOURCE_USERNAME:-root}"
pass="${SPRING_DATASOURCE_PASSWORD:-}"

echo "Waiting up to $timeout seconds for MariaDB at $host:3306..."

start_time=$(date +%s)

while true; do
  if mysql -h "$host" -P 3306 -u "$user" -p"$pass" -e "SELECT 1" >/dev/null 2>&1; then
    echo "MariaDB is up!"
    break
  fi

  now=$(date +%s)
  elapsed=$(( now - start_time ))
  if [ "$elapsed" -ge "$timeout" ]; then
    echo "Timeout reached! MariaDB not ready after $timeout seconds."
    exit 1
  fi

  sleep 2
done

# Run the command after DB is ready
exec "$@"