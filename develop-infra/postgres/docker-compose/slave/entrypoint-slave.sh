#!/bin/bash
set -e

MASTER_IP=192.168.158.41
PGDATA=${PGDATA:-/var/lib/postgresql/data/pgdata}

# 如果数据目录为空，才做 basebackup
if [ ! -s "$PGDATA/PG_VERSION" ]; then
    echo ">>> 数据目录为空，开始初始化从库..."
    rm -rf "$PGDATA"/*
    echo ">>> 等待主库 5432 就绪..."
    until pg_isready -h "$MASTER_IP" -p 5432 -U postgres; do sleep 2; done

    echo ">>> 开始 pg_basebackup ..."
    PGPASSWORD='sDf1234#sdif1231dsaolwer2131@2025' \
    pg_basebackup -h "$MASTER_IP" -p 5432 -U postgres -D "$PGDATA" -Fp -Xs -P -R

    echo ">>> base_backup 完成"
    chown -R postgres:postgres "$PGDATA"
else
    echo ">>> 数据目录已存在，跳过 base_backup"
fi

# 启动 PostgreSQL
exec gosu postgres "$@"