#!/bin/bash

docker exec -it pg_slave psql -U postgres -c \
"SELECT sender_host, sender_port, status, written_lsn, flushed_lsn, last_msg_receipt_time \
 FROM pg_stat_wal_receiver;"