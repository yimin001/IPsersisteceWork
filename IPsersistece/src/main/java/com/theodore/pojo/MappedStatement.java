package com.theodore.pojo;

import com.theodore.enums.SqlQueryType;

public class MappedStatement {
    //id标识
    private String id;
    //返回值类型
    private String resultType;
    //参数类型
    private String paramType;
    //sql
    private String sql;
    // sql类型
    private SqlQueryType sqlQueryType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlQueryType getSqlQueryType() {
        return sqlQueryType;
    }

    public void setSqlQueryType(SqlQueryType sqlQueryType) {
        this.sqlQueryType = sqlQueryType;
    }
}
