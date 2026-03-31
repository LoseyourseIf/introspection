#!/bin/bash
set -e

HBA_FILE="$PGDATA/pg_hba.conf"

# 如果还没加过 就追加 允许从库复制
if ! grep -q "host replication postgres 192.168.158.42/32 md5" "$HBA_FILE"; then
    echo "host replication postgres 192.168.158.42/32 md5" >> "$HBA_FILE"
fi